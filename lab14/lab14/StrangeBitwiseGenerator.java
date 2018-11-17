package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class StrangeBitwiseGenerator implements Generator {
    int period;
    int state;

    private double normalize(int state) {
        double perc = (double) state / (period - 1);
        return -1.0 + 2 * perc;
    }

    @Override
    public double next() {
        this.state = (this.state + 1) % period;
//        int weirdState = this.state & (this.state >> 3) % period;
//        int weirdState = this.state & (this.state >> 3) & (this.state >> 8) % period;
        int weirdState = this.state & (this.state >> 7) % this.period;
        return normalize(weirdState);
    }

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        this.state = 0;
    }

    public static void main(String[] args) {
        Generator generator = new StrangeBitwiseGenerator(1024);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
