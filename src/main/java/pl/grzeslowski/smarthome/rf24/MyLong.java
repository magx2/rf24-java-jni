package pl.grzeslowski.smarthome.rf24;

import pl.grzeslowski.smarthome.rf24.generated.SWIGTYPE_p_uint32_t;

public class MyLong extends SWIGTYPE_p_uint32_t {

    public static MyLong create(short number) {
        return new MyLong(number);
    }

    private MyLong(short number) {
        super(number, false);
    }
}
