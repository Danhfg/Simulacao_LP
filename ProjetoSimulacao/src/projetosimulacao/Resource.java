/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosimulacao;

/**
 *
 * @author felipemorais2013
 */
public abstract class Resource {
    
    public static final int MAX_AMOUNT = 10;
    
    private int amount;
    private int maxAmount = MAX_AMOUNT;
    
    public Resource(int amount, int maxAmount){
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
}
