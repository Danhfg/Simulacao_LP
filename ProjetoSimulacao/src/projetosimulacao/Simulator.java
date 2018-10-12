package projetosimulacao;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private ArrayList<Resource> resources;

    private int height;
    private int width;
    
    private int step;

    private Random rng;

    public static void main(String[] args) {
        Simulator sim = new Simulator(50, 60);
        sim.clear();
        sim.generateResources();
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
        
        fishes = new ArrayList();
        resources = new ArrayList();
             
        step = 0;

    }
    
    public void clear(){
        ocean.clear();
    }
    
    public void generateResources(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Location l = new Location(i,j);
                Resource r = new Seaweed(ocean, l, rng.nextInt(11), 10);
                resources.add(r);
                ocean.placeResource(r, l);
            }
            //System.out.println("");
        }
    }

    public void populate() {
        //ocean.placeFish(new Shark(), 1,1);

        ArrayList<Location> locs = new ArrayList();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                locs.add(new Location(i, j));
            }
        }

        int[] max;
        max = new int[]{ MAX_SHARKS, MAX_SARDINES, MAX_TUNAS};

        int total = 0;
        
        for (int i = 0; i < max.length; i++) {
            for (int j = 0; j < max[i]; j++) {
                int pos = rng.nextInt(height*width - total);
                
                Fish fish;
                
                if(i==0)
                    fish = new Shark(ocean, locs.get(pos));
                else if (i==1)
                    fish = new Sardine(ocean, locs.get(pos));
                else 
                    fish = new Tuna(ocean, locs.get(pos));
                
                ocean.placeFish(fish, locs.get(pos));  
                fishes.add(fish);
                
                total++;
            }
        }

    }
    
    public void run(int steps) {
        // put the simulation main loop here
        runOneStep();
        for (int i = 0; i < steps && simView.isViable(ocean); i++) {
            runOneStep();
        }
        
    }
    
    public void runOneStep(){
        step++;
        
        // Provide space for newborn animals.
        List<Fish> newFishes = new ArrayList<Fish>();        
        // Let all rabbits act.
        for(Iterator<Fish> it = fishes.iterator(); it.hasNext(); ) {
            Fish fish = it.next();
            fish.act(newFishes);
            if(! fish.isAlive()) {
                it.remove();
            }
        }
        
        // Add the newly born foxes and rabbits to the main lists.
        fishes.addAll(newFishes);
        
        // atualiza os recursos no mapa        
        for(Iterator<Resource> it = resources.iterator(); it.hasNext(); ) {
            Resource resource = it.next();
            
            resource.act(rng);
        }
 
        simView.showStatus(step, ocean);
    }
}
