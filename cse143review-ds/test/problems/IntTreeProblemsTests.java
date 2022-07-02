package problems;

import datastructures.IntTree;
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

public class IntTreeProblemsTests extends BaseTest {

    @Test
    void depthSum_onEmptyTree_returns0() {
        IntTree tree = new IntTree(new Integer[0]);
        int output = IntTreeProblems.depthSum(tree);
        assertThat(output).isEqualTo(0);
    }

    @Test
    void depthSum_on1ElementTree_returnsCorrectValue() {
        IntTree tree = new IntTree(12);
        int output = IntTreeProblems.depthSum(tree);
        assertThat(output).isEqualTo(12);
    }

    @Test
    void depthSum_onHeight3Tree_returnsCorrectValue() {
        IntTree tree = new IntTree(9,
            7, 6,
            3, 2, null, 4,
            null, null, 5, null, null, null, null, 2);
        int output = IntTreeProblems.depthSum(tree);
        assertThat(output).isEqualTo(90);
    }

    @Test
    void depthSum_onHeight2Tree_returnsCorrectValue() {
        IntTree tree = new IntTree(3,
            5, 2,
            1, null, 4, 6);
        int output = IntTreeProblems.depthSum(tree);
        assertThat(output).isEqualTo(50);
    }

    @Test
    void depthSum_onTreeWithNegativeValues_returnsCorrectValue() {
        IntTree tree = new IntTree(19,
            47, 63,
            23, -2, null, 94,
            null, null, 55, null, null, null, null, 28);
        int output = IntTreeProblems.depthSum(tree);
        assertThat(output).isEqualTo(916);
    }

    @Test
    void depthSum_onSpindlyTree_returnsCorrectValue() {
        IntTree tree = new IntTree(2,
            null, 1,
            null, null, 7, 6,
            null, null, null, null, 4, null, null, 9,
            null, null, null, null, null, null, null, null, 3, 5, null, null, null, null, 8);
        int output = IntTreeProblems.depthSum(tree);
        assertThat(output).isEqualTo(175);
    }

    @Test
    void depthSum_onCompleteTree_returnsCorrectValue() {
        IntTree tree = new IntTree(1,
            2, 3,
            4, 5, 6, 7,
            8, 9, 10, 11, 12, 13, 14, 15);
        int output = IntTreeProblems.depthSum(tree);
        assertThat(output).isEqualTo(445);
    }

