import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private final static double CONFIDENCE_95 = 1.96;
    private final int gridSize;
    private final double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should be positive");
        }

        this.gridSize = n;
        this.thresholds = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation percolation = new Percolation(n);
            simulate(n, percolation);
            thresholds[i] = getThreshold(percolation);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double s = stddev();
        return mean - CONFIDENCE_95 * s / Math.sqrt(this.thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double s = stddev();
        return mean + CONFIDENCE_95 * s / Math.sqrt(this.thresholds.length);
    }

    private double getThreshold(Percolation percolation) {
        return percolation.numberOfOpenSites() * 1.0 / (this.gridSize * this.gridSize);
    }

    private void simulate(int n, Percolation percolation) {
        while (!percolation.percolates()) {
            int index = StdRandom.uniform(n * n );
            int row = index / n + 1;
            int col = index % n + 1;
            percolation.open(row, col);
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("missing parameters");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

}
