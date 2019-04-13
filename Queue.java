package TrainSim;

public class Queue<E> {
    
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public Queue() {
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
            tail.link = new Node(e);
            tail = tail.link;
        }
        size++;
    }

    public E dequeue() {
        E ans;
        if (head == null) {
            return null;
        } else {
            ans = head.element;
            head = head.link;
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
