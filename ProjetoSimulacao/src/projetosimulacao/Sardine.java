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
    private static final int HUNGER_MAX = 5; //nvl máx de fome antes da morte.
    
    private int hunger; // nivel de fome atual da sardinha.
    
    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();
    
    private int age; // a idade da sardinha
    
    public Sardine(Ocean ocean, Location location) {
        super(ocean, location);
        this.hunger = 0;
    }

    @Override
    public void act(List<Fish> newActors) {

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
     * Increase the age. This could result in the fox's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
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
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to add newly born foxes to.
     */
    private void giveBirth(List<Fish> newFoxes)
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
