import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnionUF implements UnionFind {
    private final int[] ids;
    private int count;

    public QuickUnionUF(int n) {
        count = n;
        ids = new int[n];
        for (int i = 0; i < ids.length; ++i) {
            ids[i] = i;
        }
    }

    /**
     * 2 * O(m) + O(1) => O(m)
     * m <= n, O(m) <= O(n)
     */
    @Override
    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) {
            return;
        }

        ids[rootP] = rootQ;
        --count;
    }

    /**
     * O(n) for worst case
     */
    private int root(int p) {
        while (p != ids[p]) {
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
        QuickUnionUF uf = new QuickUnionUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
