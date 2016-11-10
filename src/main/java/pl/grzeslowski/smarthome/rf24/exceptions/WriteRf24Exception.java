package pl.grzeslowski.smarthome.rf24.exceptions;

import pl.grzeslowski.smarthome.rf24.helpers.Pipe;

public class WriteRf24Exception extends Rf24Exception {
    public WriteRf24Exception(String message) {
        super(message);
    }

    public WriteRf24Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteRf24Exception(Pipe pipe, Throwable throwable) {
        super("Error while writing to " + pipe + "!", throwable);
    }
}
