package material.lineal.queue;

/**
 * Created by Alejandro on 7/4/16.
 */
public class ArrayQueue <E> implements Queue<E>{

    private int top;
    private E[]q;

    /**
     * Constructor of the class with the initial capacity
     *
     * @param maxSize Maximum size of the stack.
     */
    public ArrayQueue(int maxSize) {
        top = 0;
        q = (E[]) new Object[maxSize];
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return number of elements in the queue.
     */
    @Override
    public int size() {
        return top;
    }

    /**
     * Returns whether the queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return top == 0;
    }

    /**
     * Inspects the element at the front of the queue.
     *
     * @return element at the front of the queue.
     */
    @Override
    public E front() throws IllegalStateException{
        if(!isEmpty()){
            return q[top-1];
        }
        throw new IllegalStateException("The queue is empty");
    }

    /**
     * Inserts an element at the rear of the queue.
     *
     * @param element new element to be inserted.
     */
    @Override
    public void enqueue(E element) throws IllegalStateException{
        if(isEmpty()){
            q[top] = element;
            top++;
        }else if(top == q.length){
            throw new IllegalStateException("The queue is full");
        }else{
            for(int i = top-1; i>=0; i--){
                q[i+1] = q[i];
            }
            q[0] = element;
            top++;
        }
    }

    /**
     * Removes the element at the front of the queue.
     *
     * @return element removed.
     */
    @Override
    public E dequeue() throws IllegalStateException{
        if(!isEmpty()){
            top--;
            E returnObj = q[top];
            q[top] = null;
            return returnObj;
        }
        throw new IllegalStateException("The queue is empty");
    }
}
