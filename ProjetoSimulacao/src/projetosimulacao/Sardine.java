package projetosimulacao;

import java.awt.List;


/**
 * A simple model of a sardine.
 * sardines age, move, breed, and die.
 * They eat plankton.
 * They exhibit flocking behaviour - they tend to seek company. 
 * If they spot a predator close by, they panic.
 * 
 */
public class Sardine extends Fish
{

    public Sardine(Ocean ocean, Location location) {
        super(ocean, location);
    }

    @Override
    public void act(java.util.List<? extends Actor> newActors) {
       
    }
    
}
