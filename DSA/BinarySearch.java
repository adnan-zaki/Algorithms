public class BinarySearch {
    public static void main(String[] args) {
        int[] nums = {2, 5, 7, 9, 13, 21};
        int target = 9;
        int index = binarySearch(nums, target);
        System.out.println("Index: " + index);
    }

    static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
