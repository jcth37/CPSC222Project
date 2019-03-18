package TrainSim;

// Update: -Now constructs route of tracks from a specified order of stations.
//         -Since distance was added to track constructor, added random distance
//          between stations

import java.util.Random;
public class Tracks {
    private Track[] route;  //should have different name?
    
    public Tracks(Station... stations) {
        Random r = new Random();
        // This constructs array of tracks from ordered set of stations
        if (stations.length < 2)
            throw new RuntimeException("Need at least two stations!");
        route = new Track[stations.length-1];
        for (int i = 0; i < stations.length-1; i++)
            route[i] = new Track(stations[i], stations[i+1], r.nextDouble()*35);
    }
    
    public Track getTrack(int i) {
        if (i < 0 || i >= route.length)
            return null;
        return route[i];
    }
    
}