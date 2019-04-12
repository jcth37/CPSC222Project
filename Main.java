package TrainSim;
//added graphics stuff

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.io.*;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        
        ArrayList<Station> stationList = new ArrayList();
        ArrayList<Route> routeList = new ArrayList();
        ArrayList<Train> trainList = new ArrayList();
        
        File file = new File("map.txt");
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] arguments;
            String line;
            ArrayList<String> names = new ArrayList();
            while ((line = bufferedReader.readLine()) != null) {
                arguments = line.split("\\s+");
                // show errors for too little arguments, or incorrect types
                if (arguments[0].equals(""))
                    break;
                names.add(arguments[0]);
                int x = Integer.parseInt(arguments[1]);
                int y = Integer.parseInt(arguments[2]);
                Station s = new Station(x, y);
                stationList.add(s);
            }
            
            while ((line = bufferedReader.readLine()) != null) {
                arguments = line.split("\\s+");
                // show errors for too little arguments, or incorrect types
                ArrayList<Station> routeStations = new ArrayList();
                for (String a : arguments)
                    routeStations.add(stationList.get(names.indexOf(a)));
                routeList.add(new Route(routeStations.toArray(new Station[routeStations.size()])));
            }
            names = null;
            
            
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        } 
        
        Station[] allStations = stationList.toArray(new Station[stationList.size()]);
        Route[] allRoutes = routeList.toArray(new Route[routeList.size()]);
                
        stationList = null;
        routeList = null; 

        for (Station s : allStations)
            s.init(3);
        
        for (Route r : allRoutes) {
            Train t = new Train(r);
            trainList.add(t);
            t.start();
        }
        
        Train[] allTrains = trainList.toArray(new Train[trainList.size()]);

        trainList = null;
        
        
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