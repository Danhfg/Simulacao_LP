package projetosimulacao;

import java.awt.List;


/**
 * A simple model of a tuna.
 * Tuna age, move, breed, and die.
 * They eat herring.
 * 
 * @author Richard Jones and Michael Kolling
 */
public class Tuna extends Fish
{

    public Tuna(Ocean ocean, Location location) {
        super(ocean, location);
    }

    @Override
    public void act(List newActors) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
