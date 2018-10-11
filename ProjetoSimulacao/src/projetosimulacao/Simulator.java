package projetosimulacao;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * (Fill in description and author info here)
 */
public class Simulator {

    private Ocean ocean;
    private SimulatorView simView;
    private ArrayList<Fish> actors;

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

        //ocean.clear();

        ArrayList<Location> locs = new ArrayList();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                locs.add(new Location(i, j));
            }
        }

        int sharkMax = 10;
        int sardineMax = 20;
        int tunaMax = 30;

        for (int i = 0; i < sharkMax; i++) {
            int pos = rng.nextInt(height * width - i);
            ocean.placeFish(new Shark(), locs.get(pos));
            locs.remove(pos);
        }
        
        for (int i = 0; i < sardineMax; i++) {
            int pos = rng.nextInt(height * width - i - sharkMax);
            ocean.placeFish(new Sardine(), locs.get(pos));
            locs.remove(pos);
        }
        
        for (int i = 0; i < tunaMax; i++) {
            int pos = rng.nextInt(height * width - i - sharkMax - tunaMax);
            ocean.placeFish(new Tuna(), locs.get(pos));
            locs.remove(pos);
        }

    }

    public void run(int steps) {
        // put the simulation main loop here

        simView.showStatus(0, ocean);
    }
}
