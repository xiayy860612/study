package merge;

public class MergeSort {

    public void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];

        sort(aux, a, 0, a.length - 1);
    }

    private void sort(Comparable[] aux, Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo)/2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);
        merge(a, lo, mid, hi, aux);
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
