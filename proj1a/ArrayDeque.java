/**
 *
 * @author Struggler A
 */
public class ArrayDeque<T> {

    private static final int DEFAULT_SIZE = 8;

    private int maxSize = DEFAULT_SIZE;
    private int currentSize = 0;
    private int first = maxSize / 2; /* Floor integer division - start at middle. */
    private T items[];

    public ArrayDeque() {
        items = (T[]) new Object[maxSize];
    }

    private int getCircularIndex(int index) {
        return (index < 0) ? (index + maxSize) : index % maxSize;
    }

    private int getLastFreeIndex() {
        return getCircularIndex( getCircularIndex(first) + currentSize);
    }

    private boolean isFull() {
        return currentSize == maxSize;
    }

    /**
     * @requires currentSize <= newSize
     * @param newSize
     */
    private void extendArray(int newSize) {
        T[] newItems = (T[]) new Object[newSize];

        /* Start placing items from the centre */
        int centreOfQueue = (newSize - 1) / 2;
        int j = centreOfQueue;
        int newFirst = j;

        /* Move items in old array to new larger array */
        for (int i = 0; i < currentSize; i++) {
            int index = getCircularIndex(first + i);
            newItems[j] = items[index];
            j++;
        }

        /* Update pointers */
        first = newFirst;
        items = newItems;
        maxSize = newSize;
    }

    /**
     * Shrinks the items array.
     * @requires newSize < maxSize
     * @param newSize
     */
    private void shrinkArray(int newSize) {
        extendArray(newSize);
    }

    public void addFirst(T item) {

        /* Need to re-size array and then re-order if necessary */
        if (isFull()) {
            extendArray(maxSize * 2);
        }


        currentSize++;
        items[getCircularIndex(first)] = item;
        first--;
    }

    public void addLast(T item) {
        if (isFull()) {
            extendArray(maxSize * 2);
        }

        items[getCircularIndex(getLastFreeIndex() + 1)] = item;
        currentSize++;

    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void printDeque() {
        for (int i = 0; i < currentSize; i++) {
            int index = getCircularIndex(first + i);
            System.out.print(items[index]);
            if (i < currentSize - 1) {
                System.out.print(" ");
            }
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int firstIndex = getCircularIndex(first + 1);
        T result = items[firstIndex];
        items[firstIndex] = null;
        first += 1;
        currentSize -= 1;

        if (maxSize > 16 && currentSize == maxSize / 4) {
            shrinkArray(maxSize / 2);
        }

        return result;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int lastIndex = getLastFreeIndex();
        T result = items[lastIndex];
        items[lastIndex] = null;
        currentSize -= 1;


        if (maxSize > 16 && currentSize == maxSize / 4) {
            shrinkArray(maxSize / 2);
        }

        return result;
    }

    public T get(int index) {
        if (index < currentSize) {
            return items[getCircularIndex(first + index)];
        }

        return null;
    }

    public int size() {
        return currentSize;
    }

}