package TrainSim;

// Update: Station only loads passengers that need a given route.

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
    
    public boolean hasPassengers(Route route) {
        for (Passenger person : p) {
            Route pRoute = p.getNextRoute();
            if (pRoute == route || pRoute == null)  //null => station on current track
                return true;
        }
        return false;
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
    
    public Passenger loadPassenger(Route route){//put the passenger on the train
        // only put on if person's route is the same as the train's
        for (int i = 0; i < p.size(); i++) {
            Route pRoute = p.get(i).getNextRoute();
            if (pRoute == route || pRoute == null)  //null => station on current track
                return p.remove(i);
        }
        return null;
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
