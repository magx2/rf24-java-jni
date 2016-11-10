package pl.grzeslowski.smarthome.rf24.helpers;

import static java.lang.String.format;

public final class Retry {
    private static final short DEFAULT_RETRY_DELAY = (short) 15;
    private static final short DEFAULT_RETRY_NUMBER = (short) 15;

    private final short retryDelay;
    private final short retryNumber;

    public static Retry defaultInstance() {
        return new Retry(DEFAULT_RETRY_DELAY, DEFAULT_RETRY_NUMBER);
    }

    public Retry(short retryDelay, short retryNumber) {
        if(retryDelay < 0) throw new IllegalArgumentException(format("Retry delay cannot be smaller than 0. Was %s.", retryDelay));
        this.retryDelay = retryDelay;
        if(retryNumber < 0) throw new IllegalArgumentException(format("Retry number cannot be smaller than 0. Was %s.", retryNumber));
        this.retryNumber = retryNumber;
    }

    public short getRetryDelay() {
        return retryDelay;
    }

    public short getRetryNumber() {
        return retryNumber;
    }

    @Override
    public String toString() {
        return format("%s[delay: %s, number: %s]",
                Retry.class.getSimpleName(),
                retryDelay,
                retryNumber);
    }
}
