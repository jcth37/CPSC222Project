package TrainSim;

import static TrainSim.TrainComponent.STATIONSIZE;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class Train extends Thread {
    
    public static int CAP = 6;//max capacity
    public static int DRIVE_DELAY = 15; //multiplies with track distance
    
    private int contains;
    private Track currentTrack;
    private ArrayList<Passenger> people;
    private Route route;
    private int trackNum;  // current track number in route
    private int direction; // must be -1 or +1
    
    private static int count = 0;
    public final int id;
    
    //graphics stuff below
    private Point2D[] points;
    private Point2D[] ipoints;
    
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
        
        points = new Point2D[5];
        ipoints = new Point2D[points.length];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point2D.Double(0, 0);
            ipoints[i] = new Point2D.Double(0, 0);
        }
    }
    
    @Override
    public void run(){
        try {
            Station station = currentTrack.getStation(0);
            while (!interrupted()) {
                
                ipoints[0].setLocation(station.x, station.y);
                ipoints[1].setLocation(station.x + STATIONSIZE, station.y);
                ipoints[2].setLocation(station.x, station.y + STATIONSIZE);
                ipoints[3].setLocation(station.x + STATIONSIZE, station.y + STATIONSIZE);
                ipoints[4].setLocation(currentTrack.getPoint(-direction));
                shiftPoints(0, 0);
                
                System.out.printf("%s arrived at %s\n", this.toString(), station.toString());
                
                for (int z = 0; z < people.size(); z++) 
                    people.get(z).setCurrentStation(station);
                    
                station.enter(id);  // synchronization lock
                
                for (int i = contains-1; i >= 0; i--) {
                    Passenger p = people.get(i);
                    // Drop passenger off at station
                    if (p.getDest() == station) {
                        people.remove(i);
                        System.out.printf("\t%s from %s, has arrived at destination %s\n", p.toString(), this.toString(), station.toString());
                        p.reset();
                        contains--;
                        this.sleep(250); // have a delay between dropping off and picking up, for clearer visualization
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
                        this.sleep(250);
                    }
                }
                while (contains < CAP) {
                    // Load only valid passengers from station
                    Passenger p = station.loadPassenger(route);
                    if (p != null && p.getCurrentStation() == station) {
                        people.add(p);
                        System.out.printf("\t%s was added to %s\n", p.toString(), this.toString());
                        contains++;
                        this.sleep(250);
                    } else { 
                        break;
                    }
                }
                station.exit(id);   // synchronization unlock
                
                System.out.printf("%s has left %s\n", this.toString(), station.toString());

                Track nextTrack = route.getTrack(trackNum+direction);               
                if (nextTrack == null) 
                    direction = -direction; //reverse direction
                else {
                    trackNum += direction;  //go to next track
                    currentTrack = nextTrack;
                }
                
                Station nextStation = currentTrack.getStation(-direction);
                double endTime = currentTrack.getDistance()*DRIVE_DELAY;
                double currentTime = 0;
                while (currentTime < endTime) {
                    //calc xdiff ydiff
                    double shiftX = currentTime/endTime*(nextStation.x-station.x);
                    double shiftY = currentTime/endTime*(nextStation.y-station.y);
                    shiftPoints(shiftX, shiftY);
                    this.sleep(1);
                    currentTime += 1;
                } 
                station = nextStation;
                
            }
        } catch (InterruptedException e) { 
        }
    }
    
    @Override
    public String toString() { 
        return "Train #"+id;
    }
    
    public void shiftPoints(double shiftX, double shiftY) {
        for (int i = 0; i < points.length; i++)
            points[i].setLocation(ipoints[i].getX()+shiftX, ipoints[i].getY()+shiftY);
    }

    public void draw(Graphics2D g2) {
        g2.draw(new Line2D.Double(points[0], points[1]));
        g2.draw(new Line2D.Double(points[0], points[2]));
        g2.draw(new Line2D.Double(points[1], points[3]));
        g2.draw(new Line2D.Double(points[2], points[3]));
        Integer i = people.size();//this is done to it can be converted to string
        g2.drawString(i.toString(), (int)points[4].getX(), (int)points[4].getY());
    }
}
