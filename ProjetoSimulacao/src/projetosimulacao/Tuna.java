package projetosimulacao;

import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a tuna. Tuna age, move, breed, and die. They eat herring.
 *
 * @author Richard Jones and Michael Kolling
 * @author Daniel Henrique
 * @author Felipe Morais
 */
public class Tuna extends Fish {

    /**
     * Método construtor da classe Tuna
     *
     * @param ocean Oceano no qual o Atum está
     * @param location Localização no oceano do Atum
     */
    public Tuna(Ocean ocean, Location location) {
        super(ocean, location);
        setHunger(0);
        setAge(0);
        setBreedingAge(20);
        setBreedingProbability(0.2);
        setHungerMax(10);
        setMaxAge(40);
        setMaxLitterSize(4);
    }

    /**
     * Função que faz o atum nadar e comer.
     *
     * @param newFishes A lista com os Peixes
     */
    public void act(List<Fish> newFishes) {

        incrementAge();
        moreHunger();
        
        if (isAlive()) {
            if (super.getAge() == super.getMaxAge() || super.getHunger() == super.getHungerMax()) {
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
     * Procura uma Sardinha perto do Atum para ele se alimentar.
     *
     * @param location Localização atual do Atum para ele procurar comida.
     * @return Onde a comida foi encontrada, ou nula se não foi.
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
     * Checa se o Atum deve ou não ter um filho no próximo passo.
     *
     * @param newFoxes Uma lita para adicionar os atuns que nasceram.
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
