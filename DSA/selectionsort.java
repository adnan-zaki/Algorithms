// Selection Sort implementation in Java. 
//  Takes a list of integers as input from the user, sorts them using selection sort algorithm, and prints the sorted array.

import java.util.*;
public class selectionsort {
      
    public static void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            if (min != i) {
                int tmp = a[i];
                a[i] = a[min];
                a[min] = tmp;
            }
        }
    }

    public static void main(String[] args) {

        int[] arr;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter numbers to sort, separated by spaces or commas:");
        String input = scanner.nextLine();
        // split on commas or whitespace (one or more), handles inputs like "1 2 3", "1,2,3" or "1, 2,3 4"
        String[] tokens = input.trim().split("[,\\s]+");
        List<Integer> numbers = new ArrayList<>();
        for (String t : tokens) {
            if (t == null || t.trim().isEmpty()) continue;
            try {
                numbers.add(Integer.parseInt(t));
            } catch (NumberFormatException e) {
                System.err.println("Warning: invalid integer '" + t + "' - skipping");
            }
        }
        if (numbers.isEmpty()) {
            System.out.println("No valid integers entered. Exiting.");
            scanner.close();
            return;
        }
        arr = numbers.stream().mapToInt(Integer::intValue).toArray();
        selectionSort(arr);
        System.out.println("Sorted Array : " + Arrays.toString(arr));
        scanner.close();
    }
}
