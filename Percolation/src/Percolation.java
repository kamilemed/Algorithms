import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] sites;
    private int openSites = 0;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final int virtualTop;
    private final int virtualBottom;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        sites = new boolean[n][n];
        this.n = n;
        uf = new WeightedQuickUnionUF(n*n+2);
        virtualTop = n*n;
        virtualBottom = n*n+1;
    }

    public void open(int row, int col) {
        checkArguments(row, col);
        if (isOpen(row,col)) {
            return;
        }
        sites[row-1][col-1] = true;
        openSites++;

        int p = convert(row,col);
        //up
        if (row > 1 && isOpen(row-1, col)) {
            int q = convert(row-1,col);
            uf.union(p, q);
        }
        //down
        if (row < n && isOpen(row+1, col)) {
            int q = convert(row+1,col);
            uf.union(p, q);
        }
        //right
        if (col < n && isOpen(row, col+1)) {
            int q = convert(row,col+1);
            uf.union(p, q);
        }
        //left
        if (col > 1 && isOpen(row, col-1)) {
            int q = convert(row,col-1);
            uf.union(p, q);
        }
        // virtual top
        if (row == 1) {
            uf.union(p, virtualTop);
        }
        // virtual bottom
        if (row == n) {
            uf.union(p, virtualBottom);
        }
    }

    private int convert(int row, int col) {
        return (row-1) * n + (col-1);
    }

    public boolean isOpen(int row, int col) {
        checkArguments(row, col);
        return sites[row-1][col-1];
    }

    public boolean isFull(int row, int col) {		
        checkArguments(row, col);
        int p = convert(row,col);
        return uf.connected(p, virtualTop);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(n*n, n*n+1);
    }

    private void checkArguments(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException();
        }
    }

}

