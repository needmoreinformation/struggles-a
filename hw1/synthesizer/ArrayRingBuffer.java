package synthesizer;// TODO: Make sure to make this class a part of the synthesizer package
// package <package name>;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }


    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        fillCount += 1;
        rb[last] = x;

        if (last == capacity - 1) { /* Wrap around back to 0 */
            last = 0;
        } else {
            last += 1;
        }
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        int currentIndex;
        int itemsSeen;
        public ArrayRingBufferIterator() {
            currentIndex = first;
            itemsSeen = 0;
        }

        @Override
        public boolean hasNext() {
            return itemsSeen < fillCount;
        }

        @Override
        public T next() {
            T result = rb[currentIndex];
            if (currentIndex == capacity - 1) {
                currentIndex = 0;
            } else {
                currentIndex += 1;
            }
            itemsSeen += 1;
            return result;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T result = rb[first];
        rb[first] = null;
        if (first == capacity - 1) {
            first = 0;
        } else {
            first += 1;
        }

        fillCount -= 1;
        return result;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        T result = rb[first];
        return result;
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
