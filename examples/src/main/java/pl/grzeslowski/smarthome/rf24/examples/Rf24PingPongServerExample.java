package pl.grzeslowski.smarthome.rf24.examples;

import org.apache.commons.lang3.time.DurationFormatUtils;
import pl.grzeslowski.smarthome.rf24.Rf24Adapter;
import pl.grzeslowski.smarthome.rf24.examples.ping_pong.Rf24PingPongAbstract;
import pl.grzeslowski.smarthome.rf24.exceptions.ReadRf24Exception;
import pl.grzeslowski.smarthome.rf24.exceptions.WriteRf24Exception;
import pl.grzeslowski.smarthome.rf24.helpers.Pipe;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Rf24PingPongServerExample extends Rf24PingPongAbstract {
    static {
        Rf24Adapter.loadLibrary();
    }

    public static final Pipe WRITE_PIPE = new Pipe("1Node");
    public static final Pipe READ_PIPE = new Pipe("2Node");

    private static final long WAITING_FOR_RESPONSE_TIME = TimeUnit.SECONDS.toMillis(1);
    private static final long TIME_TO_SLEEP = TimeUnit.SECONDS.toMillis(1);

    private final long numberOfSends;

    public static void main(String[] args) throws Exception {
        runExample(new Rf24PingPongServerExample(args));
    }

    public Rf24PingPongServerExample(String[] args) {
        super(args, WRITE_PIPE, READ_PIPE);
        numberOfSends = argsReader.readNumberOfSends(args);
    }

    @Override
    public void run() throws InterruptedException {
        for (long counter = 1; counter <= numberOfSends; counter++) {
            logger.info("Iteration #{}", counter);

            // send
            send();

            // read
            read();

            // Sleep
            Thread.sleep(TIME_TO_SLEEP);
        }

        logger.info(
                String.format("SENT: %d/%d, RECEIVED: %d, LOST: %d (%.1f%%) where: NOT SEND: %d, TIMEOUT: %d",
                        statistics.howManySent(),
                        numberOfSends,
                        statistics.howManyReceivedBack(),
                        statistics.howManyLost(),
                        statistics.howManyLostInPercentage(),
                        statistics.howManyErrorWhileSending(),
                        statistics.howManyTimeoutMessage())
        );
        logger.info(
                String.format("RoundTrip MAX: %d, MIN: %d, AVG: %d",
                        statistics.maxRoundTripTime().orElse(-1L),
                        statistics.minRoundTripTime().orElse(-1L),
                        statistics.avgRoundTripTime()
                        )
        );
    }

    private void send() {
        long time = new Date().getTime();
        logger.info("Now sending {}...", time);
        sendBuffer.clear();
        sendBuffer.putLong(time);
        try {
            final boolean wrote = rf24.write(WRITE_PIPE, sendBuffer.array());
            if (!wrote) {
                logger.error("Failed sending {}!", time);
                statistics.errorWhileSending();
            } else {
                statistics.messageSent();
            }
        } catch (WriteRf24Exception ex) {
            logger.error("Failed sending " + time + "!", ex);
            statistics.errorWhileSending();
        }
    }

    private void read() {
        readBuffer.clear();
        final long startedAt = new Date().getTime();
        boolean wasRead = false;
        boolean timeout = false;
        try {
            while (!wasRead && !timeout) {
                wasRead = rf24.read(READ_PIPE, readBuffer);
                timeout = !wasRead && new Date().getTime() > startedAt + WAITING_FOR_RESPONSE_TIME;
            }
        } catch (ReadRf24Exception ex) {
            logger.error("Error while reading!", ex);
        }

        if (wasRead) {
            final long response = readBuffer.getLong();
            final long now = new Date().getTime();
            final long roundTripTime = now - response;
            logger.info("Got {}, Round trip time {} [s].", response, DurationFormatUtils.formatDuration(roundTripTime, "ss.SS", true));
            statistics.receivedBack(roundTripTime);
        } else {
            logger.error("Timeout!");
            statistics.timeoutMessage();
        }
    }
}
