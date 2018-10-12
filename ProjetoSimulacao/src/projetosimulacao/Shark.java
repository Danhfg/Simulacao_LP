package projetosimulacao;

import java.awt.List;


/**
 * A simple model of a shark.
 * Sharks age, move, breed, and die.
 * Sharks eat groper or herring but they prefer groper.
 * Sharks are loners - they prefer not to swim next to each other
 * @author Richard Jones and Michael Kolling
 */
public class Shark extends Fish
{

    public Shark(Ocean ocean, Location location) {
        super(ocean, location);
    }

    @Override
    public void act(java.util.List<? extends Actor> newActors) {
        
    }

    
}
