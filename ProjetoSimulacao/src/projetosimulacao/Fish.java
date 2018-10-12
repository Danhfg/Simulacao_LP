package projetosimulacao;


import java.util.List;

import java.util.Random;


/**
 * Write a description of class Fish here.
 *
 * NOTE: This should serve as a superclass to all specific tyes of fish
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Fish extends Actor {
    
    private int hungerMax; //nvl máx de fome antes da morte.
    
    private int hunger; // nivel de fome atual do peixe.
    
    // The age at which a rabbit can start to breed.
    private int breedingAge;
    // The age to which a fish can live.
    private int maxAge;
    // The likelihood of a fish breeding.
    private double breedingProbability;
    // The maximum number of births.
    private int maxLitterSize;
    
    private int age; // a idade da sardinha
    
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();
    
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
     * Make this fish less hungry. This could result in the fox's death.
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
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= breedingAge;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHungerMax() {
        return hungerMax;
    }

    public void setHungerMax(int hungerMax) {
        this.hungerMax = hungerMax;
    }

    public int getBreedingAge() {
        return breedingAge;
    }

    public void setBreedingAge(int breedingAge) {
        this.breedingAge = breedingAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public double getBreedingProbability() {
        return breedingProbability;
    }

    public void setBreedingProbability(double breedingProbability) {
        this.breedingProbability = breedingProbability;
    }

    public int getMaxLitterSize() {
        return maxLitterSize;
    }

    public void setMaxLitterSize(int maxLitterSize) {
        this.maxLitterSize = maxLitterSize;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    

    public abstract void act(List<Fish> newActors);
}
