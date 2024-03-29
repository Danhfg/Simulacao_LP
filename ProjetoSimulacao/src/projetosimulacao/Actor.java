/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosimulacao;

import java.util.List;


/**
 * Abstract class that represents a Actor
 *
 * This is a superclass to all specific tyes of actor in the simulation
 *
 * @author Daniel Henrique
 * @author Felipe Morais
 * 
 * @version 12/10/18
 */
public abstract class Actor {
    // Actor's ocean
    private Ocean ocean;
    
    // Actor's location
    private Location location;
    
    /**
     * Create a new actor at location in ocean.
     * 
     * @param ocean The ocean currently occupied.
     * @param location The location within the field.
     */
    public Actor(Ocean ocean, Location location){
        this.ocean = ocean;
        this.location = location;
    }

    
     /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Return the actors's ocean.
     * @return The actors's ocean.
     */
    public Ocean getOcean()
    {
        return ocean;
    }
    
    /**
     * Place the actor at the new location in the given ocean.
     * @param newLocation The actors's new location.
     */
    public void setLocation(Location newLocation){
        location = newLocation;
    }
    
}
