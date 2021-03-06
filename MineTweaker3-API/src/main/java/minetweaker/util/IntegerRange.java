package minetweaker.util;

import java.util.Random;

public class IntegerRange {

    private final int min;
    private final int max;
    private final Random rand;

    public IntegerRange(int min, int max) {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);
        rand = new Random(2906);
    }

    public int getRandom() {
        return rand.nextInt((max - min) + 1) + min;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
