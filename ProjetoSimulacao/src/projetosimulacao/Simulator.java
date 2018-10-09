package projetosimulacao;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * (Fill in description and author info here)
 */

public class Simulator
{
    private Ocean ocean;
    private SimulatorView simView;
    private ArrayList<Fish> actors;
    
    
    private int height;
    private int width;
    
    public static void main(String[] args) 
    {
        Simulator sim = new Simulator(50, 60);
        sim.populate();
        sim.run(1000);
        //System.exit(0);
    }
    
    
    
    public Simulator(int height, int width)
    {
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);
        
        // define in which color fish should be shown
        //        //simView.setColor(Fish.class, Color.red);
        simView.setColor(Shark.class, Color.red);
        simView.setColor(Sardine.class, Color.blue);
        simView.setColor(Tuna.class, Color.green);

    }
    
    public void populate(){
        Random rng = new Random();
        ocean.clear();
        
        ArrayList<Location> locs = new ArrayList<Location>(height*width);
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                locs.add(new Location(i,j));
            }
        }
        
        System.out.println(locs.size());
                
                
                
        
        int sharkAmount = 0;
        int sharkMax = 10;
        
        int pos = rng.nextInt(height*width - sharkAmount);
        ocean.placeFish(new Shark(), locs.get(pos));
        locs.remove(pos);
        sharkAmount++;
        
    }
    
    public void run(int steps)
    {
        // put the simulation main loop here
        
        simView.showStatus(0, ocean);
    }
}
