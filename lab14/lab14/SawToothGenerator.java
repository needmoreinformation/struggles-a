package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    int period;
    int state;

    private double normalize(int state) {
        double perc = (double) state / (period - 1);
        return -1.0 + 2 * perc;
    }

    @Override
    public double next() {
        this.state = (this.state + 1) % period;
        return normalize(this.state);
    }

    public SawToothGenerator(int period) {
        this.period = period;
        this.state = 0;
    }
}
