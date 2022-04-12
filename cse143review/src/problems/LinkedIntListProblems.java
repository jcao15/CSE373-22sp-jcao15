package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        System.out.println(list);
        list.front.next.next.next = list.front;
        System.out.println(list);
        list.front = list.front.next.next;
        System.out.println(list);
        list.front.next.next.next = list.front.next;
        System.out.println(list);
        list.front.next = list.front.next.next;
        System.out.println(list);
        list.front.next.next.next = null;
        System.out.println(list);

    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {

        if (list.front != null) {
            ListNode temp = list.front;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = list.front;
            list.front = list.front.next;
            temp.next.next = null;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        if (a.front != null && b.front != null) {
            LinkedIntList ans = new LinkedIntList(a.front.data);

            ListNode temp1 = a.front.next;
            ListNode temp2 = ans.front;
            ListNode temp3 = b.front;

            while (temp1 != null) {
                temp2.next = new ListNode(temp1.data);
                temp1 = temp1.next;
                temp2 = temp2.next;
            }
            while (temp3 != null) {
                temp2.next = new ListNode(temp3.data);
                temp3 = temp3.next;
                temp2 = temp2.next;
            }
            return ans;
        } else if (a.front != null) {
            return a;
        } else if (b.front != null) {
            return b;
        } else {
            return new LinkedIntList();
        }
    }
}
