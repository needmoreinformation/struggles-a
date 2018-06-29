/**
 *
 * @author Struggler A
 */
public class ArrayDeque<T> implements Deque<T> {

    private static final int DEFAULT_SIZE = 8;

    private int maxSize;
    private int currentSize;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    public ArrayDeque() {

        currentSize = 0;
        maxSize = DEFAULT_SIZE;
        items = (T[]) new Object[maxSize];
        int centre = (maxSize - 1) / 2;
        nextFirst = centre;
        nextLast = centre + 1;
    }

    private int getCircularIndex(int index) {
        if (index < 0) {
            index = index % maxSize;
        }
        return (index < 0) ? (index + maxSize) : index % maxSize;
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

        /* Move items in old array to new larger array */
        for (int i = 0; i < currentSize; i++) {
            int index = getCircularIndex(getCurrentFirst() + i);
            newItems[j] = items[index];
            j += 1;
        }

        /* Update pointers */
        nextFirst = centreOfQueue - 1;
        nextLast = j;
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

    @Override
    public void addFirst(T item) {

        /* Need to re-size array and then re-order if necessary */
        if (isFull()) {
            extendArray(maxSize * 2);
        }


        currentSize++;
        items[getCircularIndex(nextFirst)] = item;
        nextFirst -= 1;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            extendArray(maxSize * 2);
        }

        items[getCircularIndex(nextLast)] = item;
        currentSize++;
        nextLast += 1;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < currentSize; i++) {
            int index = getCircularIndex(getCircularIndex(getCurrentFirst() + i));
            System.out.print(items[index]);
            if (i < currentSize - 1) {
                System.out.print(" ");
            }
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int firstIndex = getCurrentFirst();
        T result = items[firstIndex];
        items[firstIndex] = null;
        nextFirst += 1;
        currentSize -= 1;

        if (maxSize > 16 && currentSize == maxSize / 4) {
            shrinkArray(maxSize / 2);
        }

        return result;
    }

    private int getCurrentFirst() {
        return getCircularIndex(nextFirst + 1);
    }

    private int getCurrentLast() {
        return getCircularIndex(nextLast - 1);
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int lastIndex = getCurrentLast();
        T result = items[lastIndex];
        items[lastIndex] = null;
        currentSize -= 1;
        nextLast -= 1;

        if (maxSize > 16 && currentSize == maxSize / 4) {
            shrinkArray(maxSize / 2);
        }

        return result;
    }

    @Override
    public T get(int index) {
        if (index < currentSize) {
            return items[getCircularIndex(getCurrentFirst() + index)];
        }

        return null;
    }

    @Override
    public int size() {
        return currentSize;
    }

}
