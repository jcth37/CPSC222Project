package TrainSim;

public class Route {

    private Track[] myTracks;  //should have different name?

    public final Station[] s;

    public Route(Station... stations) {
        // This constructs array of tracks from ordered set of stations
        s = stations;
        if (stations.length < 2) {
            throw new RuntimeException("Need at least two stations!");
        }
        myTracks = new Track[stations.length - 1];
        for (int i = 0; i < stations.length - 1; i++) {
            stations[i].addRoute(this);
            myTracks[i] = new Track(stations[i], stations[i + 1]);
        }
        stations[stations.length - 1].addRoute(this);
    }

    public Track getTrack(int i) {
        if (i < 0 || i >= myTracks.length) {
            return null;
        }
        return myTracks[i];
    }

    public boolean contains(Station aStation) {
        boolean ans = false;
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals(aStation)) {
                ans = true;
            }
        }
        return ans;
    }
    
}
