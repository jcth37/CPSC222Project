package TrainSim;


public class Main {
    public static void main(String[] args){
        Station a = new Station();
        Station b = new Station();
        Station c = new Station();
        Station d = new Station();
        Route r = new Route(a, b, c);
        Route r2 = new Route(c, d);
        
        Train t1 = new Train(r);
        Train t2 = new Train(r2);
        
        t1.start();
        t2.start();
    }
}
