package pl.grzeslowski.smarthome.rf24.rpi;

import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * Convenient way to use RPi GPIO pins with RF24 devices.
 *
 * {@see pl.grzeslowski.smarthome.rf24.rpi.RpiPins}
 */
public enum RpiGpio {
    /* Version 1, Pin P1-03 */

    RPI_GPIO_P1_03((short) 0),
    /**
     * Version 1, Pin P1-05
     */
    RPI_GPIO_P1_05((short) 1),
    /**
     * Version 1, Pin P1-07
     */
    RPI_GPIO_P1_07((short) 4),
    /**
     * Version 1, Pin P1-08, defaults to alt function 0 UART0_TXD
     */
    RPI_GPIO_P1_08((short) 14),
    /**
     * Version 1, Pin P1-10, defaults to alt function 0 UART0_RXD
     */
    RPI_GPIO_P1_10((short) 15),
    /**
     * Version 1, Pin P1-11
     */
    RPI_GPIO_P1_11((short) 17),
    /**
     * Version 1, Pin P1-12, can be PWM channel 0 in ALT FUN 5
     */
    RPI_GPIO_P1_12((short) 18),
    /**
     * Version 1, Pin P1-13
     */
    RPI_GPIO_P1_13((short) 21),
    /**
     * Version 1, Pin P1-15
     */
    RPI_GPIO_P1_15((short) 22),
    /**
     * Version 1, Pin P1-16
     */
    RPI_GPIO_P1_16((short) 23),
    /**
     * Version 1, Pin P1-18
     */
    RPI_GPIO_P1_18((short) 24),
    /**
     * Version 1, Pin P1-19, MOSI when SPI0 in use
     */
    RPI_GPIO_P1_19((short) 10),
    /**
     * Version 1, Pin P1-21, MISO when SPI0 in use
     */
    RPI_GPIO_P1_21((short) 9),
    /**
     * Version 1, Pin P1-22
     */
    RPI_GPIO_P1_22((short) 25),
    /**
     * Version 1, Pin P1-23, CLK when SPI0 in use
     */
    RPI_GPIO_P1_23((short) 11),
    /**
     * Version 1, Pin P1-24, CE0 when SPI0 in use
     */
    RPI_GPIO_P1_24((short) 8),
    /**
     * Version 1, Pin P1-26, CE1 when SPI0 in use
     */
    RPI_GPIO_P1_26((short) 7),


    /* RPi Version 2 */

    /**
     * Version 2, Pin P1-03
     */
    RPI_V2_GPIO_P1_03((short) 2),
    /**
     * Version 2, Pin P1-05
     */
    RPI_V2_GPIO_P1_05((short) 3),
    /**
     * Version 2, Pin P1-07
     */
    RPI_V2_GPIO_P1_07((short) 4),
    /**
     * Version 2, Pin P1-08, defaults to alt function 0 UART0_TXD
     */
    RPI_V2_GPIO_P1_08((short) 14),
    /**
     * Version 2, Pin P1-10, defaults to alt function 0 UART0_RXD
     */
    RPI_V2_GPIO_P1_10((short) 15),
    /**
     * Version 2, Pin P1-11
     */
    RPI_V2_GPIO_P1_11((short) 17),
    /**
     * Version 2, Pin P1-12, can be PWM channel 0 in ALT FUN 5
     */
    RPI_V2_GPIO_P1_12((short) 18),
    /**
     * Version 2, Pin P1-13
     */
    RPI_V2_GPIO_P1_13((short) 27),

    /**
     * Version 2, Pin P1-15
     */
    RPI_V2_GPIO_P1_15((short) 22),
    /**
     * Version 2, Pin P1-16
     */
    RPI_V2_GPIO_P1_16((short) 23),
    /**
     * Version 2, Pin P1-18
     */
    RPI_V2_GPIO_P1_18((short) 24),
    /**
     * Version 2, Pin P1-19, MOSI when SPI0 in use
     */
    RPI_V2_GPIO_P1_19((short) 10),
    /**
     * Version 2, Pin P1-21, MISO when SPI0 in use
     */
    RPI_V2_GPIO_P1_21((short) 9),
    /**
     * Version 2, Pin P1-22
     */
    RPI_V2_GPIO_P1_22((short) 25),
    /**
     * Version 2, Pin P1-23, CLK when SPI0 in use
     */
    RPI_V2_GPIO_P1_23((short) 11),
    /**
     * Version 2, Pin P1-24, CE0 when SPI0 in use
     */
    RPI_V2_GPIO_P1_24((short) 8),
    /**
     * Version 2, Pin P1-26, CE1 when SPI0 in use
     */
    RPI_V2_GPIO_P1_26((short) 7),
    /**
     * Version 2, Pin P1-29
     */
    RPI_V2_GPIO_P1_29((short) 5),
    /**
     * Version 2, Pin P1-31
     */
    RPI_V2_GPIO_P1_31((short) 6),
    /**
     * Version 2, Pin P1-32
     */
    RPI_V2_GPIO_P1_32((short) 12),
    /**
     * Version 2, Pin P1-33
     */
    RPI_V2_GPIO_P1_33((short) 13),
    /**
     * Version 2, Pin P1-35
     */
    RPI_V2_GPIO_P1_35((short) 19),
    /**
     * Version 2, Pin P1-36
     */
    RPI_V2_GPIO_P1_36((short) 16),
    /**
     * Version 2, Pin P1-37
     */
    RPI_V2_GPIO_P1_37((short) 26),
    /**
     * Version 2, Pin P1-38
     */
    RPI_V2_GPIO_P1_38((short) 20),
    /**
     * Version 2, Pin P1-40
     */
    RPI_V2_GPIO_P1_40((short) 21),


