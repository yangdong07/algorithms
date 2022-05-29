
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholds;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        thresholds = new double[trials];
        T = trials;
        for (int t = 0; t < T; ++t) {
            Percolation model = new Percolation(n);
            int[] perm = StdRandom.permutation(n * n);
            int opened = 0;
            while (!model.percolates()) {
                int idx = perm[opened];
                int row = idx / n + 1;
                int col = idx % n + 1;
                model.open(row, col);
                ++opened;
            }
            thresholds[t] = (double)opened / (n * n);
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
        return (mean() - 1.96 * stddev() / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(T));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trivals = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trivals);
        StdOut.printf("%-24s = %f\n", "mean", stats.mean());
        StdOut.printf("%-24s = %f\n", "stddev", stats.stddev());
        StdOut.printf("%-24s = [%f, %f]\n", "95% confidence interval",
                stats.confidenceLo(), stats.confidenceHi());
    }

}
