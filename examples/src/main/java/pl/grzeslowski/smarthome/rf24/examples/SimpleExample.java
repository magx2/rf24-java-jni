package pl.grzeslowski.smarthome.rf24.examples;

import org.apache.commons.cli.*;
import pl.grzeslowski.smarthome.rf24.generated.RF24;

public class SimpleExample {
    static {
        System.loadLibrary("rf24bcmjava");
    }

    private static final String CMD_CE = "ce";
    private static final String CMD_CSN = "csn";
    private static final String CMD_CLOCK_SPEED = "clock_speed";

    private static final short DEFAULT_CE = 22;
    private static final short DEFAULT_CSN = 8;
    private static final short DEFAULT_CLOCK_SPEED = 32;

    private final CommandLineParser parser = new DefaultParser();
    private final Options options = new Options();

    public static void main(String[] args) throws Exception {
        new SimpleExample().run(args);
        //noinspection InfiniteLoopStatement
        for(;;) {}
    }

    public SimpleExample() {
        options.addOption(CMD_CE, false, "CE pin");
        options.addOption(CMD_CSN, false, "CSN pin");
        options.addOption(CMD_CLOCK_SPEED, false, "Clock speed pin");
    }

    private void run(String[] args) throws ParseException {
        CommandLine cmd = parser.parse( options, args);

        final RF24 radio = new RF24(cePin(cmd), csnPin(cmd), clockSpeed(cmd));

        radio.begin();

        // optionally, increase the delay between retries & # of retries
        radio.setRetries((short) 15, (short) 15);
        // Dump the configuration of the rf unit for debugging
        radio.printDetails();
    }

    private short cePin(CommandLine cmd) {
        if(cmd.hasOption(CMD_CE)) {
            return new Short(cmd.getOptionValue(CMD_CE));
        } else {
            return DEFAULT_CE;
        }
    }

    private short csnPin(CommandLine cmd) {
        if(cmd.hasOption(CMD_CSN)) {
            return new Short(cmd.getOptionValue(CMD_CSN));
        } else {
            return DEFAULT_CSN;
        }
    }

    private short clockSpeed(CommandLine cmd) {
        if(cmd.hasOption(CMD_CLOCK_SPEED)) {
            return new Short(cmd.getOptionValue(CMD_CLOCK_SPEED));
        } else {
            return DEFAULT_CLOCK_SPEED;
        }
    }
}