    /* RPi Version 2, new plug P5 */

    /**
     * Version 2, Pin P5-03
     */
    RPI_V2_GPIO_P5_03((short) 28),
    /**
     * Version 2, Pin P5-04
     */
    RPI_V2_GPIO_P5_04((short) 29),
    /**
     * Version 2, Pin P5-05
     */
    RPI_V2_GPIO_P5_05((short) 30),
    /**
     * Version 2, Pin P5-06
     */
    RPI_V2_GPIO_P5_06((short) 31),


    /* RPi B+ J8 header, also RPi 2 40 pin GPIO header */

    /**
     * B+, Pin J8-03
     */
    RPI_BPLUS_GPIO_J8_03((short) 2),
    /**
     * B+, Pin J8-05
     */
    RPI_BPLUS_GPIO_J8_05((short) 3),
    /**
     * B+, Pin J8-07
     */
    RPI_BPLUS_GPIO_J8_07((short) 4),
    /**
     * B+, Pin J8-08, defaults to alt function 0 UART0_TXD
     */
    RPI_BPLUS_GPIO_J8_08((short) 14),
    /**
     * B+, Pin J8-10, defaults to alt function 0 UART0_RXD
     */
    RPI_BPLUS_GPIO_J8_10((short) 15),
    /**
     * B+, Pin J8-11
     */
    RPI_BPLUS_GPIO_J8_11((short) 17),
    /**
     * B+, Pin J8-12, can be PWM channel 0 in ALT FUN 5
     */
    RPI_BPLUS_GPIO_J8_12((short) 18),
    /**
     * B+, Pin J8-13
     */
    RPI_BPLUS_GPIO_J8_13((short) 27),
    /**
     * B+, Pin J8-15
     */
    RPI_BPLUS_GPIO_J8_15((short) 22),
    /**
     * B+, Pin J8-16
     */
    RPI_BPLUS_GPIO_J8_16((short) 23),
    /**
     * B+, Pin J8-18
     */
    RPI_BPLUS_GPIO_J8_18((short) 24),
    /**
     * B+, Pin J8-19, MOSI when SPI0 in use
     */
    RPI_BPLUS_GPIO_J8_19((short) 10),
    /**
     * B+, Pin J8-21, MISO when SPI0 in use
     */
    RPI_BPLUS_GPIO_J8_21((short) 9),
    /**
     * B+, Pin J8-22
     */
    RPI_BPLUS_GPIO_J8_22((short) 25),
    /**
     * B+, Pin J8-23, CLK when SPI0 in use
     */
    RPI_BPLUS_GPIO_J8_23((short) 11),
    /**
     * B+, Pin J8-24, CE0 when SPI0 in use
     */
    RPI_BPLUS_GPIO_J8_24((short) 8),
    /**
     * B+, Pin J8-26, CE1 when SPI0 in use
     */
    RPI_BPLUS_GPIO_J8_26((short) 7),
    /**
     * B+, Pin J8-29,
     */
    RPI_BPLUS_GPIO_J8_29((short) 5),
    /**
     * B+, Pin J8-31,
     */
    RPI_BPLUS_GPIO_J8_31((short) 6),
    /**
     * B+, Pin J8-32,
     */
    RPI_BPLUS_GPIO_J8_32((short) 12),
    /**
     * B+, Pin J8-33,
     */
    RPI_BPLUS_GPIO_J8_33((short) 13),
    /**
     * B+, Pin J8-35,
     */
    RPI_BPLUS_GPIO_J8_35((short) 19),

    /**
     * B+, Pin J8-36,
     */
    RPI_BPLUS_GPIO_J8_36((short) 16),
    /**
     * B+, Pin J8-37,
     */
    RPI_BPLUS_GPIO_J8_37((short) 26),
    /**
     * B+, Pin J8-38,
     */
    RPI_BPLUS_GPIO_J8_38((short) 20),
    /**
     * B+, Pin J8-40,
     */
    RPI_BPLUS_GPIO_J8_40((short) 21);


    private final short gpioPin;

    RpiGpio(short gpioPin) {
        this.gpioPin = gpioPin;
    }

    public short getGpioPin() {
        return gpioPin;
    }

    public Optional<RpiGpio> fingForGpioPin(short gpioPin) {
        return stream(RpiGpio.values())
                .filter(gpio -> gpio.gpioPin == gpioPin)
                .findFirst();
    }

}
