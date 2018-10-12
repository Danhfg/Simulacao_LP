package projetosimulacao;

import java.util.List;
import java.util.Iterator;


/**
 * A simple model of a shark.
 * Sharks age, move, breed, and die.
 * Sharks eat groper or herring but they prefer groper.
 * Sharks are loners - they prefer not to swim next to each other
 * @author Richard Jones and Michael Kolling
 */
public class Shark extends Fish
{
    private static final int HUNGER_MAX = 10; //nvl máx de fome antes da morte.
    
    private int hunger; // nivel de fome atual do Tubarão.
    
    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;

    public Shark(Ocean ocean, Location location) {
        super(ocean, location);
    }

    @Override
    public void act(List newActors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            if(fish instanceof Tuna) {
                Tuna tuna = (Tuna) fish;
                if(tuna.isAlive()) { 
                    tuna.setDead();
                    lessHunger();
                    // Remove the dead seaweed from the field.
                    return where;
                }
            }
        }
        it = adjacent.iterator();
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
