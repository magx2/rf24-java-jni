package pl.grzeslowski.smarthome;

import pl.grzeslowski.smarthome.generated.*;

public class Rf24Adapter {
    private pl.grzeslowski.smarthome.generated.RF24 rf24;

    public Rf24Adapter() {
        rf24 = new RF24((short)1, (short)2);
    }
}
