package TrainSim;

public class Passenger {
    private Station dest; //destination
    private MyStack<Route> routeStack;
    
    public Passenger(){
        reset();
    }
    
    private void generateRouteStack(Station s) {     // Using Breadth First Search
        MyQueue<Route> search = new MyQueue();
        MyQueue<Node<Route>> traceBack = new MyQueue();  //traceBack keeps track of the "route of routes"
        
        Route r;
        Node<Route> n = null;
        
        // Search for efficient queue of routes and recording a traceBack
        for (Route r2 : s.myRoutes) {
            search.enqueue(r2);
            traceBack.enqueue(new Node(r2));
        }
        while (s != dest) {         //Problem: will infinite loop if station is on a disconnected set of routes
            r = search.dequeue();
            n = traceBack.dequeue();
            int i = 0;
            Track t = r.getTrack(0);
            s = t.getStation(-1);
            while (s != dest) {
                for (Route r2 : s.myRoutes) {
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
        routeStack = new MyStack();
        while (n != null) {
            routeStack.push(n.element);
            n = n.link;
        }
    }
    
    public final void reset(){
        dest = Station.getRandomStation();
        Station start = Station.getRandomStation();
        while(start == dest){
            start = Station.getRandomStation();
        }
        generateRouteStack(start);
        start.newPassenger(this);
    }
    
    public Station getDest(){
        return dest;
    }
    
    public Route getNextRoute() {
        return routeStack.top();
    }
    
    public void goNextRoute() {
        routeStack.pop();
    }
    
    //public void setDest(Station s){
    //    destStack.push(dest);
    //    dest = s;
    //}
    
    //public boolean hasArrived() {
    //    return destStack.size() == 0;
    //}
    
}
