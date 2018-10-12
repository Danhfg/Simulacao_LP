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
    private static final int HUNGER_MAX = 7; //nvl máx de fome antes da morte.
    
    private int hunger; // nivel de fome atual do Arum.
    
    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.20;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;

    public Tuna(Ocean ocean, Location location) {
        super(ocean, location);
        this.hunger = 0;
    }

    
    public void act(List<Fish> newFishes) {
        
    }

    
    /**
     * Makes this fish more hungry
     */
    private void moreHunger()
    {
        hunger++;
        if(hunger > HUNGER_MAX) {
            setDead();
        }
    }  
    
    /**
     * Make this fish less hungry. This could result in the fox's death.
     */
    private void lessHunger()
    {
        if(hunger > 0) {
            hunger--;
        }
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
