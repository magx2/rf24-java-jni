package pl.grzeslowski.smarthome.rf24.exceptions;

public class InitRf24Exception extends Rf24Exception {
    public InitRf24Exception(String message) {
        super(message);
    }

    public InitRf24Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public InitRf24Exception(Throwable t) {
        super("Error while initializing!", t);
    }
}
