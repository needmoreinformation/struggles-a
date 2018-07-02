package synthesizer;

/**
 * Purpose: provide a protected fillCount and capacity variable that all
 * subclasses will inherit. As well as so-called 'getter' methods
 * for these variables.
 * @param <T>
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    /* Gets inherited from BoundedQueue.
    public boolean isEmpty();
    public boolean isFull();
    public abstract T peek();
    public abstract T dequeue();
    public abstract void enqueue(T x);
    */
}
