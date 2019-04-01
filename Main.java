package TrainSim;

public class Main {
    
    public static void main(String[] args){
        
    	Station a = new Station();
        Station b = new Station();
        Station c = new Station();
        Station d = new Station();
        
        Station e = new Station();
        Station f = new Station();
        
        Station g = new Station();
        Station h = new Station();
        
        //create something where passengers are periodically created at random stations with random destinations
        //or have it when one passenger gets to it's destination another takes it's place at a random station
        
        //circle track, a-> b-> c-> d-> a...
        Track z = new Track(a, b, 50);
        Track y = new Track(b, c, 75);
        Track x = new Track(c, d, 50);
        Track w = new Track(d, a, 75);
        
        //branch paths
        Track v = new Track(b, e, 100);
        Track u = new Track(e, f, 50);
        
        Track t = new Track(d, g, 50);
        Track s = new Track(g, h, 100);
        
        //creating routes for each train
        Route r = new Route (z);
        r.addTrack(y);
        r.addTrack(x);
        r.addLoop(w);
        
        Route r2 = new Route(v);
        r2.addTrack(u);
        
        Route r3 = new Route(t);
        r3.addTrack(s);
         
        
        
        Thread t1 = new Thread(new Train(r));
		Thread t2 = new Thread(new Train(r2));
		Thread t3 = new Thread(new Train(r3));
        
        t1.start();
        t2.start();
        t3.start();
    }
}
