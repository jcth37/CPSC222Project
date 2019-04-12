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
    private static int count = 0;
    public final int id;
    public final double x, y;
    
    // Synchronization stuff
    private byte[] trains;   // Trains entering
    private int[] tk;       // Tokens
    private int n;          // Number of threads 
    
    public Station(double x, double y){
        allStations.add(this);
        id = count++;
        this.x = x;
        this.y = y;
    }
    
    public boolean hasPassengers(){
        return !p.isEmpty();
    }
    
    public boolean hasPassengers(Route route) {
        for (Passenger person : p) {
            Route pRoute = person.getNextRoute();
            if (pRoute == route || pRoute == null)  //null => station on current track
                return true;
        }
        return false;
    }
    
    public void addRoute(Route r){
        myRoutes.add(r);
    }
    
    public void init(int n){
        //intended only to be called when initializing station.
        for(int i = 0 ; i < 1 ; i++)
            new Passenger();
        //bakery stuff
        this.n = n;
        trains = new byte[n];
        tk = new int[n];
    }
    
    public Passenger loadPassenger(Route route){//put the passenger on the train
        // only put on if person's route is the same as the train's
        for (int i = 0; i < p.size(); i++) {
            Route pRoute = p.get(i).getCurrentRoute();
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
   
    /**
     * @return      the maximum token value
     */
    private int maxToken() {
        int m = tk[0];
        for (int j = 1; j < n; j++) {
            if (m < tk[j])
                m = tk[j];
        }
        return m;
    }
    
    /**
     * Enter into a critical section. Will pause thread until section is empty.
     * @param i     the current thread ID
     * @throws InterruptedException 
     */
    public void enter(int i) throws InterruptedException {
        trains[i] = 1;
        tk[i] = maxToken()+1;
        trains[i] = 0;
        for (int j = 0; j < n; j++) {
            // Note: delays must added, or else massive performance drops occur.
            while (trains[j] != 0) 
                Thread.currentThread().sleep(1);
            while (tk[j] != 0 && (j < i || j == i && tk[j] < tk[i])) 
                Thread.currentThread().sleep(1);
        }
    }
    
    /**
     * Exits a critical section. Allows the next thread to have access.
     * @param i     the current thread ID 
     */
    public void exit(int i) {
        tk[i] = 0;
    }
    
    /**
     * Resets a bakery's variables. In case experiment was halted at a bad time.
     */
    public void reset() {
        for (int i = 0; i < n; i++) {
            trains[i] = 0;
            tk[i] = 0;
        }
    }
    
    public String toString() { 
        return "Station #"+id;
    }
    
}
