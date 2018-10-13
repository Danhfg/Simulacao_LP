package projetosimulacao;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * A simple model of a sardine. sardines age, move, breed, and die. They eat
 * plankton. They exhibit flocking behaviour - they tend to seek company. If
 * they spot a predator close by, they panic.
 *
 * @author Daniel Henrique
 * @author Felipe Morais
 */
public class Sardine extends Fish {

    /**
     * Construtor Para a classe Sandine
     *
     * @param ocean Oceano no qual as sardinhas estão 
     * @param location Localização da sardinha
     */
    public Sardine(Ocean ocean, Location location) {
        super(ocean, location);
        setHunger(0);
        setAge(0);
        setBreedingAge(8);
        setBreedingProbability(0.3);
        setHungerMax(5);
        setMaxAge(20);
        setMaxLitterSize(2);
    }

    /**
     * Função para fazer com que a Sardinha coma, fuja e nade.
     *
     * @param newActors Lista com os peixes do oceano
     */
    @Override
    public void act(List<Fish> newActors) {

        incrementAge();
        moreHunger();
        if (isAlive()) {
            if (super.getAge() == super.getMaxAge() || super.getHunger() == super.getHungerMax()) {
                setDead();
            } else {
                giveBirth(newActors);

                List<Location> locs = safeLocations();
                if (locs == null) {
                    Location panic = getOcean().freeAdjacentLocation(getLocation());

                    if (panic != null) {
                        setLocation(panic);
                    }
                } else {
                    // se estiver com fome
                    if (super.getHunger() >= super.getHungerMax() / 2) {
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
                        
                    //agrupar com sardinhas
                    } else {
                        Location newLocation = agroup();
                        
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

        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Verifique se esta sardinha pode ter filhos nesse passo.
     *
     * @param newFoxes Lista para adicionar as sardinhas que nasceram .
     */
    private void giveBirth(List<Fish> newFoxes) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Ocean ocean = getOcean();
        List<Location> free = ocean.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Sardine young = new Sardine(ocean, loc);
            newFoxes.add(young);
        }
    }

    /**
     * Procura uma alga perto da sardinha para ela se alimentar.
     *
     * @param location Localização atual da sardinha para ela procurar comida.
     * @return Onde a comida foi encontrada, ou nula se não foi.
     */
    private Location findFood(Location location) {
        Ocean ocean = getOcean();
        List<Location> adjacent = ocean.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object recource = ocean.getResourceAt(where.getRow(), where.getCol());
            if (recource instanceof Seaweed) {
                Seaweed seaweed = (Seaweed) recource;
                if (seaweed.getAmount() >= 0) {
                    seaweed.setAmount(seaweed.getAmount() - 1);
                    lessHunger();
                    // Remove the dead seaweed from the field.
                    return where;
                }
            }
        }
        return null;

    }
    
        /**
     * Procura posições segurar para a sardinha nadar.
     *
     * @return Retorna uma lista de localizações seguras.
     */
    private List<Location> safeLocations() {

        Ocean ocean = getOcean();
        List<Location> freeAdjacent = ocean.getFreeAdjacentLocations(getLocation());
        Iterator<Location> it = freeAdjacent.iterator();

        boolean predator = false;

        List<Location> safe = new LinkedList<Location>();
        
        while (it.hasNext()) {

            Location where = it.next();

            List<Location> adjacent = ocean.adjacentLocations(getLocation());

            predator = false;

            for (Location l : adjacent) {
                Fish f = ocean.getFishAt(l.getRow(), l.getCol());

                if (f != null && !(f instanceof Sardine)) {
                    predator = true;
                }
            }

            if (!predator) {
                safe.add(where);
            }
        }
        if (safe.isEmpty()) {
            return null;
        } else {
            return freeAdjacent;
        }

    }
    /**
     * Localização para para fazer as sardinhas se agruparem.
     *
     * @return Retorna a posição com sardinhas para a atual se agrupar.
     */
    private Location agroup(){
        Ocean ocean = getOcean();
        List<Location> freeAdjacent = ocean.getFreeAdjacentLocations(getLocation());
        Iterator<Location> it = freeAdjacent.iterator();

        int friend;

        List<Location> safe = new LinkedList<Location>();
        
        while (it.hasNext()) {

            Location where = it.next();

            List<Location> adjacent = ocean.adjacentLocations(getLocation());

            friend = 0;

            for (Location l : adjacent) {
                Fish f = ocean.getFishAt(l.getRow(), l.getCol());

                if (f != null && (f instanceof Sardine)) {
                    friend++;
                }
                
                if (friend > 1)
                    return where;
            }

           
        }
        
        return freeAdjacent.get(0);
    }

}
