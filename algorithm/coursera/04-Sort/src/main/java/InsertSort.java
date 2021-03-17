public class InsertSort implements SortExample {
    @Override
    public void sort(Comparable[] a) {
        for (int i = 1; i < a.length; ++i) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); --j) {
                exch(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) {
        String input = "sortexample";
        String[] a = new String[input.length()];
        int index = 0;
        for (char c : input.toCharArray()) {
            a[index++] = String.valueOf(c);
        }

        InsertSort sort = new InsertSort();
        sort.sort(a);
        assert sort.isSorted(a);
        sort.show(a);
    }
}
