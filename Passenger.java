package TrainSim;

public class Passenger {
    private Station dest; //destination
    
    private MyStack<Route> routeStack;
    private static int count = 0;
    public final int id;
    
    private Route myRoute;
    private Station currStat;
    
    public Passenger(){
        id = count++;
        reset();
    }
    
    private void generateRouteStack(Station s) {     // Using Breadth First Search
        MyQueue<Route> search = new MyQueue();
        MyQueue<Node<Route>> traceBack = new MyQueue();  //traceBack keeps track of the "route of routes"
        
        Route r;
        Node<Route> n = null;
        
        // Search for efficient queue of routes while recording a traceBack
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
        myRoute = routeStack.pop();
    }
    
    public final void reset(){
        dest = Station.getRandomStation();
        Station start = Station.getRandomStation();
        while(start == dest){
            start = Station.getRandomStation();
        }
        generateRouteStack(start);
        start.newPassenger(this);
        currStat = start;
        System.out.printf("\t%s starts at %s, to get to %s\n", toString(), start.toString(), getDest().toString());
    }
    
    public Station getDest(){
        return dest;
    }
    
    public Station getCurrentStation(){
        return currStat;
    }
    
    public void setCurrentStation(Station a){
        currStat = a;
    }
    
    public Route getCurrentRoute() {
        return myRoute;
    }
    
    public Route getNextRoute() {
        return routeStack.top();
    }
    
    public void goNextRoute() {
        myRoute = routeStack.pop();
    }
    
    @Override
    public String toString() { 
        return "Passenger #"+id;
    }
    
}
