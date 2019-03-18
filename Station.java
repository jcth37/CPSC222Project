package TrainSim;

import java.util.ArrayList;
import java.util.Random;
public class Station{
    //station is the shared resource; two trains cannot access the same station
    //at the same time
    private static final Random r = new Random();
    private static final ArrayList<Station> allStations = new ArrayList();
    public final ArrayList<Route> myRoutes = new ArrayList();
    private ArrayList<Passenger> p = new ArrayList();
    
    public Station(){
        allStations.add(this);
        init();
    }
    
    public boolean hasPassengers(){
        return !p.isEmpty();
    }
    
    public void addRoute(Route r){
        myRoutes.add(r);
    }
    
    private void init(){
        //intended only to be called when initializing station.
        for(int i = 0 ; i < 5 ; i++){
            p.add(new Passenger());
        }
    }
    
    public Passenger loadPassenger(){//put the passenger on the train
        return p.remove(0);
    }
    
    public void newPassenger(Passenger person){//a passenger arrives at the station
        p.add(person);
    }
    
    public static Station getRandomStation(){
        //used by passengers to get their destination
        int n = r.nextInt(allStations.size());
        return allStations.get(n);
    }
}
