package TrainSim;

/* Update:  -Generates an efficient, non-infinite stack of routes.
            -destStack was changed to routeStack, because the amount of work overall seems smaller in this case to me.
*/

public class Passenger {
    private Station dest; //destination
    private Station start; //origin
            
    //may add a current station indicator        
    
    //
    public Passenger(){
        reset();
    }
    
    
    //////////
    public final void reset(){
        dest = Station.getRandomStation();
        Station start = Station.getRandomStation();
        while(start == dest){
            start = Station.getRandomStation();
        }
        start.newPassenger(this);
    }
    
    
    
    public Station getDest(){
        return dest;
    }
    
    public Station getStart(){
        return start;
    }
    
    //to be collected by garbage collector once it's done **null other links if added. eg current station
    public void setNull() {
        start = null;
        dest = null;
    }
    
}
