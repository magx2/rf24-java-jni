package pl.grzeslowski.smarthome.rf24.rpi;

import pl.grzeslowski.smarthome.rf24.helpers.ClockSpeed;
import pl.grzeslowski.smarthome.rf24.helpers.Pins;

/**
 * Pins definition that is easier to use with RPi.
 *
 * {@see RpiGpio}
 * {@see ClockSpeed}
 */
public class RpiPins extends Pins {
    public RpiPins(RpiGpio cePin, RpiGpio csPin, ClockSpeed clockSpeed) {
        super(cePin.getGpioPin(), csPin.getGpioPin(), clockSpeed.getClockSpeed());
    }
}
