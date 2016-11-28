package pl.grzeslowski.smarthome.rf24.examples.ping_pong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.grzeslowski.smarthome.rf24.BasicRf24;
import pl.grzeslowski.smarthome.rf24.Rf24Adapter;
import pl.grzeslowski.smarthome.rf24.examples.cmd_line.ArgsReader;
import pl.grzeslowski.smarthome.rf24.examples.stats.Statistics;
import pl.grzeslowski.smarthome.rf24.helpers.Payload;
import pl.grzeslowski.smarthome.rf24.helpers.Pins;
import pl.grzeslowski.smarthome.rf24.helpers.Pipe;
import pl.grzeslowski.smarthome.rf24.helpers.Retry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public abstract class Rf24PingPongAbstract implements AutoCloseable {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final Pipe writePipe;
    protected final Pipe readPipe;

    protected final ArgsReader argsReader = new ArgsReader();
    protected final ByteBuffer sendBuffer = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
    protected final ByteBuffer readBuffer = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);

    protected final BasicRf24 rf24;
    protected final Statistics statistics = new Statistics();

    // read from command line
    private final Pins pins;
    private final Retry retry;
    private final Payload payload;

    protected static void runExample(Rf24PingPongAbstract pingPong) throws Exception {
        try {
            pingPong.init();
            pingPong.run();
        } finally {
            pingPong.close();
        }
    }

    protected Rf24PingPongAbstract(String[] args, Pipe writePipe, Pipe readPipe) {
        this.writePipe = writePipe;
        this.readPipe = readPipe;

        pins = argsReader.readPins(args);
        retry = argsReader.readRetry(args);
        payload = new Payload((short) (Long.SIZE / Byte.SIZE));

        rf24 = new Rf24Adapter(pins, retry, payload);

        sendBuffer.order(ByteOrder.LITTLE_ENDIAN);
        readBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public void init() {
        logger.info("Init RF24");
        logger.info("Write pipe: {}", writePipe);
        logger.info("Read  pipe: {}", readPipe);
        logger.info("Pins: {}", pins);
        logger.info("Retry: {}", retry);
        logger.info("Payload: {}", payload);

        rf24.init();

        if (rf24 instanceof Rf24Adapter) {
            ((Rf24Adapter) rf24).printDetails();
        }
    }

    protected abstract void run() throws Exception;

    @Override
    public void close() {
        rf24.close();
    }
}
