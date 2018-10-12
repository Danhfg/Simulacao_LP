package projetosimulacao;

import java.util.List;
import java.util.Iterator;


/**
 * A simple model of a tuna.
 * Tuna age, move, breed, and die.
 * They eat herring.
 * 
 * @author Richard Jones and Michael Kolling
 */
public class Tuna extends Fish
{
    public Tuna(Ocean ocean, Location location) {
        super(ocean, location);
        setHunger(0);
        setAge(0);
        setBREEDING_AGE(5);
        setBREEDING_PROBABILITY(0.15);
        setHUNGER_MAX(5);
        setMAX_AGE(40);
        setMAX_LITTER_SIZE(4);
    }

    
    public void act(List<Fish> newFishes) {
        
    }

    
    /**
     * Tell the sardine to look for Seaweed adjacent to its current location.
     * Only the first live seaweed is eaten.
     * @param location Where in the oncean it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location)
    {
        Ocean ocean = getOcean();
        List<Location> adjacent = ocean.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object fish = ocean.getFishAt(where.getRow(), where.getCol());
            if(fish instanceof Sardine) {
                Sardine sardine = (Sardine) fish;
                if(sardine.isAlive()) { 
                    sardine.setDead();
                    lessHunger();
                    // Remove the dead seaweed from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
}
