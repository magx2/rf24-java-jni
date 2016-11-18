package pl.grzeslowski.smarthome.rf24.examples.cmd_line;

import org.apache.commons.cli.*;
import pl.grzeslowski.smarthome.rf24.helpers.ClockSpeed;
import pl.grzeslowski.smarthome.rf24.helpers.Pins;
import pl.grzeslowski.smarthome.rf24.helpers.Retry;

import static java.lang.String.format;

public class ArgsReader {
    private static final String CMD_CE = "ce";
    private static final String CMD_CSN = "csn";
    private static final String CMD_CLOCK_SPEED = "clock_speed";
    private static final String CMD_RETRY_DELAY = "retry_delay";
    private static final String CMD_RETRY_NUMBER = "retry_number";
    private static final String CMD_NUMBER_OF_SENDS = "number_of_sends";

    private static final short DEFAULT_CE = 22;
    private static final short DEFAULT_CSN = 8;
    private static final ClockSpeed DEFAULT_CLOCK_SPEED = ClockSpeed.BCM2835_SPI_SPEED_8MHZ;
    private static final short DEFAULT_RETRY_DELAY = 15;
    private static final short DEFAULT_RETRY_NUMBER = 15;
    private static final long DEFAULT_NUMBER_OF_SENDS = 100;

    protected final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();

    public ArgsReader() {
        options.addOption(CMD_CE, true, "CE pin");
        options.addOption(CMD_CSN, true, "CSN pin");
        options.addOption(CMD_CLOCK_SPEED, true, "Clock speed pin");
        options.addOption(CMD_RETRY_DELAY, true, format("How long should wait before sending a retry message. Defaulr %s.",
                DEFAULT_RETRY_DELAY));
        options.addOption(CMD_RETRY_NUMBER, true, format("How many times should retry sending message. Default %s.",
                DEFAULT_RETRY_NUMBER));
        options.addOption(CMD_NUMBER_OF_SENDS, true, format("How much messages server should send. Default %s.",
                DEFAULT_NUMBER_OF_SENDS));
    }

    public Pins readPins(String[] args) {
        short ce = read(args, CMD_CE, DEFAULT_CE);
        short cs = read(args, CMD_CSN, DEFAULT_CSN);
        ClockSpeed clockSpeed = read(args, CMD_CLOCK_SPEED, DEFAULT_CLOCK_SPEED);

        return new Pins(ce, cs, clockSpeed);
    }

    public Retry readRetry(String[] args) {
        short retryDelay = read(args, CMD_RETRY_DELAY, DEFAULT_RETRY_DELAY);
        short retryNumber = read(args, CMD_RETRY_NUMBER, DEFAULT_RETRY_NUMBER);

        return new Retry(retryDelay, retryNumber);
    }

    public long readNumberOfSends(String[] args) {
        return read(args, CMD_NUMBER_OF_SENDS, DEFAULT_NUMBER_OF_SENDS);
    }

    protected CommandLine parse(String[] args) {
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    protected short read(String[] args, String option, short defaultValue) {
        final CommandLine cmd = parse(args);
        if (cmd.hasOption(option)) {
            return new Short(cmd.getOptionValue(option));
        } else {
            return defaultValue;
        }
    }

    protected int read(String[] args, String option, int defaultValue) {
        final CommandLine cmd = parse(args);
        if (cmd.hasOption(option)) {
            return new Integer(cmd.getOptionValue(option));
        } else {
            return defaultValue;
        }
    }

    protected long read(String[] args, String option, long defaultValue) {
        final CommandLine cmd = parse(args);
        if (cmd.hasOption(option)) {
            return new Integer(cmd.getOptionValue(option));
        } else {
            return defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Enum<T>> T read(String[] args, String option, T defaultValue) {
        final CommandLine cmd = parse(args);
        if (cmd.hasOption(option)) {
            final Class<T> clazz = (Class<T>) defaultValue.getClass();
            return T.valueOf(clazz, cmd.getOptionValue(option));
        } else {
            return defaultValue;
        }
    }
}
