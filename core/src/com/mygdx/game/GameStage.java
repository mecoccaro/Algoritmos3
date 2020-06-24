package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameStage extends Stage {
    Board board;
    Casilla toThrow;
    private TextureRegion graphic;


    public GameStage(Viewport viewport, int board_dimension) {
        super(viewport);
        board = new Board(board_dimension);
        board.addToStage(this);
        float lado_casilla = board.getLado_casilla();
        toThrow = new Casilla(board.getX()+1.0f,board.getY() - 2*lado_casilla,2,lado_casilla, true);
        this.addActor(toThrow);
    }





    public Casilla getToThrow() {
        return toThrow;
    }

}
