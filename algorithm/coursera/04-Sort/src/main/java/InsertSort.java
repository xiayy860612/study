public class InsertSort implements SortExample {
    @Override
    public void sort(Comparable[] a) {
        for (int i = 1; i < a.length; ++i) {
            Comparable obj = a[i];
            int index = i;
            while (index > 0 && less(obj, a[index-1])) {
                a[index] = a[index-1];
                --index;
            }
            a[index] = obj;
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
