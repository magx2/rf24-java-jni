package pl.grzeslowski.smarthome.rf24.exceptions;

public class CloseRf24Exception extends Rf24Exception {
    public CloseRf24Exception(String message) {
        super(message);
    }

    public CloseRf24Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public CloseRf24Exception(Throwable throwable) {
        super("Error while closing!", throwable);
    }
}
