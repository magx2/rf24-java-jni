package pl.grzeslowski.smarthome.rf24.helpers;

import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * Convenient enumerator to use proper values for devices clock speed.
 */
public enum ClockSpeed {
    BCM2835_SPI_SPEED_64MHZ(4),
    BCM2835_SPI_SPEED_32MHZ(8),
    BCM2835_SPI_SPEED_16MHZ(16),
    BCM2835_SPI_SPEED_8MHZ(32),
    BCM2835_SPI_SPEED_4MHZ(64),
    BCM2835_SPI_SPEED_2MHZ(128),
    BCM2835_SPI_SPEED_1MHZ(256),
    BCM2835_SPI_SPEED_512KHZ(512),
    BCM2835_SPI_SPEED_256KHZ(1024),
    BCM2835_SPI_SPEED_128KHZ(2048),
    BCM2835_SPI_SPEED_64KHZ(4096),
    BCM2835_SPI_SPEED_32KHZ(8192),
    BCM2835_SPI_SPEED_16KHZ(16384),
    BCM2835_SPI_SPEED_8KHZ(32768);

    private final int clockSpeed;

    ClockSpeed(int clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public int getClockSpeed() {
        return clockSpeed;
    }

    public static Optional<ClockSpeed> findClockSpeed(int clockSpeed) {
        return stream(ClockSpeed.values())
                .filter(x -> x.clockSpeed == clockSpeed)
                .findFirst();
    }
}
