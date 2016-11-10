package pl.grzeslowski.smarthome.rf24.exceptions;

import pl.grzeslowski.smarthome.rf24.helpers.Pipe;

import java.util.List;
import java.util.stream.Collectors;

public class ReadRf24Exception extends Rf24Exception {
    public ReadRf24Exception(String message) {
        super(message);
    }

    public ReadRf24Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadRf24Exception(List<? extends Pipe> readPipes, Throwable throwable) {
        super("Error while reading from pipes: " + String.join(",", readPipes.stream().map(Pipe::toString).collect(Collectors.toList())) + "!", throwable);
    }
}
