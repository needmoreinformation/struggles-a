package hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int numTrials;
    private double numTrialsSqrt;
    private double[] percolationThreshold;
    private double percMean;
    private double percStddev;

    /**
     * Perform trials independant of experiments on an n-by-n grid
     * @param n
     * @param trials
     */
    public PercolationStats(int n, int trials, PercolationFactory pf) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be above 0");
        }

        numTrials = trials;
        numTrialsSqrt = Math.sqrt(trials);
        int numSites = n * n;
        percolationThreshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = pf.make(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(0, n);
                int col = StdRandom.uniform(0, n);
                p.open(row, col);
            }
            percolationThreshold[i] = (double)p.numberOfOpenSites() / numSites;
        }
        percMean = StdStats.mean(percolationThreshold);
        percStddev = StdStats.stddev(percolationThreshold);
    }

    /**
     * Sample mean of percolation threshold.
     * @return
     */
    public double mean() {
        return percMean;
    }

    /**
     * Sample standard deviation of percolation threshold.
     * @return
     */
    public double stddev() {
        return percStddev;
    }

    private double confidenceHelper() {
        return 1.96 * stddev();
    }

    /**
     * Low endpoint of 95% confidence interval.
     * @return
     */
    public double confidenceLow() {
        return mean() - (confidenceHelper() / numTrialsSqrt);
    }

    /**
     * High endpoint of 95% confidence interval.
     * @return
     */
    public double confidenceHigh() {
        return mean() + (confidenceHelper() / numTrialsSqrt);
    }

    /**
     * Test client.
     *
     * Usage: PercolationStats n T
     * Example: PercolationStats 200 100
     *
     * Limitations:
     * n > 0
     * T > 0
     *
     * @param args
     */
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int numTrials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(gridSize, numTrials, new PercolationFactory());
        StdOut.printf("%-23s = %.16f\n", "mean", ps.mean());
        StdOut.printf("%-23s = %.16f\n", "stddev", ps.stddev());
        StdOut.printf("%-23s = [%.16f, %.16f]\n", "95% confidence interval", ps.confidenceLow(), ps.confidenceHigh());

    }
}