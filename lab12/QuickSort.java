import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {

        for (Item item : unsorted) {
            int cmp = item.compareTo(pivot);
            if (cmp < 0) {
                less.enqueue(item);
            } else if (cmp == 0) {
                equal.enqueue(item);
            } else if (cmp > 0) {
                greater.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {

        if (items.size() <= 0) {
            return items;
        }

        Queue<Item> unsorted = new Queue<Item>();

        for (Item item : items) {
            unsorted.enqueue(item);
        }

        Queue<Item> less = new Queue<Item>();
        Queue<Item> equal = new Queue<Item>();
        Queue<Item> greater = new Queue<Item>();
        Item pivot = getRandomItem(unsorted);

        partition(unsorted, pivot, less, equal, greater);
        less = quickSort(less);
        greater = quickSort(greater);

        Queue<Item> sortedItems = new Queue<>();
        for (Item item : less) {
            sortedItems.enqueue(item);
        }
        for (Item item : equal) {
            sortedItems.enqueue(item);
        }
        for (Item item : greater) {
            sortedItems.enqueue(item);
        }

        return sortedItems;
    }

    /************************** TESTING CLIENT ********************************/

    @Test
    public void testZeroLength() {
        Queue<Integer> testQueue = new Queue<>();
        Queue<Integer> sorted = quickSort(testQueue);
        int[] expected = {};
        int[] actual = new int[testQueue.size()];
        int numElems = sorted.size();
        for (int i = 0; i < numElems; i++) {
            actual[i] = sorted.dequeue();
        }

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testSorting() {
        Queue<Integer> numbers = new Queue<>();
        int[] numberArray = {5, 4, 7, 3, 3, 2, 2, 1, 5, 5};
        for (int elem : numberArray) {
            numbers.enqueue(elem);
        }

        Queue<Integer> expectedQueue = new Queue<>();
        int[] expected = {1, 2, 2, 3, 3, 4, 5, 5, 5, 7};

        Queue<Integer> sortedQueue = quickSort(numbers);
        StdOut.println(sortedQueue);
        for (int elem : expected) {
            expectedQueue.enqueue(elem);
        }

        for (int i = 0; i < numberArray.length; i++) {
            numberArray[i] = sortedQueue.dequeue();
        }


        assertArrayEquals(numberArray, expected);

        /* Lab example */
        Queue<String> students = arrayToQueue(new String[]{"Alice", "Vanessa", "Ethan"});
        StdOut.println(students);

        Queue<String> sortedStudents = quickSort(students);
        StdOut.println(sortedStudents);

        /* Lecture example */
        Queue<Integer> lectureExample = arrayToQueue(
                new Integer[]{32, 15, 2, 17, 19, 26, 41, 17, 19});
        Queue<Integer> sortedLectureExample = quickSort(lectureExample);
        Integer[] expectedLectureExample = new Integer[]{2, 15, 17, 17, 19, 19, 26, 32, 41};

        StdOut.println(sortedLectureExample);
    }

    private static <Item extends Comparable> Queue<Item> arrayToQueue(Item[] input) {
        Queue<Item> queue = new Queue<>();
        for (Item item : input) {
            queue.enqueue(item);
        }
        return queue;
    }


    public static void main(String[] args) {

    }
}
