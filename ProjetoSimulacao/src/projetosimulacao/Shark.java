package projetosimulacao;

import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a shark. Sharks age, move, breed, and die. Sharks eat
 * groper or herring but they prefer groper. Sharks are loners - they prefer not
 * to swim next to each other
 *
 * @author Richard Jones and Michael Kolling
 */
public class Shark extends Fish {

    public Shark(Ocean ocean, Location location) {
        super(ocean, location);
        setHunger(0);
        setAge(0);
        setBreedingAge(20);
        setBreedingProbability(0.385);
        setHungerMax(20);
        setMaxAge(40);
        setMaxLitterSize(2);
    }

    public void act(List<Fish> newFishes) {

        incrementAge();
        moreHunger();
        if (isAlive()) {
            if (super.getAge() == super.getMaxAge() || super.getHunger() == super.getHungerMax()) {
                setDead();
            } else {
                giveBirth(newFishes);
                Location location = getLocation();
                Location newLocation = findFood(location);
                if (newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getSharkFreeAdjacentLocation(location);
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
     * Tell the sardine to look for Seaweed adjacent to its current location.
     * Only the first live seaweed is eaten.
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
            if (fish instanceof Tuna) {
                Tuna tuna = (Tuna) fish;
                if (tuna.isAlive()) {
                    tuna.setDead();
                    lessHunger();
                    // Remove the dead seaweed from the field.
                    return where;
                }
            }
        }
        it = adjacent.iterator();
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
            Shark young = new Shark(ocean, loc);
            newFoxes.add(young);
        }
    }

    /**
     * Tell the sardine to look for Seaweed adjacent to its current location.
     * Only the first live seaweed is eaten.
     *
     * @param location Where in the oncean it is located.
     * @return Where food was found, or null if it wasn't.
     */
    public Location getSharkFreeAdjacentLocation(Location location)
    {
        // The available free ones.
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(location);

        Iterator<Location> it = free.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            if (issharkFreeAdjacentLocation(where)){
                return where;
            }
        }
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }
    
    public boolean issharkFreeAdjacentLocation(Location location)
    {
        // The available free ones.
        Ocean ocean = getOcean();
        List<Location> locs = ocean.adjacentLocations(location);
        int cont = 0; // contador de quantos tubarões tem na proximidade;
        
        Iterator<Location> it = locs.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object fish = ocean.getFishAt(where.getRow(), where.getCol());
            if (fish instanceof Shark) {
                cont++;
            }
        }
        if (cont == 1){
            return true;
        }
        return false;
    }

}
