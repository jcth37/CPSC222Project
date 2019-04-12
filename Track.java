package TrainSim;

public class Track{
    private final Station one;
    private final Station two;
    private final double distance;
    
    public Track(Station a, Station b){
        one = a;
        two = b;
        distance = Math.sqrt(Math.pow(a.x-b.x, 2)+Math.pow(a.y-b.y, 2));
    }
    
    public double getDistance() {
        return distance;
    }
    
    public Station getStation(int direction) {
        return direction <= 0 ? one : two;
    }
    
}