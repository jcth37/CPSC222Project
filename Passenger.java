
package TrainSim;


public class Passenger {
    private Station dest; //destination
    
    public Passenger(Station start){
        reset();
    }
    
    public final void reset(){
        dest = Station.getRandomStation();
        Station.getRandomStation().getPassenger(this);
    }
    
    public Station getDest(){
        return dest;
    }
}
