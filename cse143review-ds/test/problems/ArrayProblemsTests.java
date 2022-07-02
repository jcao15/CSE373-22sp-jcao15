package problems;

import edu.washington.cse373.BaseTest;
import org.junit.jupiter.api.Test;

/*
 * WARNING: These tests determine your grade for this assignment.
 * You don't need to change these tests, but it may be useful to temporarily add extra
 * printing or code for debugging.
 *
 * If you do modify this file, remember to revert your changes afterwards.
 *
 * (If you make changes that affect the results of the tests below, you might trick yourself into
 * thinking your code is correct when it really isn't, so please be careful about changing things.)
 */

public class ArrayProblemsTests extends BaseTest {

    @Test
    void rotateRight_onMediumInput_returnsCorrectOrdering() {
        int[] array = {3, 8, 19, 7};
        ArrayProblems.rotateRight(array);
        assertThat(array).containsExactly(7, 3, 8, 19);
    }

    @Test
    void rotateRight_onLargeInput_returnsCorrectOrdering() {
        int[] array = {10, 20, 30, 40, 50, 60, 70};
        ArrayProblems.rotateRight(array);
        assertThat(array).containsExactly(70, 10, 20, 30, 40, 50, 60);
    }

    @Test
    void rotateRight_onSmallInput_returnsCorrectOrdering() {
        int[] array = {100, 200};
        ArrayProblems.rotateRight(array);
        assertThat(array).containsExactly(200, 100);
    }

    @Test
    void rotateRight_onSingleInput_returnsCorrectValue() {
        int[] array = {42};
        ArrayProblems.rotateRight(array);
        assertThat(array).containsExactly(42);
    }

    @Test
    void rotateRight_onEmptyInput_returnsEmpty() {
        int[] array = {};
        ArrayProblems.rotateRight(array);
        assertThat(array).isEmpty();
    }

    @Test
    void reverse_onLargeInput_returnsCorrectOrdering() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[] reversed = ArrayProblems.reverse(array);

        assertThat(reversed).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1);
        assertThat(array).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void reverse_onMediumInput_returnsCorrectOrdering() {
        int[] array = {1, 2, 3};

        int[] reversed = ArrayProblems.reverse(array);

        assertThat(reversed).containsExactly(3, 2, 1);
        assertThat(array).containsExactly(1, 2, 3);
    }

    @Test
    void reverse_onSingleInput_returnsCorrectValue() {
        int[] array = {1};

        int[] reversed = ArrayProblems.reverse(array);

        assertThat(reversed).containsExactly(1);
        assertThat(array).containsExactly(1);
    }

    @Test
    void reverse_onEmptyInput_returnsEmpty() {
        int[] array = {};

        int[] reversed = ArrayProblems.reverse(array);

        assertThat(reversed).isEmpty();
        assertThat(array).isEmpty();
    }

    @Test
    void toString_onLargeInput_returnsCorrectString() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String arrayString = ArrayProblems.toString(array);
        assertThat(arrayString).isEqualTo("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
    }

    @Test
    void toString_onMediumInput_returnsCorrectString() {
        int[] array = {5, 7, 123, 12};
        String arrayString = ArrayProblems.toString(array);
        assertThat(arrayString).isEqualTo("[5, 7, 123, 12]");
    }

    @Test
    void toString_onSingleInput_returnsCorrectString() {
        int[] array = {1};
        String arrayString = ArrayProblems.toString(array);
        assertThat(arrayString).isEqualTo("[1]");
    }

    @Test
    void toString_onEmptyInput_returnsCorrectString() {
        int[] array = {};
        String arrayString = ArrayProblems.toString(array);
        assertThat(arrayString).isEqualTo("[]");
    }
}
