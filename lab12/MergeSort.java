import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> singleItemQueue = new Queue<Queue<Item>>();
        for (Item item : items) {
            Queue<Item> subqueue = new Queue<Item>();
            subqueue.enqueue(item);
            singleItemQueue.enqueue(subqueue);
        }
        return singleItemQueue;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {

        Queue<Item> sortedQueue = new Queue<Item>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            sortedQueue.enqueue(getMin(q1, q2));
        }

        return sortedQueue;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        int numElems = items.size();
        if (numElems <= 1) {
            return items;
        }

        /* Divide list into two halves */
        Queue<Item> copy = new Queue<>();
        for (Item item : items) {
            copy.enqueue(item);
        }

        Queue<Item> leftHalf = new Queue<>();
        Queue<Item> rightHalf = new Queue<>();

        for (int i = 0; i < numElems / 2; i += 1) {
            leftHalf.enqueue(copy.dequeue());
        }

        for (int i = (numElems / 2); i < numElems; i += 1) {
            rightHalf.enqueue(copy.dequeue());
        }

        leftHalf = mergeSort(leftHalf);
        rightHalf = mergeSort(rightHalf);
        Queue<Item> merged = mergeSortedQueues(leftHalf, rightHalf);

        return merged;
    }

    /************************** TESTING CLIENT ********************************/

    @Test
    public void testZeroLength() {
        Queue<Integer> testQueue = new Queue<>();
        Queue<Integer> sorted = MergeSort.mergeSort(testQueue);
        int[] expected = {};
        int[] actual = new int[testQueue.size()];
        int numElems = sorted.size();
        for (int i = 0; i < numElems; i++) {
            actual[i] = sorted.dequeue();
        }

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> numbers = new Queue<>();
        int[] numberArray = {5, 4, 7, 3, 3, 2, 2, 1, 5, 5};
        for (int elem : numberArray) {
            numbers.enqueue(elem);
        }

        Queue<Integer> expectedQueue = new Queue<>();
        int[] expected = {1, 2, 2, 3, 3, 4, 5, 5, 5, 7};

        Queue<Integer> sortedQueue = MergeSort.mergeSort(numbers);

        for (int elem : expected) {
            expectedQueue.enqueue(elem);
        }

        for (int i = 0; i < numberArray.length; i++) {
            numberArray[i] = sortedQueue.dequeue();
        }

        assertArrayEquals(numberArray, expected);
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");

        StdOut.println(students);
        Queue<String> sortedStudents = MergeSort.mergeSort(students);
        StdOut.println(students);
        StdOut.println(sortedStudents);

        Queue<Integer> lectureExample = new Queue<>();
        lectureExample.enqueue(32);
        lectureExample.enqueue(15);
        lectureExample.enqueue(2);
        lectureExample.enqueue(17);
        lectureExample.enqueue(19);
        lectureExample.enqueue(26);
        lectureExample.enqueue(41);
        lectureExample.enqueue(17);
        lectureExample.enqueue(19);

        StdOut.println(lectureExample);
        StdOut.println(MergeSort.mergeSort(lectureExample));
    }
}
