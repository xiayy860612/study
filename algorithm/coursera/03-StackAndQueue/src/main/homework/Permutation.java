import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            queue.enqueue(input);
        }

        Iterator<String> iterator = queue.iterator();
        while (k > 0 && iterator.hasNext()) {
            StdOut.println(iterator.next());
            --k;
        }
    }
}
