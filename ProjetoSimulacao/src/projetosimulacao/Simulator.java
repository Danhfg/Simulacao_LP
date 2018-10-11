package projetosimulacao;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * (Fill in description and author info here)
 */
public class Simulator {

    public static final int MAX_SHARKS = 20;    
    public static final int MAX_SARDINES = 40;
    public static final int MAX_TUNAS = 50;

    
    private Ocean ocean;
    private SimulatorView simView;
    private ArrayList<Fish> fishes;
    

    private int height;
    private int width;

    private Random rng;

    public static void main(String[] args) {
        Simulator sim = new Simulator(50, 60);
        sim.populate();
        sim.run(1000);
        //System.exit(0);
    }

    public Simulator(int height, int width) {
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);

        // define in which color fish should be shown
        //        //simView.setColor(Fish.class, Color.red);
        simView.setColor(Shark.class, Color.red);
        simView.setColor(Sardine.class, Color.pink);
        simView.setColor(Tuna.class, Color.green);

        this.height = height;
        this.width = width;

        rng = new Random();

    }

    public void populate() {
        //ocean.placeFish(new Shark(), 1,1);

        ocean.clear();
        ocean.generateResources();

        ArrayList<Location> locs = new ArrayList();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                locs.add(new Location(i, j));
            }
        }

        Pair< Class<? extends Fish>, Integer>[] max;
        max = new Pair[]{ new Pair(Shark.class , MAX_SHARKS),
                          new Pair(Sardine.class, MAX_SARDINES ),
                          new Pair(Tuna.class, MAX_TUNAS)};

        
        int total = 0;
        for (int i = 0; i < max.length; i++) {
            for (int j = 0; j < max[i].getY(); j++) {
                int pos = rng.nextInt(height*width - total);
                
                try {
                    ocean.placeFish(max[i].getX().newInstance(), locs.get(pos));
                } catch (InstantiationException ex) {
                    Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                total++;
            }
        }
    

    }

    public void run(int steps) {
        // put the simulation main loop here
        
        simView.showStatus(0, ocean);
    }
}
