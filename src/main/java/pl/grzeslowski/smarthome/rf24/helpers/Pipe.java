package pl.grzeslowski.smarthome.rf24.helpers;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.String.format;

public class Pipe {
    private static final long MAX_VALUE_OF_PIPE = (long) Math.pow(2, 40);
    private final long pipe;

    private static long parseStringToLong(String s) {
        final String charsetName = "UTF-8";
        try {
            byte[] bytes = s.getBytes(charsetName);
            StringBuilder binary = new StringBuilder();
            for (int i = bytes.length - 1; i >=0; i--) {
                int val = bytes[i];
                for (int j = 0; j < 8; j++) {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
            }

            return Long.parseLong(binary.toString(), 2);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(String.format("Cannot find this charset: %s!", charsetName), e);
        }
    }

    public Pipe(long pipe) {
        if (pipe < 0) throw new IllegalArgumentException("Pipe can not be smaller than 0! Was: " + pipe + ".");
        if (pipe > MAX_VALUE_OF_PIPE) {
            throw new IllegalArgumentException("Pipe can not be greater than " + MAX_VALUE_OF_PIPE + "! Was: " + pipe + ".");
        }
        this.pipe = pipe;
    }

    /**
     * Parses string int long value (as a little endian binary number).
     *
     * @param pipe pipe name
     */
    public Pipe(String pipe) {
        this(parseStringToLong(pipe));
    }

    public final long getPipe() {
        return pipe;
    }

    public final BigInteger getBinaryPipe() {
        return new BigInteger(Long.toString(pipe));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pipe)) return false;

        Pipe pipe1 = (Pipe) o;

        return pipe == pipe1.pipe;
    }

    @Override
    public int hashCode() {
        return (int) pipe;
    }

    @Override
    public String toString() {
        return format("%s[bin: %s, hex: %#016X, dec: %019d, ]",
                this.getClass().getSimpleName(),
                Long.toBinaryString(pipe),
                pipe,
                pipe);
    }
}
