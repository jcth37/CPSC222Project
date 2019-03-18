package TrainSim;

public class MyQueue<E> {

    private class Node<E> {

        private E element;
        private Node<E> prev;

        private Node(E e) {
            element = e;
            prev = null;
        }

        private Node(E e, Node p) {
            element = e;
            prev = p;
        }
    }
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MyQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(E e) {
        //add at tail, remove at head
        if (size == 0) {
            head = new Node(e);
            tail = head;
        } else {
            tail.prev = new Node(e);
            tail = tail.prev;
        }
        size++;
    }

    public E dequeue() {
        E ans;
        if (head == null) {
            return null;
        } else {
            ans = head.element;
            head = head.prev;
        }
        size--;
        return ans;
    }
    
    public E look(){
        E ans;
        if (head == null) {
            return null;
        } else {
            ans = head.element;
        }
        return ans;
    }
    
    public int size() {
        return size;
    }
}
