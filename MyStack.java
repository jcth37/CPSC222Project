package TrainSim;

public class MyStack<E> {
    private class Node<E> {

        private E element;
        private Node<E> next;

        private Node(E e) {
            element = e;
            next = null;
        }

        private Node(E e, Node n) {
            element = e;
            next = n;
        }
    }
    private Node<E> head;
    private int size;
    
    public MyStack(){
        head = null;
        size = 0;
    }
    
    public void push(E e){
        if(head == null){
            head = new Node(e);
        }else{
            head = new Node(e, head);
        }
        size++;
    }
    
    public E top(){
        if(head == null){
            return null;
        }
        return head.element;
    }
    
    public E pop(){
        if(head == null){
            return null;
        }
        E ans = top();
        Node temp = head.next;
        head.next = null;
        head = temp;
        size--;
        return ans;
    }
    
    public int size(){
        return size;
    }
}

