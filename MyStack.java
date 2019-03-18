package TrainSim;

public class MyStack<E> {
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
        Node temp = head.link;
        head.link = null;
        head = temp;
        size--;
        return ans;
    }
    
    public int size(){
        return size;
    }
}

