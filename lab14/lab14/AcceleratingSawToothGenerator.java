package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class AcceleratingSawToothGenerator implements Generator {
    double factor;
    int period;
    int state;

    private double normalize(int state) {
        double perc = (double) state / (period - 1);
        return -1.0 + 2 * perc;
    }

    @Override
    public double next() {
        this.state = (this.state + 1) % period;
        if (this.state == 0) {
            this.period *= this.factor;
        }
        return normalize(this.state);
    }

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        this.state = 0;
    }

    public static void main(String[] args) {
        Generator generator = new AcceleratingSawToothGenerator(200, 1.05);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
