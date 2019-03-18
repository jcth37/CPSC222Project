package TrainSim;
    
public class Node<E> {
    
    public E element;
    public Node<E> link;

    public Node(E e) {
        element = e;
        link = null;
    }

    public Node(E e, Node n) {
        element = e;
        link = n;
    }
    
}
