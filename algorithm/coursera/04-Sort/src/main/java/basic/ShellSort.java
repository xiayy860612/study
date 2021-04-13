package basic;

public class ShellSort implements SortExample {
    @Override
    public void sort(Comparable[] a) {
        int h = 1;
        while (h < a.length/3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < a.length; ++i) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j-=h) {
                    exch(a, j, j - h);
                }
            }
            h = h/3;
        }
    }

    public static void main(String[] args) {
        String input = "sortexample";
        String[] a = new String[input.length()];
        int index = 0;
        for (char c : input.toCharArray()) {
            a[index++] = String.valueOf(c);
        }

        ShellSort sort = new ShellSort();
        sort.sort(a);
        assert sort.isSorted(a);
        sort.show(a);
    }
}
