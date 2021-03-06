import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid, percolation;
    private boolean[] opened;
    private int open_sites, n;
    public Percolation(int n) { // create n-by-n grid, with all sites blocked
	if (n <= 0) {
	    throw new IllegalArgumentException("n should be a natural number");
	} else {
	    this.n = n;
	    grid = new WeightedQuickUnionUF((n+1)*n);
	    percolation = new WeightedQuickUnionUF((n+2)*n);
	    opened = new boolean[n*n];
	    for (int i = 0; i < n-1; i++) {
		grid.union(i,i+1);
		percolation.union(i,i+1);
		percolation.union((n+1)*n+i,(n+1)*n+i+1);
	    }
	}
    }
    public void open(int row, int col) { // open site (row, col) if it is not open already
	if (row < 1 || col < 1 || row > n || col > n) {
	    throw new IllegalArgumentException("row and column should be between 1 and n inclusive");
	} else if (!isOpen(row,col)) {
	    if (row == 1 || isOpen(row-1,col)) {
		grid.union(row*n+col-1-n,row*n+col-1);
	        percolation.union(row*n+col-1-n,row*n+col-1);
	    }
	    if (row < n && isOpen(row+1,col)) {
		grid.union(row*n+col-1+n,row*n+col-1);
	        percolation.union(row*n+col-1+n,row*n+col-1);
	    } else if (row == n) {
		percolation.union(row*n+col-1+n,row*n+col-1);
	    }
	    if (col > 1 && isOpen(row,col-1)) {
		grid.union(row*n+col-2,row*n+col-1);
	        percolation.union(row*n+col-2,row*n+col-1);
	    }
	    if (col < n && isOpen(row,col+1)) {
		grid.union(row*n+col,row*n+col-1);
	        percolation.union(row*n+col,row*n+col-1);
	    }
	    opened[(row-1)*n+col-1] = true;
	    open_sites += 1;
	}
    }
    public boolean isOpen(int row, int col) { // is site (row, col) open?
	if (row < 1 || col < 1 || row > n || col > n) {
	    throw new IllegalArgumentException("row and column should be between 1 and n inclusive");
	} else {
	    return opened[(row-1)*n+col-1];
	}
    }
    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (row < 1 || col < 1 || row > n || col > n) {
	    throw new IllegalArgumentException("row and column should be between 1 and n inclusive");
	} else {
	    return grid.connected(row*n+col-1,0);
	}
    }
    public int numberOfOpenSites() { // number of open sites
	return open_sites;
    }
    public boolean percolates() { // does the system percolate?
	return percolation.connected(0,(n+2)*n-1);
    }
}
