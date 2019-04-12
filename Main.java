package TrainSim;
//added graphics stuff

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

    public static void main(String[] args) {
        Station a = new Station(10, 10);
        Station b = new Station(400, 10);
        Station c = new Station(790, 10);
        Station d = new Station(450, 400);
        Station[] allStations = new Station[]{a, b, c, d};

        Route r = new Route(a, b, d);
        Route r2 = new Route(c, d);
        Route r3 = new Route(a, d);
        Route[] allRoutes = new Route[]{r, r2, r3};

        a.init(3);
        b.init(3);
        c.init(3);
        d.init(3);

        Train t1 = new Train(r);
        Train t2 = new Train(r2);
        Train t3 = new Train(r3);
        Train[] allTrains = new Train[]{t1, t2, t3};

        t1.start();
        t2.start();
        t3.start();

        //graphics stuff
        

        JFrame frame = new JFrame();

        frame.setSize(1000, 500);
        frame.setTitle("Train Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TrainComponent tc = new TrainComponent(allStations, allRoutes, allTrains);
        frame.add(tc);
        
        class TimerListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                tc.repaint();
            }
        }
        
        ActionListener listener = new TimerListener();
        Timer t = new Timer(5, listener);
        t.start();
        frame.setVisible(true);
        //end of graphics stuff
    }

}