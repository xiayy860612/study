import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * see @{link https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php}
 */
public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    // 0 is blocked, 1 is open
    private final int[] status;
    private int openCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("outside its prescribed range");
        }
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n*n);
        this.status = new int[n*n];
        for (int i = 0; i < this.status.length; ++i) {
            this.status[i] = 0;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getIndex(row, col);
        if (this.status[index] == 1) {
            return;
        }

        this.status[index] = 1;
        ++openCount;
        if (row > 1) {
            int topIndex = index - this.n;
            if (this.status[topIndex] == 1) {
                this.uf.union(index, topIndex);
            }
        }

        // bottom
        if (row < this.n) {
            int bottomIndex = index + this.n;
            if (this.status[bottomIndex] == 1) {
                this.uf.union(index, bottomIndex);
            }
        }

        // left
        if (col > 1) {
            int leftIndex = index - 1;
            if (this.status[leftIndex] == 1) {
                this.uf.union(index, leftIndex);
            }
        }

        // right
        if (col < this.n) {
            int rightIndex = index + 1;
            if (this.status[rightIndex] == 1) {
                this.uf.union(index, rightIndex);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return this.status[index] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        return isIndexFull(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < this.n; ++i) {
            int index = this.status.length - 1 - i;
            if (isIndexFull(index)) {
                return true;
            }
        }
        return false;
    }

    private void validate(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("outside its prescribed range");
        }
    }

    private int getIndex(int row, int col) {
        validate(row, col);
        return (row - 1) * this.n + (col - 1);
    }

    private boolean isIndexFull(int index) {
        if (this.status[index] == 0) {
            return false;
        }
        if (index < this.n) {
            return true;
        }

        int root = this.uf.find(index);
        for (int i = 0; i < this.n; ++i) {
            if (this.status[i] == 0) {
                continue;
            }

            if (this.uf.find(i) == root) {
                return true;
            }
        }
        return false;
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
            } catch (Exception ex) {
                StdOut.println(ex.getMessage());
            }
        }
    }
}
