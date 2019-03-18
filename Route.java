package TrainSim;

// Update: -Now constructs route of tracks from a specified order of stations.
//         -Since distance was added to track constructor, added random distance
//          between stations
import java.util.Random;

public class Route {

    private Track[] myTracks;  //should have different name?
    private static final Random r = new Random();

    private Station[] s;

    public Route(Station... stations) {
        // This constructs array of tracks from ordered set of stations
        s = stations;
        if (stations.length < 2) {
            throw new RuntimeException("Need at least two stations!");
        }
        myTracks = new Track[stations.length - 1];
        for (int i = 0; i < stations.length - 1; i++) {
            stations[i].addRoute(this);
            myTracks[i] = new Track(stations[i], stations[i + 1], r.nextDouble() * 35);
        }
    }

    public Track getTrack(int i) {
        if (i < 0 || i >= myTracks.length) {
            return null;
        }
        return myTracks[i];
    }

    public boolean contatins(Station aStation) {
        boolean ans = false;
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals(aStation)) {
                ans = true;
            }
        }
        return ans;
    }
}
