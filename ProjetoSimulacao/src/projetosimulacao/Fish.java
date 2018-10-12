package projetosimulacao;

/**
 * Write a description of class Fish here.
 *
 * NOTE: This should serve as a superclass to all specific tyes of fish
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Fish extends Actor {

    /**
     * Constructor for objects of class Fish
     */
    public Fish(Ocean ocean, Location location) {
        super(ocean, location);
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

    public boolean isAlive(){
        return true;
    }
    
}
