import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * O(n^2) for N objects union
 */
public class QuickFindUF implements UnionFind {
    private final int[] id;
    private int count;

    public QuickFindUF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < id.length; ++i) {
            id[i] = i;
        }
    }

    /**
     * O(n)
     */
    @Override
    public void union(int p, int q) {
        // 2 * O(1)
        int rootP = id[p];
        int rootQ = id[q];
        if (rootP == rootQ) {
            return;
        }

        // O(n)
        for (int i = 0; i < id.length; ++i) {
            if (id[i] == rootP) {
                id[i] = rootQ;
            }
        }
        --count;
    }

    /**
     * O(1)
     */
    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    @Override
    public int count() {
        return count;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}

/*
10
4 3
3 8
6 5
9 4
2 1
8 9
5 0
7 2
6 1
1 0
6 7

 */