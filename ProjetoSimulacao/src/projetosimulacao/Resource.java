/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosimulacao;

import java.util.Random;

/**
 * Classe pai para Sardinha.
 *
 * @author Daniel Henrique
 * @author Felipe Morais
 */
public abstract class Resource extends Actor {
    
    /**
     * Limiar constante da regeneração do Recurso.
     */
    public static final int MAX_AMOUNT = 10;
    
    private int amount;
    private int maxAmount = MAX_AMOUNT;
    
    /**
     * Construtor da classe Resource
     *
     * @param ocean
     * @param location
     * @param amount
     * @param maxAmount
     */
    public Resource(Ocean ocean, Location location, int amount, int maxAmount){
        super(ocean, location);
        
        this.amount = amount;
        this.maxAmount = maxAmount;
    }

    /**
     * Função para obter o regenThreshoud da Alga.
     *
     * @return Retorna a quantidade de recursos atuais.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Função para atribuir um valor na quantidade de recursos atual.
     *
     * @param amount quantidade de recurso para ser a atual.
     */
    public void setAmount(int amount) {
        if (amount < 0)
            this.amount = 0;
        else
            this.amount = amount;
    }

    /**
     * Função para obter a quantidade máxima de recurso.
     *
     * @return Retorna a quantidade máxima de recursos.
     */
    public int getMaxAmount() {
        return maxAmount;
    }

    /**
     * Modifica a quantidade máxima de recursos.
     *
     * @param maxAmount Quantidade máxima de recursos 
     */
    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
    
    /**
     * Função para fazer a alga atuar.
     *
     * @param rng Gerador de números pseudoaleatórios.
     */
    public abstract void act(Random rng);
}
