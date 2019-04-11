package TrainSim;


public class Main {
    public static void main(String[] args){
        Station a = new Station(0, 0);
        Station b = new Station(1000, 0);
        Station c = new Station(2000, 0);
        Station d = new Station(3000, 0);

        Route r = new Route(a, b, c);
        Route r2 = new Route(c, d);
        Route r3 = new Route(a, d);
        
        a.init(3);
        b.init(3);
        c.init(3);
        d.init(3);
        
        Train t1 = new Train(r);
        Train t2 = new Train(r2);
        Train t3 = new Train(r3);
        
        t1.start();
        t2.start();
        t3.start();
    }
}
