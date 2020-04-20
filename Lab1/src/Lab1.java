import java.util.Arrays;
import java.util.Random;

public class Lab1 {
    /** Sorting algorithms **/

    // Insertion sort.

    //Improve to remove swapping?
    public static void insertionSort(int[] array) {
        //Traverse array, sorting one element at a time
        for(int i = 1; i < array.length; i++){
            int elem = array[i];
            //Backtrack from selected element over sorted portion
            for(int j = i-1; j >= 0; j--){
                if(array[j] > elem) {
                    array[j+1] = array[j];
                    array[j] = elem;
                } else {
                    array[j+1] = elem;
                    break;
                }
            }
        }
    }

    // Quicksort.
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length-1);
    }

    // Quicksort part of an array
    private static void quickSort(int[] array, int begin, int end) {
        if(end-begin > 0) {
            int mid = (end+begin)/2;
            swap(array, begin, mid); //put pivot at start of current portion
            int pivot = partition(array, begin, end);
            swap(array, begin, pivot); //put pivot into correct place
            quickSort(array, begin, pivot-1);
            quickSort(array, pivot+1, end);
        }
    }

    // Partition part of an array, and return the index where the pivot
    // ended up.
    // Precondition: desired Pivot-element is placed at index 'begin'
    private static int partition(int[] array, int begin, int end) {
        if(end-begin > 0) {
            //Partitioning if longer than 1 elem
            int start = begin;
            int last = end;
            int pivot = array[begin];
            begin++;
            while(begin < end) {
                while(array[begin] < pivot && begin < last) {
                    begin++;
                }
                while(array[end] > pivot && end > start) {
                    end--;
                }
                if(begin < end) {
                    swap(array, begin, end);
                    begin++;
                    end--;
                }
            }
            if(array[end] > pivot) {
                end--;
            }
        }
        return end;
    }

    // Swap two elements in an array
    private static void swap(int[] array, int i, int j) {
        int x = array[i];
        array[i] = array[j];
        array[j] = x;
    }

    // Mergesort.

    public static int[] mergeSort(int[] array) {
        if(array.length <= 1) {
            return array; //Singleton and empty array
        }
        int mid = array.length/2;
        return(merge(mergeSort(array, 0, mid), mergeSort(array, mid+1, array.length-1)));
    }

    // Mergesort part of an array
    private static int[] mergeSort(int[] array, int begin, int end) {
        if(array.length <= 1) {
            return array; //Singleton and empty array
        }
        if(end-begin == 0) {
            int[] sorted = new int[] {array[end]};
            return sorted;
        }
        int mid = (end+begin)/2;
        return(merge(mergeSort(array, begin, mid), mergeSort(array, mid+1, end)));
    }

    // Merge two sorted arrays into one
    private static int[] merge(int[] left, int[] right) {
        if(left.length == 0) {
            return right;
        } else if(right.length == 0) {
            return left;
        } else {
            int total = left.length + right.length; //length of merged array
            int[] merged = new int[total];
            //counters for left and right respectively
            int j = 0;
            int k = 0;
            for(int i = 0; i < total; i++) {
                if(k == right.length || (j <= left.length-1 && left[j] < right[k])) {
                    merged[i] = left[j];
                    j++;
                } else if(j == left.length || (k <= right.length-1 && right[k] <= left[j])){
                    merged[i] = right[k];
                    k++;
                }
            }
            return merged;
        }
    }

    public static void main(String[] args) {
        int[] sample = new int[] {4, 2, 5, 1, 6, 23, 5, 99, 3, 12, 51, 300, 230, 1, 5, 3, 19, 20, 1338, 99, 91, 0, 22, 41, 2};
//        int[] sample = new int[] {0,2};
//        int[] sample = new int[] {2,0};
//        int[] sample = new int[] {1, 1, 1, 1, 7, 1, 1, 1, 1};

        System.out.println("UNSORTED");
        System.out.println(Arrays.toString(sample));
        sample = mergeSort(sample);
        System.out.println("SORTED");
        System.out.println(Arrays.toString(sample));
    }
}
