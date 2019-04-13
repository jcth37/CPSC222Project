package TrainSim;
//added graphics stuff

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class Main {
    
    public static void main(String[] args) {
        // Get the map file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // < working directory
        int result = fileChooser.showOpenDialog(null);
        // If file not selected exit program
        if (result != JFileChooser.APPROVE_OPTION)
            return;
        
        // Load the map file
        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
        try (FileReader fileReader = new FileReader(file)) {
            
            int frameWidth, frameHeight;
            int passengerNum;
            ArrayList<Station> stationList = new ArrayList();
            ArrayList<Route> routeList = new ArrayList();
            ArrayList<Train> trainList = new ArrayList();
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] arguments;
            String line;
            ArrayList<String> names = new ArrayList();
            
            arguments = new String[6];
            for (int a = 0; a < arguments.length; a++)
                arguments[a] = bufferedReader.readLine().split("\\s+")[0];
            frameWidth = Integer.parseInt(arguments[0]);
            frameHeight = Integer.parseInt(arguments[1]);
            passengerNum = Integer.parseInt(arguments[2]);
            Train.CAP = Integer.parseInt(arguments[3]);
            Train.DRIVE_DELAY = Integer.parseInt(arguments[4]);
            Station.WAIT_TIME = Integer.parseInt(arguments[5]);
            bufferedReader.readLine();
            // empty line
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                arguments = line.split("\\s+");
                // show errors for too little arguments, or incorrect types
                names.add(arguments[0]);
                int x = Integer.parseInt(arguments[1]);
                int y = Integer.parseInt(arguments[2]);
                Station s = new Station(x, y);
                stationList.add(s);
            }
            // empty line
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                arguments = line.split("\\s+");
                // show errors for too little arguments, or incorrect types
                ArrayList<Station> routeStations = new ArrayList();
                for (String a : arguments)
                    routeStations.add(stationList.get(names.indexOf(a)));
                routeList.add(new Route(routeStations.toArray(new Station[routeStations.size()])));
            }
            names = null;
            
            Station[] allStations = stationList.toArray(new Station[stationList.size()]);
            Route[] allRoutes = routeList.toArray(new Route[routeList.size()]);

            stationList = null;
            routeList = null; 

            for (Station s : allStations)
                s.init(allRoutes.length);  // if #trains > #routes then this must change
            
            Passenger.generate(passengerNum);
        
            for (Route r : allRoutes) {
                Train t = new Train(r);
                trainList.add(t);
                t.start();
            }
        
            Train[] allTrains = trainList.toArray(new Train[trainList.size()]);

            trainList = null;
        
        
            //graphics stuff

            JFrame frame = new JFrame();

            frame.setSize(frameWidth, frameHeight);
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

        } catch (IOException e) {
            System.out.println(e);
        } 
        
    }

}