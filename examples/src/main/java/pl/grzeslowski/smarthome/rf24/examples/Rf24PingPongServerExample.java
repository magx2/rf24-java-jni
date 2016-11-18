package pl.grzeslowski.smarthome.rf24.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.grzeslowski.smarthome.rf24.BasicRf24;
import pl.grzeslowski.smarthome.rf24.Rf24Adapter;
import pl.grzeslowski.smarthome.rf24.examples.cmd_line.ArgsReader;
import pl.grzeslowski.smarthome.rf24.exceptions.ReadRf24Exception;
import pl.grzeslowski.smarthome.rf24.exceptions.WriteRf24Exception;
import pl.grzeslowski.smarthome.rf24.helpers.Payload;
import pl.grzeslowski.smarthome.rf24.helpers.Pins;
import pl.grzeslowski.smarthome.rf24.helpers.Pipe;
import pl.grzeslowski.smarthome.rf24.helpers.Retry;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

public class Rf24PingPongServerExample {
    static {
        Rf24Adapter.loadLibrary();
    }

    private static final Logger logger = LoggerFactory.getLogger(Rf24PingPongServerExample.class);
    private static final Pipe WRITE_PIPE = new Pipe(new BigInteger("110010101100100011011110100111000110001", 2).longValue());
    private static final Pipe READ_PIPE = new Pipe(new BigInteger("110010101100100011011110100111000110010", 2).longValue());
    private static final long WAITING_FOR_RESPONSE_TIME = TimeUnit.SECONDS.toMillis(1);
    private static final long TIME_TO_SLEEP = TimeUnit.SECONDS.toMillis(1);

    private final ArgsReader argsReader = new ArgsReader();
    private final BasicRf24 rf24;
    private final ByteBuffer sendBuffer;
    private final ByteBuffer readBuffer;

    public static void main(String[] args) throws Exception {
        final Rf24PingPongServerExample server = new Rf24PingPongServerExample(args);
        server.init();
        server.run();
    }

    public Rf24PingPongServerExample(String[] args) {
        Pins pins = argsReader.readPins(args);
        Retry retry = argsReader.readRetry(args);
        Payload payload = new Payload((short) 8);

        rf24 = new Rf24Adapter(pins, retry, payload);

        sendBuffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        sendBuffer.order(ByteOrder.LITTLE_ENDIAN);

        readBuffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        readBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public void init() {
        rf24.init();
    }

    public void run() throws InterruptedException {
        for (int counter = 1; true; counter++) {
            counter++;

            // send
            send(counter);

            // read
            read();

            // Sleep
            Thread.sleep(TIME_TO_SLEEP);
        }
    }

    private void send(int counter) {
        logger.info("Now sending {}...", counter);
        sendBuffer.clear();
        sendBuffer.putInt(counter);
        try {
            final boolean wrote = rf24.write(WRITE_PIPE, sendBuffer.array());
            if (!wrote) {
                logger.error("Failed!");
            }
        } catch (WriteRf24Exception ex) {
            logger.error("Failed!", ex);
        }
    }

    private void read() {
        readBuffer.clear();
        final long startedAt = System.currentTimeMillis();
        boolean wasRead = false;
        try {
            while (!wasRead && System.currentTimeMillis() <= startedAt + WAITING_FOR_RESPONSE_TIME) {
                wasRead = rf24.read(READ_PIPE, readBuffer);
            }
        } catch (ReadRf24Exception ex) {
            logger.error("Error while reading!", ex);
        }

        if(wasRead) {
            int response = readBuffer.getInt();
            logger.info("Got {}", response);
        } else {
            logger.error("Timeout!");
        }
    }
}
