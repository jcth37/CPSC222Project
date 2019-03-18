package TrainSim;

import java.util.ArrayList;
import java.util.Random;
public class Station extends Location{
    private static final Random r = new Random();
    private static final ArrayList<Station> allStations = new ArrayList();
    private ArrayList<Passenger> p = new ArrayList();
    private ArrayList<Track> tracks = new ArrayList();
    
    public Station(){
        allStations.add(this);
        init();
    }
    
    private void init(){
        //intended only to be called when initializing station.
        for(int i = 0 ; i < 5 ; i++){
            p.add(new Passenger(this));
        }
    }
    
    public void addConnection(Track track) {
        routes.add(track);
    }
    
    public void getPassenger(Passenger person){
        p.add(person);
    }
    
    public static Station getRandomStation(){
        //used by passengers to get their destination
        int n = r.nextInt(allStations.size());
        return allStations.get(n);
    }
}
