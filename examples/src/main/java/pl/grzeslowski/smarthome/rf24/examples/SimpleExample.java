package pl.grzeslowski.smarthome.rf24.examples;

import org.apache.commons.cli.*;
import pl.grzeslowski.smarthome.rf24.generated.RF24;

import java.util.concurrent.TimeUnit;

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
        System.out.println("Start...");
        new SimpleExample().run(args);
        //noinspection InfiniteLoopStatement
        for(int i = 1;;) {
            System.out.println("For " + i++);
            TimeUnit.SECONDS.sleep(5);
        }
    }

    public SimpleExample() {
        options.addOption(CMD_CE, false, "CE pin");
        options.addOption(CMD_CSN, false, "CSN pin");
        options.addOption(CMD_CLOCK_SPEED, false, "Clock speed pin");
    }

    private void run(String[] args) throws ParseException {
        System.out.println("SimpleExample.run(args)");
        CommandLine cmd = parser.parse( options, args);

        System.out.println("new RF24(...)");
        final RF24 radio = new RF24(
                cePin(cmd),
                csnPin(cmd),
                clockSpeed(cmd)
        );

        System.out.println("radio.begin();");
        radio.begin();

        // optionally, increase the delay between retries & # of retries
//        radio.setRetries((short) 15, (short) 15);
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
