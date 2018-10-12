/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosimulacao;

import java.util.Random;

/**
 *
 * @author felipemorais2013
 */
public abstract class Resource extends Actor {
    
    public static final int MAX_AMOUNT = 10;
    
    private int amount;
    private int maxAmount = MAX_AMOUNT;
    
    /**
     *
     * @param amount
     * @param maxAmount
     */
    public Resource(Ocean ocean, Location location, int amount, int maxAmount){
        super(ocean, location);
        
        this.amount = amount;
        this.maxAmount = maxAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
    
    public abstract void act(Random rng);
}
