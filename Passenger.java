
package TrainSim;


public class Passenger {
    private Station dest; //destination
    private MyStack destStack;
    
    public Passenger(){
        reset();
    }
    
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
    
    public void setDest(Station s){
        destStack.push(dest);
        dest = s;
    }
    
    public boolean hasArrived() {
        return destStack.size() == 0;
    }
    
}
