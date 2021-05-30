import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] x;
    private double X, s;
    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
	if (n <= 0 || trials <= 0) {
	    throw new IllegalArgumentException("invalid experiments");
	} else {
	    x = new double[trials];
	    int[] closed;
	    for (int i = 0; i < trials; i++) {
		Percolation percolation = new Percolation(n);
		closed = StdRandom.permutation(n*n);
	        for (int j = 0; !percolation.percolates(); j++) {
		    percolation.open(closed[j]/n+1,closed[j]%n+1);
		}
		x[i] = percolation.numberOfOpenSites()*1.0/(n*n);
	    }
	    X = StdStats.mean(x);
	    s = StdStats.stddev(x);
	}
    }
    public double mean() { // sample mean of percolation threshold	
	return X;
    }
    public double stddev() { // sample standard deviation of percolation threshold	
	return s;
    }
    public double confidenceLo() { // low endpoint of 95% confidence interval
	return X-1.96*s/Math.sqrt(x.length);
    }
    public double confidenceHi() { // high endpoint of 95% confidence interval
	return X+1.96*s/Math.sqrt(x.length);
    }
    public static void main(String[] args) { // test client (described below)
	PercolationStats stats = new PercolationStats(new Integer(args[0]).intValue(), new Integer(args[1]).intValue());
	StdOut.print("mean                    = ");
	StdOut.println(stats.mean());
	StdOut.print("stddev                  = ");
	StdOut.println(stats.stddev());
	StdOut.print("95% confidence interval = [");
	StdOut.print(stats.confidenceLo());
	StdOut.print(", ");
	StdOut.print(stats.confidenceHi());
	StdOut.println(']');
    }
}
