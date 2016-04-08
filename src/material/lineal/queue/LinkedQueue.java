package material.lineal.queue;

/**
 * Created by Alejandro on 8/4/16.
 */
public class LinkedQueue<E> implements Queue<E>{

    class Node<E> {
        private E element;
        private Node next;

        /**
         * Constructor of Node class
         * @param element
         * @param next
         */
        public Node(E element, Node next) {
            this.element = element;
            this.next = next;
        }

        /**
         *
         * @return element of the node
         */
        public E getElement() {
            return element;
        }

        /**
         *
         * @return the next Node of actual Node
         */
        public Node getNext() {
            return next;
        }
    }

    private int size;
    private Node head;

    /**
     * Constructor of LinkedQueue class
     */
    public LinkedQueue() {
        size = 0;
        head = null;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return number of elements in the queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns whether the queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Inspects the element at the front of the queue.
     *
     * @return element at the front of the queue.
     */
    @Override
    public E front() throws IllegalStateException{
        if(!isEmpty()){
            Node aux = head;
            while (aux.next != null){
                aux = aux.getNext();
            }
            return (E) aux.getElement();
        }
        throw new IllegalStateException("The queue is empty!!!");
    }

    /**
     * Inserts an element at the rear of the queue.
     *
     * @param element new element to be inserted.
     */
    @Override
    public void enqueue(E element) {
        if(isEmpty()){
            Node node = new Node(element, null);
            head = node;
            size++;
        }else{
            Node node = new Node(element, head);
            head = node;
            size++;
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
            Node aux = head;
            while (aux.next != null){
                if(aux.next.getNext() == null){
                    Node returnNode = aux.next;
                    aux.next = null;
                    size--;
                    return (E) returnNode.getElement();
                }else {
                    aux = aux.next;
                }
            }
            head =null;
            size--;
            return (E) aux.getElement();
        }
        throw new IllegalStateException("The queue is empty!!!");
    }
}
