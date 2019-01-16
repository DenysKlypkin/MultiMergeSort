package klipkin;

import java.util.Random;

/**
 * Created by Andrii on 16.01.2019
 */
public class ParallelMergeSortLast {
    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        assert isSorted(a, mid + 1, hi); // precondition: a[mid+1..hi] sorted
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted
    }

    private static void sort(int[] a, int[] aux, int lo, int hi, int numberOfThreadLevel) {
        if (hi <= lo) return;
        else {
            int mid = lo + (hi - lo) / 2;
            if(numberOfThreadLevel>0){
                Thread left = new Thread(new Runnable() {
                    public void run() {
                        System.out.println("Left "+numberOfThreadLevel/2);
                        ParallelMergeSortLast.sort(a, aux, lo, mid,numberOfThreadLevel/2);
                    }
                });
                Thread right = new Thread(new Runnable() {
                    public void run() {
                        System.out.println("Right "+numberOfThreadLevel/2);
                        ParallelMergeSortLast.sort(a, aux, mid + 1, hi, numberOfThreadLevel/2);
                    }
                });
                left.start();
                right.start();
                try {
                    left.join();
                    right.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                sort(a, aux, lo, mid,numberOfThreadLevel);
                sort(a, aux, mid + 1, hi, numberOfThreadLevel);
            }
            if (!less(a[mid + 1], a[mid])) return;
            merge(a, aux, lo, mid, hi);
        }
    }

    public static void mergeSort(int[] a, int numberOfThreadLevels) {
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1, numberOfThreadLevels);
    }

    private static boolean isSorted(int[] a, int l, int m) {
        for (int i = l; i <= m; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    private static boolean less(int v, int w) {
        if (v < w)
            return true;
        return false;
    }

    private static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {

        int N = 0;
        if (args != null && args.length > 0)
            N = Integer.parseInt(args[0]);
        else N = 500;
        Random random = new Random();
        int[] a = new int[N];
        for (int i = 0; i < N; i++)
            a[i] = random.nextInt(N);
        int numberOfThreadLevels = a.length / 2;
        printArray(a);
        mergeSort(a, numberOfThreadLevels);
        printArray(a);

        System.out.println(isSorted(a));
    }
}
