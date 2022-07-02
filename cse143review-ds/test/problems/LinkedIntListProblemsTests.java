package problems;

import datastructures.LinkedIntList;
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

public class LinkedIntListProblemsTests extends BaseTest {

    @Test
    void reverse3_returnsCorrectList() {
        LinkedIntList list = new LinkedIntList(3, 4, 5);
        LinkedIntListProblems.reverse3(list);
        assertThat(list).containsExactly(5, 4, 3);
    }

    @Test
    void firstToLast_onEmptyList_returnsEmptyList() {
        LinkedIntList list = new LinkedIntList();
        LinkedIntListProblems.firstToLast(list);
        assertThat(list).isEmpty();
    }

    @Test
    void firstToLast_on1ElementList_returnsCorrectList() {
        LinkedIntList list = new LinkedIntList(42);
        LinkedIntListProblems.firstToLast(list);
        assertThat(list).containsExactly(42);
    }

    @Test
    void firstToLast_on2ElementList_returnsCorrectList() {
        LinkedIntList list = new LinkedIntList(42, 99);
        LinkedIntListProblems.firstToLast(list);
        assertThat(list).containsExactly(99, 42);
    }

    @Test
    void firstToLast_onMediumList_returnsCorrectList() {
        LinkedIntList list = new LinkedIntList(18, 4, 27, 9, 54, 5, 63);
        LinkedIntListProblems.firstToLast(list);
        assertThat(list).containsExactly(4, 27, 9, 54, 5, 63, 18);
    }

    @Test
    void firstToLast_onListWithDuplicates_returnsCorrectList() {
        LinkedIntList list = new LinkedIntList(3, 7, 3, 3);
        LinkedIntListProblems.firstToLast(list);
        assertThat(list).containsExactly(7, 3, 3, 3);
    }

    @Test
    void concatenate_onMediumLists_returnsCorrectList() {
        LinkedIntList a = new LinkedIntList(1, 2, 3);
        LinkedIntList b = new LinkedIntList(4, 5, 6);

        LinkedIntList output = LinkedIntListProblems.concatenate(a, b);
        assertThat(output).containsExactly(1, 2, 3, 4, 5, 6);

        assertThat(a).containsExactly(1, 2, 3);
        assertThat(b).containsExactly(4, 5, 6);
    }

    @Test
    void concatenate_onEmptyLists_returnsEmptyList() {
        LinkedIntList a = new LinkedIntList();
        LinkedIntList b = new LinkedIntList();
        LinkedIntList output = LinkedIntListProblems.concatenate(a, b);
        assertThat(output).isEmpty();

        assertThat(a).isEmpty();
        assertThat(b).isEmpty();
    }

    @Test
    void concatenate_onEmptyFirstList_returnsCorrectList() {
        LinkedIntList a = new LinkedIntList();
        LinkedIntList b = new LinkedIntList(1, 2, 3);
        LinkedIntList output = LinkedIntListProblems.concatenate(a, b);
        assertThat(output).containsExactly(1, 2, 3);

        assertThat(a).isEmpty();
        assertThat(b).containsExactly(1, 2, 3);
    }

    @Test
    void concatenate_onEmptySecondList_returnsCorrectList() {
        LinkedIntList a = new LinkedIntList(1, 2, 3);
        LinkedIntList b = new LinkedIntList();
        LinkedIntList output = LinkedIntListProblems.concatenate(a, b);
        assertThat(output).containsExactly(1, 2, 3);

        assertThat(a).containsExactly(1, 2, 3);
        assertThat(b).isEmpty();
    }
}
