package TrainSim;

/* Update:  -Train will only load or drop off valid passengers now, this is keeping in mind the inner stack of each passenger.
*/         

import java.util.ArrayList;

public class Train extends Thread {
    
    public static final int CAP = 6;//max capacity
    private static int collisions = 0;
    
    private int contains;
    private Track currentTrack;
    private ArrayList<Passenger> people;
    private Route route;
    private int trackNum;  // current track number in route
    private int direction; // must be -1 or +1
    
    public Train(Route route){
        if (route == null)
            throw new RuntimeException("Route must exist!");
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
                    // Drop passenger off at station
                    if (p.getDest() == station) {
                        people.remove(i);
                        p.reset();
                        contains--;
                    } else if (station.myRoutes.contains(p.getNextRoute())) {
                        people.remove(i);
                        p.goNextRoute();
                        station.newPassenger(p);
                        contains--;
                    }
                }
                while (contains < CAP) {
                    // Load only valid passengers from station
                    Passenger p = station.loadPassenger(route);
                    if (p != null) 
                        people.add(p);
                    else 
                        break;
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
}
