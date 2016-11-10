package pl.grzeslowski.smarthome.rf24.helpers;

import static java.lang.String.format;

/**
 * Contains proper value of payload size.
 */
public final class Payload {
    private final short size;

    public Payload(short size) {
        if(size <= 0) {
            throw new IllegalArgumentException(format("Payload size cannot be smaller or equals 0! Size: %s.", size));
        }
        this.size = size;
    }

    public short getSize() {
        return size;
    }

    @Override
    public String toString() {
        return format("%s[size: %s]",
                Payload.class.getSimpleName(),
                size);
    }
}
