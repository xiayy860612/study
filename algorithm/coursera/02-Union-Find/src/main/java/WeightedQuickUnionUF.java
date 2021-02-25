import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionUF implements UnionFind {
    private final int[] ids;
    private final int[] size;
    private int count;

    public WeightedQuickUnionUF(int n) {
        count = n;
        ids = new int[n];
        size = new int[n];
        for (int i = 0; i < ids.length; ++i) {
            ids[i] = i;
            size[i] = 1;
        }
    }

    /**
     * O(logN)
     */
    @Override
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) {
            return;
        }

        // flat the depth
        if (size[rootP] > size[rootQ]) {
            ids[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            ids[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        --count;
    }

    /**
     * O(logN)
     */
    private int root(int p) {
        while (p != ids[p]) {
            // path compression improvement
            ids[p] = ids[ids[p]];
            p = ids[p];
        }
        return p;
    }

    /**
     * 2 * O(m) => O(m)
     * m <= n, O(m) <= O(n)
     */
    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public int count() {
        return count;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
