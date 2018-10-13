/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosimulacao;

import java.util.Random;

/**
 * Classe que representa a Alga que as Sardinhas comem.
 *
 * @author Daniel Henrique
 * @author Felipe Morais
 */
public class Seaweed extends Resource {

    /**
     * Limiar constante da regeneração da Alga.
     */
    public static final float REGEN_THRESHOLD = 0.0f;

    private float regenThreshold = REGEN_THRESHOLD; // a partir desse valor, a alga começa a se regenerar

    /**
     * Construtor da classe Seaweed
     *
     * @param ocean Oceano no qual a alga está.
     * @param location Localização da alga no oceano.
     * @param amount Quantidade de algas nacélula
     * @param maxAmount Quantidade máxima das células.
     */
    public Seaweed(Ocean ocean, Location location, int amount, int maxAmount) {
        //this.amount = amount;
        super(ocean, location, amount, maxAmount);
    }

    /**
     * Função para obter o regenThreshoud da Alga.
     *
     * @return Retorna o regenThreshould
     */
    public float getRegenThreshold() {
        return regenThreshold;
    }

    /**
     * Função para fazer a alga atuar.
     *
     * @param rng Gerador de números pseudoaleatórios.
     */
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
