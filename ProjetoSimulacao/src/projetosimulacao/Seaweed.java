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
public class Seaweed {

    public static final int MAX_AMOUNT = 10;
    public static final int REGEN_THRESHOLD = 3;

    private int amount;
    private int maxAmount = MAX_AMOUNT;
    private int regenThreshold = REGEN_THRESHOLD; // a partir desse valor, a alga começa a se regenerar
    private int regenValue; // valor atual de regen da alga

    public Seaweed(int amount) {
        this.amount = amount;
        this.regenValue = 0;
    }
    
    public void increment(){
        amount++;
    }
    
    public void eat(){
        amount--;
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
