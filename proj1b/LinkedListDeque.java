/**
 * Concrete implementation of the deque ADT.
 * @param <Item> generic over this type.
 * @author Struggler A
 */
public class LinkedListDeque<Item> implements Deque<Item> {
    /** Current size of deque. */
    private int size;

    /** Sentinel node to make adding/removing code simpler. */
    private ListNode sentinel;

    /** Class representing a linked list node. */
    class ListNode<Item> {
        /** element contained in this node. */
        private Item item;

        /** Next node. */
        private ListNode next;

        /** Previous node. */
        private ListNode prev;

        /**
         * Constructor for a node.
         * @param item element contained in this node.
         */
        public ListNode(Item item) {
            this.item = item;
        }
    }

    /**
     * Construct for this deque.
     */
    public LinkedListDeque() {
        size = 0;

        /* Don't care what value the sentinel node contains. */
        sentinel = new ListNode<Item>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * Adds an item to the front of the deque.
     * @param item item to be added.
     */
    @Override
    public void addFirst(Item item) {
        ListNode<Item> insertedNode = new ListNode<Item>(item);
        ListNode<Item> oldFirst = sentinel.next;

        insertedNode.prev = sentinel;
        insertedNode.next = sentinel.next;

        /* Make old first's previous point to new first */
        sentinel.next = insertedNode;
        oldFirst.prev = insertedNode;

        size += 1;
    }

    /**
     * Adds an item to the end of the deque.
     * @param item item to be added.
     */
    @Override
    public void addLast(Item item) {
        ListNode<Item> insertedNode = new ListNode<Item>(item);
        ListNode<Item> oldLast = sentinel.prev;

        insertedNode.next = sentinel;
        insertedNode.prev = oldLast;

        /* Update pointers: the last element is sentinel node's previous,
         * due to circular topology. */
        oldLast.next = insertedNode;
        sentinel.prev = insertedNode;

        size += 1;
    }

    /**
     * Returns true if this deque contains no item.
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in this deque.
     * @return the number of elements in this deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints out the elements of the deque separated by a space and a new line at the end.
     * e.g. {0, 1, 2} would print as "0 1 2" with a new line at the end.
     */
    @Override
    public void printDeque() {
        ListNode<Item> currentNode = sentinel.next;

        while (currentNode != sentinel) {
            System.out.print(currentNode.item);
            if (currentNode.next != sentinel) {
                System.out.print(" ");
            }
            currentNode = currentNode.next;
        }
        System.out.println();
    }

    @Override
    public Item removeFirst() {
        ListNode<Item> first = sentinel.next;
        Item result = first.item;

        /* Update pointers */
        first.next.prev = sentinel;
        sentinel.next = first.next;

        size -= 1;

        return result;
    }

    @Override
    public Item removeLast() {
        ListNode<Item> last = sentinel.prev;
        Item result = last.item;

        /* Update pointers */
        last.prev.next = sentinel;
        sentinel.prev = last.prev;

        size -= 1;

        return result;
    }

    @Override
    public Item get(int index) {
        ListNode<Item> currentNode = sentinel.next;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.item;
    }

    /**
     * Recursive helper for getRecursive. Base case is when the index counter reaches 0,
     * which means the deque has been traversed index times from the first element.
     * @param index helper argument for recursion.
     * @param node current node the traversal points to.
     * @return node to be returned.
     */
    private ListNode<Item> getRecursive(int index, ListNode<Item> node) {
        if (index == 0) {
            return node;
        }
        return getRecursive(index - 1, node.next);
    }

    /**
     * Gets an item located at position first + index. The first element would be 0, and the final
     * element size() - 1.
     * @param index index of item to retrieve.
     * @return reference to item at index position within deque.
     */
    public Item getRecursive(int index) {
        ListNode<Item> result = getRecursive(index, sentinel.next);
        return result.item;
    }

    /**
     * Main method.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Deque<Character> word = new LinkedListDeque<Character>();
        word.addLast('t');
        word.addLast('e');
        word.addLast('s');
        word.addLast('t');

        word.removeFirst();
        word.removeLast();
        word.removeFirst();
        word.removeLast();
    }

}
