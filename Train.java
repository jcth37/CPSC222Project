package TrainSim;

/* Update:  -Train will now go from track to track using track order from the 
             'route' variable. If train reaches the end of the route, it's
             direction will reverse.
            -When train reaches a station, the correct passengers will be removed.
            -'people' is now an ArrayList.
*/         

import java.util.ArrayList;

public class Train extends Thread {
    
    public static final int CAP = 6;//max capacity
    private static int nextID = 0;
    private static int collisions = 0;
    
    private int contains;
    private int id;
    //private Location loc;
    private Track currentTrack;
    private ArrayList<Passenger> people;
    private Route route;
    private int trackNum;  // current track number in route
    private int direction; // must be -1 or +1
    
    public Train(Route route){
        if (route == null)
            throw new RuntimeException("Route must exist!");
        id = nextID;
        nextID++;
        contains = 0;
        people = new ArrayList();
        this.route = route;
        trackNum = 0;
        currentTrack = route.getTrack(trackNum);
        direction = +1;
    }
    
    private int calcDelayTime(double distance) {
        // Calculate how long a track takes to travel.
        // Maybe each train has a variable speed.
        return 10;   
    }
    
    @Override
    public void run(){
        try {
            while (!interrupted()) {
                Station station = currentTrack.getStation(direction);
                // <- Do synchronization stuff here perhaps
                for (int i = contains-1; i >= 0; i--) {
                    Passenger p = people.get(i);
                    if (p.getDest() == station) {
                        people.remove(i);
                        // <- Drop passenger off at station
                        contains--;
                    }
                }
                while (contains < CAP) {
                    // <- Load only valid passengers from station
                    if(station.hasPassengers()){
                        Passenger p = station.loadPassenger();
                        people.add(p);
                      //need to add list of stops
                    }else{
                        break;
                    }
                }
                sleep(calcDelayTime(currentTrack.getDistance())); //driving
                Track nextTrack = route.getTrack(trackNum+direction);
                if (nextTrack == null) 
                    direction = -direction; //reverse direction
                else {
                    trackNum += direction;  //go to next track
                    currentTrack = nextTrack;
                }
            }
        } catch (InterruptedException e) { 
        }
    }
    
    public Location getLocation() {
        return currentTrack;
    }
}
