package TrainSim;

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
    
    private static int count = 0;
    public final int id;
    
    public Train(Route route){
        if (route == null)
            throw new RuntimeException("Route must exist!");
        direction = +1;
        id = ++count;
        contains = 0;
        people = new ArrayList();
        this.route = route;
        trackNum = 0;
        currentTrack = route.getTrack(trackNum);
    }
    
    private int calcDelayTime(double distance) {
        // Calculate how long a track takes to travel.
        // Maybe each train has a variable speed.
        return 1000;   
    }
    
    @Override
    public void run(){
        try {
            while (!interrupted()) {
                Station station = currentTrack.getStation(direction);
                
                for (int z = 0; z < people.size(); z++)
                {
                    people.get(z).setCurrentStation(station);
                }
                
                System.out.printf("%s arrived at %s\n", this.toString(), station.toString());
                // <- Do synchronization stuff here perhaps
                for (int i = contains-1; i >= 0; i--) {
                    Passenger p = people.get(i);
                    // Drop passenger off at station
                    if (p.getDest() == station) {
                        people.remove(i);
                        System.out.printf("%s from %s, has arrived at destination %s\n", p.toString(), this.toString(), station.toString());
                        p.reset();
                        contains--;
                    } else if (station.myRoutes.contains(p.getNextRoute())) {
                        people.remove(i);
                        System.out.printf("%s from %s, has transfered to %s\n", p.toString(), this.toString(), station.toString());
                        p.goNextRoute();
                        station.newPassenger(p);
                        contains--;
                    }
                }
                while (contains < CAP) {
                    // Load only valid passengers from station
                    Passenger p = station.loadPassenger(route);
                    if (p != null && p.getCurrentStation() == station) {
                        people.add(p);
                        System.out.printf("%s was added to %s\n", p.toString(), this.toString());
                        contains++;
                    } else { 
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
    
    public String toString() { 
        return "Train #"+id;
    }
    
}
