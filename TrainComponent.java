package TrainSim;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainComponent extends JComponent {
    //this will create the tracks and stations in the graphics

    public final Station[] stations;
    public final Route[] routes;
    public final Train[] trains;
    public static final double STATIONSIZE = 50.0;
    public static final Color[] COLORS = new Color[]{Color.BLUE,
        Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE, Color.MAGENTA,
        Color.CYAN, Color.GRAY};

    //limitation is we can only have up to 8 different color tracks
    public TrainComponent(Station[] s, Route[] r, Train[] t) {
        stations = s;
        routes = r;
        trains = t;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (Station s : stations) {
            g2.draw(new Ellipse2D.Double(s.x, s.y, STATIONSIZE, STATIONSIZE));
        }
        for (int i = 0; i < routes.length; i++){
            g2.setColor(COLORS[i]);
            if (i == COLORS.length - 1) {
                i = 0;
            }
            Route r = routes[i];
            for (int j = 0; j < r.s.length - 1; j++) {
                g2.draw(new Line2D.Double(r.getTrack(j).getPoint(-1), 
                        r.getTrack(j).getPoint(1)));
            }
        }
        g2.setColor(Color.BLACK);
        for (Train t : trains) {
            t.draw(g2);
        }
        
    }
    
    public void move(){
        for (Train t : trains) {
            t.move();
        }
        repaint();
    }
}
