package TrainSim;

public class Train extends Thread{
    
    public static final int CAP = 6;//max capacity
    //subject to change
    private static int contains;
    
    public Train(){
        contains = 0;
    }
}
