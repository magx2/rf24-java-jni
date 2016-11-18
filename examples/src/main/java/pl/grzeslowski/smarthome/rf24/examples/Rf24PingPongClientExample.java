package pl.grzeslowski.smarthome.rf24.examples;

import pl.grzeslowski.smarthome.rf24.Rf24Adapter;
import pl.grzeslowski.smarthome.rf24.examples.ping_pong.Rf24PingPongAbstract;
import pl.grzeslowski.smarthome.rf24.exceptions.ReadRf24Exception;
import pl.grzeslowski.smarthome.rf24.exceptions.WriteRf24Exception;
import pl.grzeslowski.smarthome.rf24.helpers.Pipe;

import java.util.concurrent.TimeUnit;

public class Rf24PingPongClientExample extends Rf24PingPongAbstract {
    static {
        Rf24Adapter.loadLibrary();
    }

    private static final Pipe WRITE_PIPE = Rf24PingPongServerExample.READ_PIPE;
    private static final Pipe READ_PIPE = Rf24PingPongServerExample.WRITE_PIPE;
    private static final long TIME_TO_SLEEP = TimeUnit.SECONDS.toMillis(1);


    public static void main(String[] args) throws Exception {
        runExample(new Rf24PingPongClientExample(args));
    }

    public Rf24PingPongClientExample(String[] args) {
        super(args, WRITE_PIPE, READ_PIPE);
    }

    @Override
    public void run() throws InterruptedException {
        for (; ; ) {
            boolean read = false;
            try {
                read = rf24.read(READ_PIPE, readBuffer);
            } catch (ReadRf24Exception ex) {
                logger.error("Read error!", ex);
            }

            if (read) {
                long payload = readBuffer.getLong();
                readBuffer.clear();

                logger.info("Got payload {}", payload);

                sendBuffer.clear();
                sendBuffer.putLong(payload);
                try {
                    rf24.write(WRITE_PIPE, sendBuffer.array());
                } catch (WriteRf24Exception ex) {
                    logger.error("Write error when sending " + payload + "!", ex);
                }

                Thread.sleep(TIME_TO_SLEEP);
            }
        }
    }
}
