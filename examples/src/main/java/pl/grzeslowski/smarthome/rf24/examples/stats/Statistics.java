package pl.grzeslowski.smarthome.rf24.examples.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Statistics {
    private final AtomicLong messageSent = new AtomicLong();
    private final AtomicLong receivedBack = new AtomicLong();
    private final AtomicLong errorWhileSending = new AtomicLong();
    private final AtomicLong timeoutMessages = new AtomicLong();

    private final List<Long> roundTripTimes = Collections.synchronizedList(new ArrayList<>());

    public void messageSent() {
        messageSent.incrementAndGet();
    }

    public long howManySent() {
        return messageSent.longValue();
    }

    public void receivedBack(long roundTripTime) {
        receivedBack.incrementAndGet();
        roundTripTimes.add(roundTripTime);
    }

    public long howManyReceivedBack() {
        return receivedBack.longValue();
    }

    public void errorWhileSending() {
        errorWhileSending.incrementAndGet();
    }

    public long howManyErrorWhileSending() {
        return errorWhileSending.longValue();
    }

    public void timeoutMessage() {
        timeoutMessages.incrementAndGet();
    }

    public long howManyTimeoutMessage() {
        return timeoutMessages.longValue();
    }

    public long howManyLost() {
        return howManyErrorWhileSending() + howManyTimeoutMessage();
    }

    public double howManyLostInPercentage() {
        final long lost = howManyErrorWhileSending() + howManyTimeoutMessage();
        return lost / (lost + howManySent());
    }

    public Optional<Long> maxRoundTripTime() {
        return roundTripTimes.stream()
                .max(Long::compareTo);
    }

    public Optional<Long> minRoundTripTime() {
        return roundTripTimes.stream()
                .min(Long::compareTo);
    }

    public long avgRoundTripTime() {
        return roundTripTimes.stream()
                .reduce(0L, (acc, roundTripTime) -> acc + roundTripTime);
    }
}
