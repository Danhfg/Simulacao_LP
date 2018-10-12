/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosimulacao;

import java.util.List;
import java.util.Random;

/**
 *
 * @author felipemorais2013
 */
public class Seaweed extends Resource {

    public static final float REGEN_THRESHOLD = 0.0f;

    private float regenThreshold = REGEN_THRESHOLD; // a partir desse valor, a alga começa a se regenerar

    public Seaweed(Ocean ocean, Location location, int amount, int maxAmount) {
        //this.amount = amount;
        super(ocean, location, amount, maxAmount);
    }

    public float getRegenThreshold() {
        return regenThreshold;
    }

    public void act(Random rng) {
        
        float b = rng.nextFloat();

        if (b > regenThreshold) {
            int a = super.getAmount();
            
            if (a == Resource.MAX_AMOUNT) {
                super.setAmount(0);
            } else {
                super.setAmount(a + 1);
            }
        }

    }

}
