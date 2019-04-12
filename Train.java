package TrainSim;

import java.util.ArrayList;
import static TrainSim.TrainComponent.STATIONSIZE;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

public class Train extends Thread {
    
    public static final int CAP = 6;//max capacity
    
    private int contains;
    private Track currentTrack;
    private ArrayList<Passenger> people;
    private Route route;
    private int trackNum;  // current track number in route
    private int direction; // must be -1 or +1
    private int xdiff, ydiff;
    
    private static int count = 0;
    public final int id;
    
    //graphics stuff below
    private Point2D point1, point2, point3, point4, point5, ipoint1, ipoint2, ipoint3, ipoint4, ipoint5;
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
        point5 = new Point2D.Double(0, 0);
        ipoint1 = new Point2D.Double(0, 0);
        ipoint2 = new Point2D.Double(0, 0);
        ipoint3 = new Point2D.Double(0, 0);
        ipoint4 = new Point2D.Double(0, 0);
        ipoint5 = new Point2D.Double(0, 0);
    }
    
    @Override
    public void run(){
        try {
            while (!interrupted()) {
                Station station = currentTrack.getStation(-direction);
                
                ipoint1.setLocation(station.x, station.y);
                ipoint2.setLocation(station.x + STATIONSIZE, station.y);
                ipoint3.setLocation(station.x, station.y + STATIONSIZE);
                ipoint4.setLocation(station.x + STATIONSIZE, station.y + STATIONSIZE);
                ipoint5.setLocation(currentTrack.getPoint(-direction));
                point1.setLocation(ipoint1.getX(), ipoint1.getY());
                point2.setLocation(ipoint2.getX(), ipoint2.getY());
                point3.setLocation(ipoint3.getX(), ipoint3.getY());
                point4.setLocation(ipoint4.getX(), ipoint4.getY()); 
                point5.setLocation(ipoint5.getX(), ipoint5.getY()); 
                
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
                //long startTime = System.currentTimeMillis();

                //} while (startTime-System.currentTimeMillis() >= currentTrack.getDistance()*16);
                //sleep((long)(currentTrack.getDistance() / getVel()) * 16); //driving
                
                //remainingTime = currentTrack.getDistance()*16;
                Track nextTrack = route.getTrack(trackNum+direction);
                

                
                if (nextTrack == null) 
                    direction = -direction; //reverse direction
                else {
                    trackNum += direction;  //go to next track
                    currentTrack = nextTrack;
                }
                
                Station nextStation = currentTrack.getStation(-direction);
                double endTime = currentTrack.getDistance()*16;
                double currentTime = 0;
                while (currentTime < endTime) {
                    //calc xdiff ydiff
                    xdiff = (int)(currentTime/endTime*(nextStation.x-station.x));
                    ydiff = (int)(currentTime/endTime*(nextStation.y-station.y));
                    point1.setLocation(ipoint1.getX()+xdiff, ipoint1.getY()+ydiff);
                    point2.setLocation(ipoint2.getX()+xdiff, ipoint2.getY()+ydiff);
                    point3.setLocation(ipoint3.getX()+xdiff, ipoint3.getY()+ydiff);
                    point4.setLocation(ipoint4.getX()+xdiff, ipoint4.getY()+ydiff);
                    point5.setLocation(ipoint5.getX()+xdiff, ipoint5.getY()+ydiff);
                    sleep(1);
                    currentTime += 1;
                } 
                
                
            }
        } catch (InterruptedException e) { 
        }
    }
    
    @Override
    public String toString() { 
        return "Train #"+id;
    }

    public void draw(Graphics2D g2) {
        g2.draw(new Line2D.Double(point1, point2));
        g2.draw(new Line2D.Double(point1, point3));
        g2.draw(new Line2D.Double(point2, point4));
        g2.draw(new Line2D.Double(point3, point4));
        Integer i = people.size();
        g2.drawString(i.toString(), (int)point5.getX(), (int)point5.getY());
    }
}
