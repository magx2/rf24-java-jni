package pl.grzeslowski.smarthome.rf24;

import pl.grzeslowski.smarthome.rf24.generated.SWIGTYPE_p_uint8_t;

/**
 * Created by martin on 06.11.16.
 */
public class MyShort extends SWIGTYPE_p_uint8_t {

    public static MyShort create(short number) {
        return new MyShort(number);
    }

    private MyShort(short number) {
        super(number, false);
    }
}
