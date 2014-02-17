public class PercolationStats {
    /**
     * Field set only for testing purposes, otherwise null.
     */
    private static Percolation[] percolationMocks = null;

    private final int T;
    private final int N;
    private final double[] percolationThresholds;
    private int currentIteration;

    /**
     * perform T independent computational experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        this.N = N;
        percolationThresholds = new double[T];
        while (currentIteration < T) {
            performExperimentOn(getPercolationGrid());
            currentIteration++;
        }
    }

    private void performExperimentOn(Percolation percolationGrid) {
        int numberOfIterations = 0;
        do {
            int randomI;
            int randomJ;
            do {
                randomI = StdRandom.uniform(N) + 1;
                randomJ = StdRandom.uniform(N) + 1;
            } while (percolationGrid.isOpen(randomI, randomJ));
            percolationGrid.open(randomI, randomJ);
            numberOfIterations++;
        } while (!percolationGrid.percolates());
        percolationThresholds[currentIteration]
                = (double) numberOfIterations / (N * N);
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        if (T == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(percolationThresholds);
    }

    /**
     * returns lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * returns upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    /**
     * test client
     */
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2])
        );
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = "
                + stats.confidenceLo() + ", " + stats.confidenceHi());
    }

    private Percolation getPercolationGrid() {
        if (percolationMocks == null) {
            return new Percolation(N);
        } else {
            return percolationMocks[currentIteration];
        }

    }
}