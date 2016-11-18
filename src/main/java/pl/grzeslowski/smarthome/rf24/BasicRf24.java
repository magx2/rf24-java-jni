package pl.grzeslowski.smarthome.rf24;

import pl.grzeslowski.smarthome.rf24.exceptions.CloseRf24Exception;
import pl.grzeslowski.smarthome.rf24.exceptions.InitRf24Exception;
import pl.grzeslowski.smarthome.rf24.exceptions.ReadRf24Exception;
import pl.grzeslowski.smarthome.rf24.exceptions.WriteRf24Exception;
import pl.grzeslowski.smarthome.rf24.helpers.Payload;
import pl.grzeslowski.smarthome.rf24.helpers.Pipe;

import java.nio.ByteBuffer;
import java.util.List;

import static java.util.Collections.singletonList;

public interface BasicRf24 extends AutoCloseable {
    /**
     * Begin new transmission to device. Combined with {@see close()} can be invoked multiple times.
     *
     * @throws InitRf24Exception when some error connected with initializing device occurs
     */
    void init() throws InitRf24Exception;

    /**
     * Reads data from <b>only one</b> pipe at time and writes it to the buffer.
     *
     * @param readPipes pipes to read from, cannot be null or empty
     * @param buffer    buffer to write to. Remember to set proper {@see java.nio.ByteOrder}
     *                  (RPi has <b>little endian</b>). Can not be null
     * @return true if read was successful, if there was nothing in pipes return false
     * @throws ReadRf24Exception when some error connected with reading into pipe occurs
     */
    boolean read(List<Pipe> readPipes, ByteBuffer buffer) throws ReadRf24Exception;

    /**
     * Does the same what {@see BasicRf24#read} (just pack readPipe into one element list.
     *
     * @param readPipe see {@see BasicRf24#read}
     * @param buffer see {@see BasicRf24#read}
     * @return see {@see BasicRf24#read}
     * @throws ReadRf24Exception see {@see BasicRf24#read}
     */
    default boolean read(Pipe readPipe, ByteBuffer buffer) throws ReadRf24Exception {
        return read(singletonList(readPipe), buffer);
    }

    /**
     * Write bytes into the pipe. If needed sets {@param pipe} as writing pipe.
     *
     * @param write   pipe to write into, can not be null
     * @param toWrite bytes to write into pipe
     * @return true if write was successful
     * @throws WriteRf24Exception when some error connected with writing into pipe occurs
     */
    boolean write(Pipe write, byte[] toWrite) throws WriteRf24Exception;

    /**
     * Closes the device and releases resources. Class can be used only if init will be invoked.
     *
     * @throws CloseRf24Exception when some error connected with closing device occurs
     */
    @Override void close() throws CloseRf24Exception;
}
