package TrainSim;

import static TrainSim.TrainComponent.STATIONSIZE;
import java.awt.geom.Point2D;

public class Track{
    private final Station one;
    private final Station two;
    private final double distance;
    private final Point2D ptOne;
    private final Point2D ptTwo;   
    
    public Track(Station a, Station b){
        one = a;
        two = b;
        ptOne = new Point2D.Double(a.x + STATIONSIZE/2, a.y + STATIONSIZE/2);
        ptTwo = new Point2D.Double(b.x + STATIONSIZE/2, b.y + STATIONSIZE/2);
        distance = Math.sqrt(Math.pow(a.x-b.x, 2)+Math.pow(a.y-b.y, 2));
    }
    
    public double getDistance() {
        return distance;
    }
    
    public Point2D getPoint(int direction){
        return direction <= 0 ? ptOne : ptTwo;
    }
    
    public Station getStation(int direction) {
        return direction <= 0 ? one : two;
    }
    
}