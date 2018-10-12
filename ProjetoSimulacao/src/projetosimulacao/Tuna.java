package projetosimulacao;

import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a tuna. Tuna age, move, breed, and die. They eat herring.
 *
 * @author Richard Jones and Michael Kolling
 */
public class Tuna extends Fish {

    public Tuna(Ocean ocean, Location location) {
        super(ocean, location);
        setHunger(0);
        setAge(0);
        setBreedingAge(8);
        setBreedingProbability(0.3);
        setHungerMax(7);
        setMaxAge(20);
        setMaxLitterSize(5);
    }

    public void act(List<Fish> newFishes) {

        incrementAge();
        lessHunger();
        if (isAlive()) {
            if (super.getAge() == super.getMaxAge()) {
                setDead();
            }else{
                giveBirth(newFishes);
                Location location = getLocation();
                Location newLocation = findFood(location);
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getOcean().freeAdjacentLocation(location);
                }
                // See if it was possible to move.
                if (newLocation != null) {
                    setLocation(newLocation);
                } else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }

    /**
     * Tell the Tuna to look for Sardine adjacent to its current location. Only
     * the first live seaweed is eaten.
     *
     * @param location Where in the oncean it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Location location) {
        Ocean ocean = getOcean();
        List<Location> adjacent = ocean.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object fish = ocean.getFishAt(where.getRow(), where.getCol());
            if (fish instanceof Sardine) {
                Sardine sardine = (Sardine) fish;
                if (sardine.isAlive()) {
                    sardine.setDead();
                    lessHunger();
                    // Remove the dead seaweed from the field.
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this fox is to give birth at this step. New births
     * will be made into free adjacent locations.
     *
     * @param newFoxes A list to add newly born foxes to.
     */
    private void giveBirth(List<Fish> newFoxes) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Tuna young = new Tuna(ocean, loc);
            newFoxes.add(young);
        }
    }

}
