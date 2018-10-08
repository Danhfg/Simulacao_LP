import java.awt.Color;

/**
 * (Fill in description and author info here)
 */

public class Simulator
{
    private Ocean ocean;
    private SimulatorView simView;
    
    
    public static void main(String[] args) 
    {
        Simulator sim = new Simulator(50, 60);
        sim.run(1000);
    }
    
    
    
    public Simulator(int height, int width)
    {
        ocean = new Ocean(height, width);
        simView = new SimulatorView(height, width);
        
        // define in which color fish should be shown
        simView.setColor(Fish.class, Color.red);
    }
    
    public void run(int steps)
    {
        // put the simulation main loop here
        
        simView.showStatus(0, ocean);
    }
}
