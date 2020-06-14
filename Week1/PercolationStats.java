import edu.princeton.cs.algs4.StdRandom; 
import edu.princeton.cs.algs4.StdStats; 

public class PercolationStats {
    private final double mean;
    private final double stddev; 
    private final double low;
    private final double high; 


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) 
            throw new IllegalArgumentException("n and trials must be > 0."); 

        double[] xs = new double[trials]; // results

        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n); 
            
            // no need to check if site is already open, as 
            // numberOfOpenSites will not change in this case
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1; 
                int col = StdRandom.uniform(n) + 1; 
                p.open(row, col); 
            }

            // make sure to cast one of these as double, as 
            // int/int would truncate the result
            xs[t] = (double) p.numberOfOpenSites()/ (double) (n*n); 
        }


        mean = StdStats.mean(xs); 
        stddev = StdStats.stddev(xs); 

        // low confidence
        low = mean - (1.96 * stddev/Math.sqrt((double) trials)); 
        // high confidence
        high = mean + (1.96 * stddev/Math.sqrt((double) trials)); 
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean; 
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval 
    public double confidenceLo() {
        return low; 
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return high; 
    }

    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        if (n <= 0 || trials <= 0) 
            throw new IllegalArgumentException("N and T must be > 0."); 

        PercolationStats stats = new PercolationStats(n, trials); 

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }




}

