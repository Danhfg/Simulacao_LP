package projetosimulacao;


import java.util.List;
import java.util.Random;

/**
 * Abstract class that represents a Fish
 *
 * This is a superclass to all specific tyes of fish
 *
 * @author Daniel Henrique
 * @author Felipe Morais
 * 
 * @version 12/10/18
 */

public abstract class Fish extends Actor {
    
    // Max amount of hunger.
    private int hungerMax; 
    // Amount of hunger.
    private int hunger; 
    // The age at which a rabbit can start to breed.
    private int breedingAge;
    // The age to which a fish can live.
    private int maxAge;
    // The likelihood of a fish breeding.
    private double breedingProbability;
    // The maximum number of births.
    private int maxLitterSize;
    // Age of the fish
    private int age; 
    
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();
    
    // Status of the fish
    private boolean alive;
    
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
    
    /**
     * Indicates if the fish is alive or not
     * 
     * @return The alive status
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
    }
    
    /**
     * Makes this fish more hungry
     */
    protected void moreHunger()
    {
        hunger++;
        if(hunger > hungerMax) {
            setDead();
        }
    }  
    
    /**
     * Make this fish less hungry. This could result in the fish's death.
     */
    protected void lessHunger()
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
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * A fish can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= breedingAge;
    }

    /**
     * Set the fish's hunger 
     * @param hunger The hunger
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * @return The fish's hunger
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * @return The max value fish's hunger
     */
    public int getHungerMax() {
        return hungerMax;
    }

    /**
     * Set the max fish's hunger
     * @param hungerMax The max hunger of the fish
     */
    public void setHungerMax(int hungerMax) {
        this.hungerMax = hungerMax;
    }

    /**
     * @return The breeding age of the fish
     */
    public int getBreedingAge() {
        return breedingAge;
    }

    /**
     * Set the fish's breeding age
     * @param breedingAge The fish's breeding age
     */
    public void setBreedingAge(int breedingAge) {
        this.breedingAge = breedingAge;
    }

    /**
     * @return The max age
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Set the max age
     * @param maxAge The max age
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * @return The breeding probability
     */
    public double getBreedingProbability() {
        return breedingProbability;
    }

    /**
     * Set the fish's breeding probability
     * @param breedingProbability The breeding probability
     */
    public void setBreedingProbability(double breedingProbability) {
        this.breedingProbability = breedingProbability;
    }

    /**
     * @return The max litter size
     */
    public int getMaxLitterSize() {
        return maxLitterSize;
    }

    /**
     * Set the fish's max litter size
     * @param maxLitterSize The max litter size
     */
    public void setMaxLitterSize(int maxLitterSize) {
        this.maxLitterSize = maxLitterSize;
    }

    /**
     * @return The fish's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the fish's age
     * @param age The fish's age
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * The behaviour of the fish
     * @param newActors the born fishes
     */
    public abstract void act(List<Fish> newActors);
}
