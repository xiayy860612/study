package basic;

public class SelectSort implements SortExample {

    @Override
    public void sort(Comparable[] a) {
        for (int i = 0; i < a.length; ++i) {
            int min = i;
            for (int j = i + 1; j < a.length; ++j) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            exch(a, i, min);
        }
    }

    public static void main(String[] args) {
        String input = "sortexample";
        String[] a = new String[input.length()];
        int index = 0;
        for (char c : input.toCharArray()) {
            a[index++] = String.valueOf(c);
        }
        SelectSort sort = new SelectSort();
        sort.sort(a);
        assert sort.isSorted(a);
        sort.show(a);
    }
}
