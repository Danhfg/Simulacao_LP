package projetosimulacao;

import java.util.List;

/**
 * Write a description of class Fish here.
 *
 * NOTE: This should serve as a superclass to all specific tyes of fish
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Fish extends Actor {

    private boolean alive; // variável que informa o status de vida do peixe.
    /**
     * Constructor for objects of class Fish
     * @param ocean oceano no qual o peixe está
     * @param location localização no ocean do peixe
     * 
     */
    public Fish(Ocean ocean, Location location) {
        super(ocean, location);
        this.alive = true;
    }

    /**
     * Place the fish at the new location in the given ocean.
     *
     * @param newLocation The fish's new location.
     */
    @Override
    public void setLocation(Location newLocation) {
        Location location = getLocation();
        if (location != null) {
            getOcean().clear(location);
        }
        super.setLocation (newLocation);
        getOcean().placeFish(this, newLocation);
    }


    /**
     * Indicate that the fish is no longer alive.
     * It is removed from the ocean.
     */
    public void setDead()
    {
        Location location = getLocation();
        this.alive = false;
        if(location != null) {
            getOcean().clear(location);
            location = null;
            //getOcean() = null;
        }
    }
    
    public boolean isAlive() {
        return alive;
    }

    public abstract void act(List<Fish> newActors);
}
