package TrainSim;

// Update: -Added 'distance' variable; which currently has no way to be calculated.
//         -Can return station one/two depending on specified train direction.

public class Track extends Location{
    private Station one;
    private Station two;
    private double distance;
    
    public Track(Station a, Station b){
        one = a;
        two = b;
        // Need a way to determine distance.
    }
    
    public double getDistance() {
        return distance;
    }
    
    public Station getStation(int direction) {
        return direction <= 0 ? one : two;
    }
    
}
