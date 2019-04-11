package TrainSim;

// Update: -Added 'distance' variable; which currently has no way to be calculated.
//         -Can return station one/two depending on specified train direction.
//----------------
//         -Variables changed to final
//         -Distance is something that we have to come up with anyway, so it
//          was added to the constructor
public class Track{
    private final Station one;
    private final Station two;
    private final double distance;
    
    public Track(Station a, Station b, double d){
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