package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;



public class Board extends Actor {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<Casilla> casillas;
    private int dimension;
    private int numerocasilla=0;
    private int numero=0;
    private float lado;
    private float lado_casilla;
    private double grados=30.0;
    Ficha ficha;


    public Board(int dimension) {
        //this.setDebug(true);
        lado = Constants.WORLD_WIDTH - 2*Constants.WORLD_PADDING;
        this.setBounds(Constants.WORLD_PADDING,(Constants.WORLD_HEIGHT - lado)/2+2,lado,lado);
        this.dimension = dimension;
        this.lado_casilla = 1.6f;

        crearCasillas();
        this.setTouchable(Touchable.disabled);
        generarAdyacentes();

    }

    private void crearCasillas() {
        /*
         * Crea y llena la lista de Casillas que pertenecen al board.
         * Por ahora lo hace asumiendo que siempre seran cuadrados, lo que probablemente
         * deberia ser una subclase.
         */

        casillas = new ArrayList<Casilla>();
        float cambioX=0f;
        float cambioY=0f;
        float xMove = this.getX()+lado_casilla*3;
        float yMove = this.getY()+lado_casilla*3;


        for (int i=0; i < dimension-2; i++) {
            for (int j=0; j <dimension+8; j++) {
                int casilla_value = 0;
                if (i==0 && j==0){
                    Casilla casilla = new Casilla(xMove, yMove-3.0f, casilla_value, lado_casilla,false);
                    Casilla centro = new Casilla(xMove, yMove-3.0f, casilla_value, lado_casilla,false);
                    casillas.add(casilla);
                    break;
                }else if (i==1){
                    cambioX += xMove+(i+0.7)*Math.cos(Math.toRadians(grados));
                    cambioY += yMove-3.0f+(i+0.7)*Math.sin(Math.toRadians(grados));
                    Casilla casilla = new Casilla(cambioX, cambioY, casilla_value, lado_casilla,false);
                    casillas.add(casilla);
                    grados+=60;
                    if (j==5)
                        break;
                }else if (i==2){
                    cambioX += xMove+(i+1.35)*Math.cos(Math.toRadians(grados));
                    cambioY += yMove-3.0f+(i+1.35)*Math.sin(Math.toRadians(grados));
                    grados+=30;
                    if (j > 0) {
                        Casilla casilla = new Casilla(cambioX, cambioY, casilla_value, lado_casilla, false);
                        casillas.add(casilla);
                    }
                }
                cambioX=0f;
                cambioY=0f;
            }
        }
    }

    private void generarAdyacentes(){
        for (Casilla ca:casillas) {
            for (Casilla c:casillas) {
                if (c!=ca) {
                    Vector2 distancia = new Vector2(c.getX() - ca.getX(), c.getY() - ca.getY());
                    if (distancia.len() < 2.1)
                        ca.setVecinos(c);
                }
            }
        }
    }


    public void addToStage(Stage stage) {
        stage.addActor(this);
        for (Casilla c: casillas) {
            stage.addActor(c);
        }

    }


    public float getLado_casilla() {
        return lado_casilla;
    }
}
