package projetosimulacao;

import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a shark. Sharks age, move, breed, and die. Sharks eat
 * groper or herring but they prefer groper. Sharks are loners - they prefer not
 * to swim next to each other
 *
 * @author Richard Jones and Michael Kolling
 * @author Daniel Henrique
 * @author Felipe Morais
 * 
 */
public class Shark extends Fish {

    /**
     * Método construtor da classe Shark
     *
     * @param ocean Oceano no qual o Tubarão está
     * @param location Localização no oceano do Tubarão
     */
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

    /**
     * Função que faz o Tubarão nadar e comer.
     *
     * @param newFishes A lista com os Peixes
     */
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
     * Procura um Atum ou uma Sardinha perto do tubarão para ele se alimentar.
     *
     * @param location Localização atual do tubarão para ele procurar comida.
     * @return Onde a comida foi encontrada, ou nula se não foi.
     */
    private Location findFood(Location location) {
        Ocean ocean = getOcean();
        List<Location> adjacent = ocean.adjacentLocations(location);
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
     * Checa se o tubarão deve ou não ter um filho no próximo passo.
     *
     * @param newFoxes Uma lita para adicionar os tubarões que nasceram.
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
     * A função busca, caso posível, uma posição livre de tubarões para o 
     * tubarão nadar.
     * 
     * @param location
     * @return Retorna a melhor localização para o tubarão andar 
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
    
    /**
     * A função verifica se uma localizaçao possui tubarões por perto.
     * 
     * @param location Localização para procurar ausência de tubarão 
     * @return Retorna True se a localização não possui tubarões perto
     */
    public boolean issharkFreeAdjacentLocation(Location location)
    {
        // The available free ones.
        Ocean ocean = getOcean();
        List<Location> locs = ocean.adjacentLocations(location);
        int sharks = 0; // contador de quantos tubarões tem na proximidade;
        
        Iterator<Location> it = locs.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object fish = ocean.getFishAt(where.getRow(), where.getCol());
            if (fish instanceof Shark) {
                sharks++;
            }
        }
        return sharks == 1;
    }

}
