package pl.grzeslowski.smarthome.rf24;


import pl.grzeslowski.smarthome.rf24.generated.RF24;

public class Rf24Adapter {
    private RF24 rf24;

    public Rf24Adapter() {
        rf24 = new RF24((short)1, (short)2);
    }
}
