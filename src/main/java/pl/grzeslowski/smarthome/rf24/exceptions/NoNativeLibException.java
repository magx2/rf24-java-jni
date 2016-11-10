package pl.grzeslowski.smarthome.rf24.exceptions;

public class NoNativeLibException extends Rf24Exception {
    public NoNativeLibException(String message) {
        super(message);
    }

    public NoNativeLibException(String message, Throwable cause) {
        super(message, cause);
    }
}
