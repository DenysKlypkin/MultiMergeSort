package klipkin;

public class ParallelMergeSort extends Thread {


    private static int low;
    private static int high;
    public static int[] arr;
    private static int numberOfThreadLevels;


    public ParallelMergeSort(int[] arr, int low, int high, int numberOfThreadLevels) {
        ParallelMergeSort.high = high;
        ParallelMergeSort.low = low;
        ParallelMergeSort.arr = arr;
        ParallelMergeSort.numberOfThreadLevels = numberOfThreadLevels;
    }

    @Override
    public void run() {
        mergeSort(arr, numberOfThreadLevels);
    }


    public static void mergeSort(int[] arr, int numberOfThreadLevels) {

        if (arr.length > 1) {
            int mid = (arr.length) / 2;
            int[] links = new int[mid];
            for (int i = 0; i <= mid - 1; i++) {
                links[i] = arr[i];
            }

            int[] rechts = new int[arr.length - mid];
            for (int i = mid; i <= arr.length - 1; i++) {
                rechts[i - mid] = arr[i];
            }

            if (numberOfThreadLevels > 0) {
                ParallelMergeSort parallelMergeSortOne =
                        new ParallelMergeSort(arr, low, mid - 1, numberOfThreadLevels / 2);
                parallelMergeSortOne.start();


                ParallelMergeSort parallelMergeSortTwo =
                        new ParallelMergeSort(arr, mid, high, numberOfThreadLevels / 2);
                parallelMergeSortTwo.start();

                try {
                    parallelMergeSortOne.join();
                    parallelMergeSortTwo.join();
                } catch (InterruptedException e) {
                    //todo
                    e.printStackTrace();
                }


            } else {
                merge(ParallelMergeSort.arr, ParallelMergeSort.low, mid, ParallelMergeSort.high);
            }


        } else {
            ParallelMergeSort.arr = arr;
        }


    }


    private static void merge(int arr[], int l, int m, int r) {

        int n1 = m - l + 1;
        int n2 = r - m;


        int L[] = new int[n1];
        int R[] = new int[n2];


        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];


        int i = 0, j = 0;


        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }


        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }


        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }


}