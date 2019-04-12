package TrainSim;

import java.util.ArrayList;
import static TrainSim.TrainComponent.STATIONSIZE;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

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
    
    //graphics stuff below
    private double xvel, yvel;
    private Point2D point1, point2, point3, point4;
    /*
     ________
     |        |
     |        | <- this point is where sideX, sideY coordinate points to
     |________|     if the train is going to the left of the screen
     */
    
    public Train(Route route){
        if (route == null)
            throw new RuntimeException("Route must exist!");
        direction = +1;
        id = count++;
        contains = 0;
        people = new ArrayList();
        this.route = route;
        trackNum = 0;
        currentTrack = route.getTrack(trackNum);
        
        point1 = new Point2D.Double(0, 0);
        point2 = new Point2D.Double(0, 0);
        point3 = new Point2D.Double(0, 0);
        point4 = new Point2D.Double(0, 0);
        xvel = 0;
        yvel = 0;
    }
    
    @Override
    public void run(){
        try {
            while (!interrupted()) {
                Station station = currentTrack.getStation(-direction);
                
                point1.setLocation(station.x, station.y);
                point2.setLocation(station.x + STATIONSIZE, station.y);
                point3.setLocation(station.x, station.y + STATIONSIZE);
                point4.setLocation(station.x + STATIONSIZE, station.y + STATIONSIZE);
                xvel = (currentTrack.getPoint(direction).getX()- 
                currentTrack.getPoint(-direction).getX())/500;
                yvel = (currentTrack.getPoint(direction).getY()- 
                currentTrack.getPoint(-direction).getY())/500;
                System.out.printf("%s arrived at %s\n", this.toString(), station.toString());
                
                for (int z = 0; z < people.size(); z++) 
                    people.get(z).setCurrentStation(station);
                    
                station.enter(id);  // synchronization lock
                
                System.out.printf("%s arrived at %s\n", this.toString(), station.toString());
                for (int i = contains-1; i >= 0; i--) {
                    Passenger p = people.get(i);
                    // Drop passenger off at station
                    if (p.getDest() == station) {
                        people.remove(i);
                        System.out.printf("\t%s from %s, has arrived at destination %s\n", p.toString(), this.toString(), station.toString());
                        p.reset();
                        contains--;
                    } 
                    else if (route.contains(p.getDest())){
                        System.out.printf("\t%s stays on %s\n", p.toString(), this.toString()); /////////////////added to keep tabs on passengers
                    }
                    else if (station.myRoutes.contains(p.getNextRoute())) {                        
                        people.remove(i);
                        System.out.printf("\t%s from %s, has transferred to %s\n", p.toString(), this.toString(), station.toString());
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
                        System.out.printf("\t%s was added to %s\n", p.toString(), this.toString());
                        contains++;
                    } else { 
                        break;
                    }
                }
                System.out.printf("%s has left %s\n", this.toString(), station.toString());
                
                station.exit(id);   // synchronization unlock
                
                sleep((long)(currentTrack.getDistance() / getVel()) * 16); //driving
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
    
    @Override
    public String toString() { 
        return "Train #"+id;
    }
    
    private double getVel(){
         return Math.sqrt(Math.pow(xvel, 2)+Math.pow(yvel, 2));
    }

    public void draw(Graphics2D g2) {
        g2.draw(new Line2D.Double(point1, point2));
        g2.draw(new Line2D.Double(point1, point3));
        g2.draw(new Line2D.Double(point2, point4));
        g2.draw(new Line2D.Double(point3, point4));
    }

    public void move() {
        point1.setLocation(point1.getX()+xvel, point1.getY()+yvel);
        point2.setLocation(point2.getX()+xvel, point2.getY()+yvel);
        point3.setLocation(point3.getX()+xvel, point3.getY()+yvel);
        point4.setLocation(point4.getX()+xvel, point4.getY()+yvel);
    }
    
}
