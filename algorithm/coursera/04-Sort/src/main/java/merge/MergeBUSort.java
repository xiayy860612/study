package merge;

public class MergeBUSort {

    public void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        for (int sz = 1; sz < a.length; sz += sz) {
            for (int lo = 0; lo < a.length - sz; lo += 2*sz) {
                merge(a, lo, lo + sz - 1, Math.min(lo + 2 * sz - 1, a.length - 1), aux);
            }
        }
    }

    private void merge(Comparable[] a, int lo, int mid, int hi, Comparable[] aux) {
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <=hi; ++k) {
            aux[k] = a[k];
        }

        for (int k = lo; k <=hi; ++k) {
            if (i > mid) {
                a[k] = aux[j++];
                continue;
            }

            if (j > hi) {
                a[k] = aux[i++];
                continue;
            }

            a[k] = less(aux[j], aux[i]) ?
                aux[j++] : aux[i++];
         }
    }

    private boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

}
