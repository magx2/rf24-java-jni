package pl.grzeslowski.smarthome.rf24.helpers;

import static java.lang.String.format;

/**
 * Contains pins configuration for RF24 device.
 *
 * For RPi look at {@see pl.grzeslowski.smarthome.rf24.rpi.RpiPins} and {@see pl.grzeslowski.smarthome.rf24.rpi.RpiGpio}.
 */
public class Pins {
    private final short cePin;
    private final short csPin;
    private final int clockSpeed;

    /**
     * This constructor is protected to make end user use {@link Pins#Pins(short, short, ClockSpeed)}
     *
     * @param cePin pin number on the device that is connected to CE pin on RF24
     * @param csPin pin number on the device that is connected to CS pin on RF24
     * @param clockSpeed clock speed of RF24
     */
    protected Pins(short cePin, short csPin, int clockSpeed) {
        this.cePin = cePin;
        this.csPin = csPin;
        this.clockSpeed = clockSpeed;
    }

    /**
     * Creates object that represents how RF24 is connected to device.
     *
     * @param cePin pin number on the device that is connected to CE pin on RF24
     * @param csPin pin number on the device that is connected to CS pin on RF24
     * @param clockSpeed clock speed of RF24
     */
    public Pins(short cePin, short csPin, ClockSpeed clockSpeed) {
        this(cePin, csPin, clockSpeed.getClockSpeed());
    }

    public final short getCePin() {
        return cePin;
    }

    public final short getCsPin() {
        return csPin;
    }

    public final int getClockSpeed() {
        return clockSpeed;
    }

    @Override
    public String toString() {
        return format("%s[CE: %s, CS: %s, Clock Speed: %s]",
                this.getClass().getSimpleName(),
                cePin,
                csPin,
                clockSpeed);
    }
}
