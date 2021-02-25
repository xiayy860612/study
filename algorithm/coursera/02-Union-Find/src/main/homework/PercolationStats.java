import edu.princeton.cs.algs4.*;

public class PercolationStats {
    private final int gridSize;
    private final Percolation[] percolations;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should be positive");
        }

        this.gridSize = n;
        this.percolations = new Percolation[trials];
        for (int i = 0; i < percolations.length; ++i) {
            this.percolations[i] = new Percolation(n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double[] thresholds = new double[this.percolations.length];
        for (int i = 0; i < this.percolations.length; ++i) {
            thresholds[i] = getThreshold(this.percolations[i]);
        }
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double[] thresholds = new double[this.percolations.length];
        for (int i = 0; i < this.percolations.length; ++i) {
            thresholds[i] = getThreshold(this.percolations[i]);
        }
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double s = stddev();
        return mean - 1.96 * s / Math.sqrt(this.percolations.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double s = stddev();
        return mean + 1.96 * s / Math.sqrt(this.percolations.length);
    }

    private double getThreshold(Percolation percolation) {
        return percolation.numberOfOpenSites() * 1.0 / (this.gridSize * this.gridSize);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n, trials);
        Stopwatch stopwatch = new Stopwatch();
        for (Percolation percolation : stats.percolations) {
            simulate(n, percolation);
        }
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
        StdOut.println();
        StdOut.printf("Total Run Time: %s(s)", stopwatch.elapsedTime());
    }

    private static void simulate(int n, Percolation percolation) {
        while (!percolation.percolates()) {
            int index = StdRandom.uniform(n * n );
            int row = index / n + 1;
            int col = index % n + 1;
            percolation.open(row, col);
        }
    }

}
