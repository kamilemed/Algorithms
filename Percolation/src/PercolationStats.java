import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private final double[] results;
    private final int trials;

    public PercolationStats(int n, int trials) {
        if (n<= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = doTrial(n);
        }	
    }

    private double doTrial(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n+1);
            int col = StdRandom.uniform(1, n+1);
            percolation.open(row, col); 
        }
        double sample = (double) percolation.numberOfOpenSites()/(n*n);
        return sample;
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        double lo = mean() - (1.96 * stddev()) / Math.sqrt(trials);
        return lo;
    }

    public double confidenceHi() {
        double hi = mean() + (1.96 * stddev()) / Math.sqrt(trials);
        return hi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, T);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]" );

    }
}