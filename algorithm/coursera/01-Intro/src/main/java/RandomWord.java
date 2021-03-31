import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int index = 1;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            boolean result = StdRandom.bernoulli(1.0/index);
            if (result) {
                champion = input;
            }
            ++index;
        }
        StdOut.println(champion);
    }
}
