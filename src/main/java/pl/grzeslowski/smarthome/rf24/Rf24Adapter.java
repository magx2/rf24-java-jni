package pl.grzeslowski.smarthome.rf24;


import pl.grzeslowski.smarthome.rf24.exceptions.*;
import pl.grzeslowski.smarthome.rf24.generated.RF24;
import pl.grzeslowski.smarthome.rf24.helpers.Payload;
import pl.grzeslowski.smarthome.rf24.helpers.Pins;
import pl.grzeslowski.smarthome.rf24.helpers.Pipe;
import pl.grzeslowski.smarthome.rf24.helpers.Retry;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * <p>Adapter that delegates everything to {@link RF24} and do dome small login.</p>
 * <p>
 * <p>Watch out! All class methods are synchronized (on this)!</p>
 */
public class Rf24Adapter implements BasicRf24 {
    public static final int MAX_NUMBER_OF_READING_PIPES = 5;
    private static final long DELAY_AFTER_STARTING_LISTENING = TimeUnit.SECONDS.convert(1, TimeUnit.MILLISECONDS);
    private static final String RF24_LIB_NAME = "rf24bcmjava";

    private final Pins pins;
    private final Retry retry;
    private final Payload payload;

    private RF24 rf24;
    private List<Pipe> actualReadPipes;

    public static void loadLibrary() {
        try {
            System.loadLibrary(RF24_LIB_NAME);
        } catch (UnsatisfiedLinkError e) {
            throw new NoNativeLibException("Native code library (" + RF24_LIB_NAME + ") failed to load.", e);
        }
    }

    public Rf24Adapter(Pins pins, Retry retry, Payload payload) {
        if (pins == null) throw new NullPointerException("Pins cannot be null!");
        this.pins = pins;
        if (retry == null) throw new NullPointerException("Retry cannot be null!");
        this.retry = retry;
        if (payload == null) throw new NullPointerException("Payload cannot be null!");
        this.payload = payload;
    }

    /**
     * <p>Instead of allowing inheriting classes to directly uses RF24 object, this method
     * allows to use it with proper synchronization and boundaries (do not use this if Rf34Adapter is not
     * {@link Rf24Adapter#init()}.</p>
     *
     * @param function      piece of code to execute with {@link RF24}
     * @param <ReturnClass> class of what will be returned
     * @return result of {@link Function#apply(Object)}
     */
    protected synchronized <ReturnClass> ReturnClass mapRf24(Function<RF24, ReturnClass> function) {
        if (function == null) {
            throw new NullPointerException("Function cannot be null!");
        }
        try {
            return function.apply(rf24);
        } catch (Exception e) {
            throw new Rf24Exception("mapRf24 thrown an Exception!", e);
        }
    }

    /**
     * <p>Instead of allowing inheriting classes to directly uses RF24 object, this method
     * allows to use it with proper synchronization and boundaries (do not use this if Rf34Adapter is not
     * {@link Rf24Adapter#init()}.</p>
     *
     * @param consumer piece of code to execute with {@link RF24}
     */
    protected synchronized void consumeRf24(Consumer<RF24> consumer) {
        if (consumer == null) {
            throw new NullPointerException("Consumer cannot be null!");
        }
        try {
            consumer.accept(rf24);
        } catch (Exception e) {
            throw new Rf24Exception("consumeRf24 thrown an Exception!", e);
        }
    }

    @Override
    public synchronized void init() {
        if (isInit()) {
            throw new IllegalStateException(format("RF24 is already initialized! You need to call close() first! RF24: [%s]", rf24));
        }
        try {
            rf24 = new RF24(pins.getCePin(), pins.getCsPin(), pins.getClockSpeed());
            rf24.setPayloadSize(payload.getSize());
            rf24.begin();
            rf24.setRetries(retry.getRetryDelay(), retry.getRetryNumber());
            startListening();
        } catch (Exception e) {
            throw new InitRf24Exception(e);
        }
    }