    @Test
    void removeLeaves_onEmptyTree_returnsEmptyTree() {
        IntTree tree = new IntTree();
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree();
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on1ElementTree_returnsEmptyTree() {
        IntTree tree = new IntTree(12);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree();
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on3HeightTree_returnsCorrectTree() {
        IntTree tree = new IntTree(7,
            3, 9,
            1, 4, 6, 8,
            null, null, null, null, null, null, null, 0);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(7,
            3, 9,
            null, null, null, 8);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on2HeightTree_returnsCorrectTree() {
        IntTree tree = new IntTree(7,
            3, 9,
            null, null, null, 8);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(7, null, 9);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on1HeightTree_returnsCorrectTree() {
        IntTree tree = new IntTree(7, null, 9);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(7);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on0HeightTree_returnsEmptyTree() {
        IntTree tree = new IntTree(7);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree();
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on3HeightRightSpindlyTree_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            5, 4,
            null, null, null, 1,
            null, null, null, null, null, null, 3, 2);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6,
            null, 4,
            null, null, null, 1);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on2HeightRightSpindlyTree_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            null, 4,
            null, null, null, 1);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6, null, 4);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on1HeightRightSpindlyTree_returnsCorrectTree() {
        IntTree tree = new IntTree(6, null, 4);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6);
        assertThat(tree).isEqualTo(expected);
    }

    // testing the same structure as above but mirrored horizontally (and with some different values)
    @Test
    void removeLeaves_on3HeightLeftSpindlyTree_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            5, 2,
            4, null, null, null,
            1, 3);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6,
            5, null,
            4);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on2HeightLeftSpindlyTree_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            5, null,
            4);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6, 5);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on1HeightLeftSpindlyTree_returnsCorrectTree() {
        IntTree tree = new IntTree(6, 5);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on3HeightTreeWithOnlyLeftChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(4,
            2, null,
            3, null, null, null,
            1);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(4,
            2, null,
            3);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on2HeightTreeWithOnlyLeftChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(4,
            2, null,
            3);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(4, 2);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on1HeightTreeWithOnlyLeftChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(4, 2);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(4);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on3HeightTreeWithOnlyRightChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(2,
            null, 3,
            null, null, null, 4,
            null, null, null, null, null, null, null, 1);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(2,
            null, 3,
            null, null, null, 4);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on2HeightTreeWithOnlyRightChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(2,
            null, 3,
            null, null, null, 4);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(2, null, 3);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on1HeightTreeWithOnlyRightChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(2, null, 3);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(2);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on5HeightTreeWithArbitrarySingleChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            5, null,
            null, 4, null, null,
            null, null, 3, null, null, null, null, null,
            null, null, null, null, null, 2, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, 1);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6,
            5, null,
            null, 4, null, null,
            null, null, 3, null, null, null, null, null,
            null, null, null, null, null, 2);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on4HeightTreeWithArbitrarySingleChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            5, null,
            null, 4, null, null,
            null, null, 3, null, null, null, null, null,
            null, null, null, null, null, 2);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6,
            5, null,
            null, 4, null, null,
            null, null, 3);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on3HeightTreeWithArbitrarySingleChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            5, null,
            null, 4, null, null,
            null, null, 3);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6,
            5, null,
            null, 4);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on2HeightTreeWithArbitrarySingleChildren_returnsCorrectTree() {
        IntTree tree = new IntTree(6,
            5, null,
            null, 4);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(6, 5);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on3HeightFullTree_returnsCorrectTree() {
        IntTree tree = new IntTree(1,
            2, 3,
            4, 5, 6, 7,
            8, 9, 10, 11, 12, 13, 14, 15);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(1,
            2, 3,
            4, 5, 6, 7);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on2HeightFullTree_returnsCorrectTree() {
        IntTree tree = new IntTree(1,
            2, 3,
            4, 5, 6, 7);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(1, 2, 3);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void removeLeaves_on1HeightFullTree_returnsCorrectTree() {
        IntTree tree = new IntTree(1, 2, 3);
        IntTreeProblems.removeLeaves(tree);
        IntTree expected = new IntTree(1);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_onEmptyTree_returnsEmptyTree() {
        IntTree tree = new IntTree();
        IntTreeProblems.trim(tree, 40, 1300);
        IntTree expected = new IntTree();
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_onHeight4TreeWithLesserAndGreaterValues_returnsCorrectTree() {
        IntTree tree = new IntTree(50,
            38, 90,
            14, 42, 54, null,
            8, 20, null, null, null, 72, null, null,
            null, null, null, 26, null, null, null, null, null, null, 61, 83);
        IntTreeProblems.trim(tree, 25, 75);
        IntTree expected = new IntTree(50,
            38, 54,
            26, 42, null, 72,
            null, null, null, null, null, null, 61);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_withMinLargerThanRoot_returnsCorrectTree() {
        IntTree tree = new IntTree(50,
            38, 90,
            14, 42, 54, null,
            8, 20, null, null, null, 72, null, null,
            null, null, null, 26, null, null, null, null, null, null, 61, 83);
        IntTreeProblems.trim(tree, 52, 65);
        IntTree expected = new IntTree(54, null, 61);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_withMaxLessThanRoot_returnsCorrectTree() {
        IntTree tree = new IntTree(50,
            38, 90,
            14, 42, 54, null,
            8, 20, null, null, null, 72, null, null,
            null, null, null, 26, null, null, null, null, null, null, 61, 83);
        IntTreeProblems.trim(tree, 18, 42);
        IntTree expected = new IntTree(38,
            20, 42,
            null, 26);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_withMaxLessThanAllValues_returnsEmptyTree() {
        IntTree tree = new IntTree(50,
            38, 90,
            14, 42, 54, null,
            8, 20, null, null, null, 72, null, null,
            null, null, null, 26, null, null, null, null, null, null, 61, 83);
        IntTreeProblems.trim(tree, -90, -50);
        IntTree expected = new IntTree();
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_withMinGreaterThanAllValues_returnsEmptyTree() {
        IntTree tree = new IntTree(50,
            38, 90,
            14, 42, 54, null,
            8, 20, null, null, null, 72, null, null,
            null, null, null, 26, null, null, null, null, null, null, 61, 83);
        IntTreeProblems.trim(tree, 120, 500);
        IntTree expected = new IntTree();
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_withMinEqualToMax_returnsCorrectTree() {
        IntTree tree = new IntTree(50,
            38, 90,
            14, 42, 54, null,
            8, 20, null, null, null, 72, null, null,
            null, null, null, 26, null, null, null, null, null, null, 61, 83);
        IntTreeProblems.trim(tree, 50, 50);
        IntTree expected = new IntTree(50);
        assertThat(tree).isEqualTo(expected);
    }

    @Test
    void trim_withLargeRange_returnsCorrectTree() {
        IntTree tree = new IntTree(50,
            38, 90,
            14, 42, 54, 1100,
            null, null, null, null, null, null, 99, 1500,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, 1250);
        IntTreeProblems.trim(tree, 40, 1300);
        IntTree expected = new IntTree(50,
            42, 90,
            null, null, 54, 1100,
            null, null, null, null, null, null, 99, 1250);
        assertThat(tree).isEqualTo(expected);
    }
}
