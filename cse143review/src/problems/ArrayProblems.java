package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {

        StringBuilder res = new StringBuilder("[");

        if (array.length > 0) {
            for (int i = 0; i < array.length - 1; i++) {
                res.append(array[i]).append(", ");
            }
            res.append(array[array.length - 1]);
        }
        res.append("]");

        return res.toString();

    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {

        int[] res = array.clone();
        int l = 0;
        int r = array.length - 1;

        while (l <= r) {
            int temp = res[l];
            res[l] = res[r];
            res[r] = temp;
            l++;
            r--;
        }
        return res;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        int lastIdx = array.length - 1;

        for (int i = 0; i < array.length; i++) {
            int temp = array[i];
            array[i] = array[lastIdx];
            array[lastIdx] = temp;
        }
    }
}
