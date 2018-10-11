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
public class Seaweed extends Resource {

    
    public static final int REGEN_THRESHOLD = 3;

    
    private int regenThreshold = REGEN_THRESHOLD; // a partir desse valor, a alga começa a se regenerar
    private int regenValue; // valor atual de regen da alga

    public Seaweed(int amount, int maxAmount) {
        //this.amount = amount;
        super(amount, maxAmount);
        this.regenValue = 0;
    }
    

    public int getRegenThreshold() {
        return regenThreshold;
    }


    public int getRegenValue() {
        return regenValue;
    }

    public void setRegenValue(int regenValue) {
        this.regenValue = regenValue;
    }

}
