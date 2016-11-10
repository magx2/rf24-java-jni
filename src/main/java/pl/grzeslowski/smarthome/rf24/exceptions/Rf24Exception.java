package pl.grzeslowski.smarthome.rf24.exceptions;

/**
 * Exception hierarchy for all errors connected with interacting with RF24 devices.
 */
public class Rf24Exception extends RuntimeException {
    public Rf24Exception(String message) {
        super(message);
    }

    public Rf24Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
