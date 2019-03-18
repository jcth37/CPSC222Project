package TrainSim;

public class Passenger {
    private Station dest; //destination
    private MyStack destStack;
    
    public Passenger(){
        reset();
    }
    
    private void generateDestStack(Station s) {     // Using Breadth First Search
        MyQueue<Route> search = new MyQueue();
        MyQueue<Node<Route>> traceBack = new MyQueue();  //traceBack keeps track of the "route of routes"
        
        Route r
        Node<Route> n
        
        // Search for efficient queue of routes and recording a traceBack
        for (Route r : s.getRoutes()) {
            search.enqueue(r);
            traceBack.enqueue(new Node(r));
        }
        while (s != dest) {         //Problem: will infinite loop if station is on a disconnected set of routes
            r = search.dequeue();
            n = traceBack.dequeue();
            int i = 0;
            Track t = r.getTrack(0);
            s = t.getStation(-1);
            while (s != dest) {
                for (Route r2 : s.getRoutes()) {
                    search.enqueue(r2);
                    traceBack.enqueue(new Node(r2, n));
                }
                if (t != null) { 
                    s = t.getStation(+1);
                    t = r.getTrack(++i);
                } else {
                    break;
                }      
            } 
        }
        
        // Finally use traceBack nodes to generate destStack
        // thankfully pushing on stack reverses the order
        destStack = new MyStack();
        while (n != null) {
            destStack.push(n.element);
            n = n.link;
        }
    }
    
    public final void reset(){
        dest = Station.getRandomStation();
        Station start = Station.getRandomStation();
        while(start == dest){
            start = Station.getRandomStation();
        }
        generateDestStack(start);
        start.newPassenger(this);
    }
    
    public Station getDest(){
        return dest;
    }
    
    public void setDest(Station s){
        destStack.push(dest);
        dest = s;
    }
    
    public boolean hasArrived() {
        return destStack.size() == 0;
    }
    
}
