package projetosimulacao;

import java.util.List;
import java.util.Iterator;
import java.util.Random;


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
        setHunger(0);
    }

    @Override
    public void act(List<Actor> newActors) {
        incrementAge();
        lessHunger();
        if (isAlive())
        {
            giveBirth(newActors);   
            Location location = getLocation();
            Location newLocation = findFood(location);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getOcean().freeAdjacentLocation(location);
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to add newly born foxes to.
     */
    private void giveBirth(List<Actor> newFoxes)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Sardine young = new Sardine(ocean, loc);
            newFoxes.add(young);
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
            Object recource = ocean.getResourceAt(where.getRow(), where.getCol());
            if(recource instanceof Seaweed) {
                Seaweed seaweed = (Seaweed) recource;
                if(seaweed.getRegenValue() >= 0) { 
                    seaweed.setRegenValue(seaweed.getRegenValue()-1);
                    lessHunger();
                    // Remove the dead seaweed from the field.
                    return where;
                }
            }
        }
        return null;
    }
    
}
