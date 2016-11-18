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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

public class Rf24PingPongClientExample {
    static {
        Rf24Adapter.loadLibrary();
    }

    private static final Logger logger = LoggerFactory.getLogger(Rf24PingPongClientExample.class);
    private static final Pipe WRITE_PIPE = Rf24PingPongServerExample.READ_PIPE;
    private static final Pipe READ_PIPE = Rf24PingPongServerExample.WRITE_PIPE;
    private static final long TIME_TO_SLEEP = TimeUnit.SECONDS.toMillis(1);

    private final ArgsReader argsReader = new ArgsReader();
    private final BasicRf24 rf24;
    private final ByteBuffer sendBuffer;
    private final ByteBuffer readBuffer;

    public static void main(String[] args) throws Exception {
        final Rf24PingPongClientExample server = new Rf24PingPongClientExample(args);
        server.init();
        server.run();
    }

    public Rf24PingPongClientExample(String[] args) {
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

    private void run() throws InterruptedException {
        for (; ; ) {
            boolean read = false;
            try {
                read = rf24.read(READ_PIPE, readBuffer);
            } catch (ReadRf24Exception ex) {
                logger.error("Read error!", ex);
            }

            if (read) {
                int payload = readBuffer.getInt();
                readBuffer.clear();

                logger.info("Got payload {}", payload);

                sendBuffer.clear();
                sendBuffer.putInt(payload);
                try {
                    rf24.write(WRITE_PIPE, sendBuffer.array());
                } catch (WriteRf24Exception ex) {
                    logger.error("Write error!", ex);
                }

                Thread.sleep(TIME_TO_SLEEP);
            }
        }
    }
}
