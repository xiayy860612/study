import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * see @{link https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php}
 * WeightedQuickUnionUF size = n^2 * 2 * 4 + 4
 * Percolation size = WeightedQuickUnionUF size + 4 + n^2 + 3 * 4;
 *
 */
public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF vUf;
    // true is open, or not
    private final boolean[] status;
    private int openCount = 0;
    private final int vh;
    private final int vt;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("outside its prescribed range");
        }
        this.n = n;
        this.vh = n * n;
        this.vt = n * n + 1;
        this.vUf = new WeightedQuickUnionUF(n * n + 2);
        this.status = new boolean[n*n];
        for (int i = 0; i < this.status.length; ++i) {
            this.status[i] = false;
            if (i < this.n) {
                this.vUf.union(this.vh, i);
            }

            if (i > this.status.length - 1 - this.n) {
                this.vUf.union(this.vt, i);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getIndex(row, col);
        if (this.status[index]) {
            return;
        }

        this.status[index] = true;
        ++openCount;
        // top
        if (row > 1) {
            int topIndex = index - this.n;
            if (this.status[topIndex]) {
                this.vUf.union(index, topIndex);
            }
        }

        // left
        if (col > 1) {
            int leftIndex = index - 1;
            if (this.status[leftIndex]) {
                this.vUf.union(index, leftIndex);
            }
        }

        // right
        if (col < this.n) {
            int rightIndex = index + 1;
            if (this.status[rightIndex]) {
                this.vUf.union(index, rightIndex);
            }
        }

        // bottom
        if (row < this.n) {
            int bottomIndex = index + this.n;
            if (this.status[bottomIndex]) {
                this.vUf.union(index, bottomIndex);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return this.status[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        if (!this.status[index]) {
            return false;
        }
        if (index < this.n) {
            return true;
        }

        return this.vUf.find(index) == this.vUf.find(this.vh);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.vUf.find(this.vt) == this.vUf.find(this.vh);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("outside its prescribed range");
        }
    }

    private int getIndex(int row, int col) {
        validate(row, col);
        return (row - 1) * this.n + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            try {
                percolation.open(row, col);
                StdOut.println("Percolation: " + percolation.percolates());
            } catch (IllegalArgumentException ex) {
                StdOut.println(ex.getMessage());
            }
        }
    }
}
