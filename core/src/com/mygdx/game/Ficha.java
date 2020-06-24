package com.mygdx.game;

import java.util.Random;



public class Ficha extends Casilla {

    private int value;
    private float x,y;
    public Ficha(int value) {
        this.value=value;
    }


    protected int generarFicha(int i){
        Random random = new Random();
        value = random.nextInt(i)+1;
        return value;
    }

    protected Ficha getFicha(Casilla c){
        return this;
    }
    protected int getValor(){
        return this.value;
    }



}
