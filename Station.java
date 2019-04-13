package TrainSim;

import java.util.ArrayList;
import java.util.Random;
public class Station{
    public static int WAIT_TIME = 3000;  //how long a train must wait at the station
    
    //station is the shared resource; two trains cannot access the same station
    //at the same time
    private static final Random r = new Random();
    private static final ArrayList<Station> allStations = new ArrayList();
    public final ArrayList<Route> myRoutes = new ArrayList();
    private ArrayList<Passenger> people = new ArrayList();
    private static int count = 0;
    public final int id;
    public final double x, y;
    
    // Synchronization stuff
    private boolean[] trains;   // Trains entering
    private int[] tk;       // Tokens
    private int n;          // Number of threads 
    private boolean sectDirect = false;
    private boolean waitingCheck;
    
    public Integer getNumPassengers(){
        return people.size();
    }
    
    public Station(double x, double y){
        allStations.add(this);
        id = count++;
        this.x = x;
        this.y = y;
    }
    
    public boolean hasPassengers(){
        return !people.isEmpty();
    }
    
    public boolean hasPassengers(Route route) {
        for (Passenger person : people) {
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
        //for(int i = 0 ; i < 1 ; i++)     < doing this outside station now
        //    new Passenger();
        //bakery stuff
        this.n = n;
        trains = new boolean[n];
        tk = new int[n];
    }
    
    public Passenger loadPassenger(Route route){//put the passenger on the train
        // only put on if person's route is the same as the train's
        for (int i = 0; i < people.size(); i++) {
            Route pRoute = people.get(i).getCurrentRoute();
            if (pRoute == route || pRoute == null)  //null => station on current track
                return people.remove(i);
        }
        return null;
    }
    
    public void newPassenger(Passenger person){//a passenger arrives at the station
        people.add(person);
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
     * @return      the minimum token value
     */
    private int minToken() {
        int m = tk[0];
        for (int j = 1; j < n; j++) {
            if (m > tk[j])
                m = tk[j];
        }
        return m;
    }
    
    private void wait(int i) throws InterruptedException {
        // Note: delays must added, or else massive performance drops occur.
        Thread.currentThread().sleep(1);
        if (!waitingCheck) {
            waitingCheck = true;
            System.out.println ("Train #" + i + " is waiting at " + this);
        }        
    } 
    
    /**
     * Applies lock using Symmetric Token Bakery Algorithm.
     * Enter into a critical section. Will pause thread until section is empty.
     * @param i     the current thread ID
     * @throws InterruptedException 
     */
    public void enter(int i) throws InterruptedException {
        waitingCheck = false;
        trains[i] = true;
        if (sectDirect) {
            tk[i] = minToken()-1;
            trains[i] = false;
            for (int j = 0; j < n; j++) {
                while (trains[j]) 
                    wait(i);                
                while (tk[j] < 0 && (j > i || j == i && tk[j] > tk[i]) || tk[j] > 0 && sectDirect) 
                    wait(i);
            }  
        } else {
            tk[i] = maxToken()+1;
            trains[i] = false;
            for (int j = 0; j < n; j++) {
                while (trains[j]) 
                    wait(i);                
                while (tk[j] > 0 && (j < i || j == i && tk[j] < tk[i]) || tk[j] < 0 && !sectDirect) 
                    wait(i);
            }
        }
        sectDirect = !sectDirect;
        Thread.currentThread().sleep(WAIT_TIME/2);
    }
    
    /**
     * Exits a critical section. Allows the next thread to have access.
     * @param i     the current thread ID 
     */
    public void exit(int i) throws InterruptedException {
        Thread.currentThread().sleep(WAIT_TIME/2);
        tk[i] = 0;
    }
    
    /**
     * Resets a bakery's variables. In case experiment was halted at a bad time.
     */
    public void resetSynchronization() {
        for (int i = 0; i < n; i++) {
            trains[i] = false;
            tk[i] = 0;
        }
    }
    
    public String toString() { 
        return "Station #"+id;
    }
    
}
