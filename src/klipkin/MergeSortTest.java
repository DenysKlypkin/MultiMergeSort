package klipkin;

public class MergeSortTest {
    public static void main(String[] args) {
        ParallelMergeSort parallelMergeSort = new ParallelMergeSort(new int[]{1, 2, 5, 3, 10}, 4, 0, 2);

        ParallelMergeSort.mergeSort(new int[]{1, 2, 5, 3, 10}, 4);

        for (int a : ParallelMergeSort.arr
                ) {
            System.out.println(a);
        }


    }
}
