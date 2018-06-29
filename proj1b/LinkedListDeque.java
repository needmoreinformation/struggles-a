/**
 * No error checking.
 * @param <T>
 */
public class LinkedListDeque<T> implements Deque<T> {

    private int size;
    private ListNode sentinel;

    private class ListNode<T> {
        T item;
        ListNode next;
        ListNode prev;

        ListNode(T item) {
            this.item = item;
        }
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new ListNode<T>(null); /* Don't care what value inside node */
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void addFirst(T item) {
        ListNode<T> insertedNode = new ListNode<T>(item);
        ListNode<T> oldFirst = sentinel.next;

        insertedNode.prev = sentinel;
        insertedNode.next = sentinel.next;

        /* Make old first's previous point to new first */
        sentinel.next = insertedNode;
        oldFirst.prev = insertedNode;

        size += 1;
    }

    @Override
    public void addLast(T item) {
        ListNode<T> insertedNode = new ListNode<T>(item);
        ListNode<T> oldLast = sentinel.prev;

        insertedNode.next = sentinel;
        insertedNode.prev = oldLast;

        /* Update pointers: the last element is sentinel node's previous,
        due to circular topology. */
        oldLast.next = insertedNode;
        sentinel.prev = insertedNode;

        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        ListNode<T> currentNode = sentinel.next;

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
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        ListNode<T> first = sentinel.next;
        T result = first.item;

        /* Update pointers */
        first.next.prev = sentinel;
        sentinel.next = first.next;

        size -= 1;

        return result;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        ListNode<T> last = sentinel.prev;
        T result = last.item;

        /* Update pointers */
        last.prev.next = sentinel;
        sentinel.prev = last.prev;

        size -= 1;

        return result;
    }

    @Override
    public T get(int index) {
        ListNode<T> currentNode = sentinel.next;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.item;
    }

    private ListNode<T> getRecursiveHelper(int index, ListNode<T> node) {
        if (index == 0) {
            return node;
        }
        return getRecursiveHelper(index - 1, node.next);
    }

    /**
     * Gets an element at a given index position.
     * Implementation is recursive walkthrough of the linked list.
     * @param index index of the element
     * @return element at this position
     */
    public T getRecursive(int index) {
        ListNode<T> resultNode = getRecursiveHelper(index, sentinel.next);
        return resultNode.item;
    }

}
