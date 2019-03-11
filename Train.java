package TrainSim;

public class Train extends Thread{
    
    public static final int CAP = 6;//max capacity
    private static int nextID = 0;
    private static int collisions = 0;
    
    private int contains;
    private int id;
    private Location loc;
    private Passenger[] people;
    //route?
    
    public Train(){
        id = nextID;
        nextID++;
        contains = 0;
    }
    
    @Override
    public void run(){
        
    }
}