    @Override
    public synchronized boolean isInit() {
        return rf24 != null;
    }

    private void startListening() {
        assert isInit();
        rf24.startListening();
        try {
            TimeUnit.MILLISECONDS.sleep(DELAY_AFTER_STARTING_LISTENING);
        } catch (InterruptedException ignored) {
            // Ignore
        }
    }

    @Override
    public synchronized void close() {
        if (!isInit()) {
            throw new IllegalStateException("RF24 was not initialized! Please call init() before calling close().");
        }
        try {
            rf24.delete();
        } catch (Error e) {
            throw new CloseRf24Exception(e);
        } finally {
            rf24 = null;
        }
    }

    @Override
    public synchronized boolean read(List<Pipe> readPipes, ByteBuffer buffer) {
        if (readPipes == null) throw new NullPointerException("Read pipes cannot be null!!");
        if (buffer == null) throw new NullPointerException("Buffer cannot be null!!");
        if (readPipes.isEmpty()) throw new IllegalArgumentException("There need to be at least 1 pipe to read from!");
        if (readPipes.size() > MAX_NUMBER_OF_READING_PIPES) {
            throw new IllegalArgumentException(format("There are too much reading pipes! Max: %s, was: %s.", MAX_NUMBER_OF_READING_PIPES, readPipes.size()));
        }

        try {
            trySetNewReadingPipes(readPipes);
            if (rf24.available()) {
                rf24.read(buffer.array(), (short) (buffer.capacity()));

                return true;
            } else {
                return false;
            }
        } catch (Error e) {
            throw new ReadRf24Exception(readPipes, e);
        }
    }

    private void trySetNewReadingPipes(List<Pipe> readPipes) {
        if (isNewReadingPipes(readPipes)) {
            rf24.stopListening();

            actualReadPipes = new ArrayList<>(readPipes.size());
            for (short i = 1; i <= readPipes.size(); i++) {
                final Pipe pipe = readPipes.get(i - 1);
                rf24.openReadingPipe(i, pipe.getBinaryPipe());
                actualReadPipes.add(i - 1, pipe);
            }

            startListening();
        }
    }

    private boolean isNewReadingPipes(List<Pipe> readPiped) {
        if (actualReadPipes == null) return true;

        if (actualReadPipes.size() != readPiped.size()) return true;

        for (int i = 0; i < actualReadPipes.size(); i++) {
            final Pipe actual = actualReadPipes.get(i);
            final Pipe pipe = readPiped.get(i);
            if (!actual.equals(pipe)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public synchronized boolean write(Pipe write, byte[] toSend) {
        if (write == null) throw new NullPointerException("Write pipe cannot nbe null!");
        if (toSend.length > rf24.getPayloadSize()) {
            throw new IllegalArgumentException(format("Bytes to send exceeds max payload size! Bytes size: %s, max payload size: %s",
                    toSend.length, rf24.getPayloadSize()));
        }

        try {
            rf24.stopListening();
            rf24.openWritingPipe(write.getBinaryPipe());
            return rf24.write(toSend, (short) toSend.length);
        } catch (Exception e) {
            throw new WriteRf24Exception(write, e);
        } finally {
            startListening();
        }
    }

    /**
     * Printing details from RF24 card.
     *
     * Important: Use it only in debug purposes!
     */
    public void printDetails() {
        rf24.printDetails();
    }

    @Override
    public String toString() {
        final String readPipes = String.join(",",
                actualReadPipes.stream()
                        .map(Pipe::getPipe)
                        .map(l -> Long.toString(l))
                        .collect(Collectors.toList())
        );
        return String.format("%s[init: %s, readPipes: %s, pins: %s, retry: %s, payload: %s]",
                this.getClass().getSimpleName(),
                rf24 != null,
                readPipes,
                pins,
                retry,
                payload);
    }
}
