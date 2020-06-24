package com.mygdx.game;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;


public class ThrowAction extends MoveToAction {
    private final String TAG = getClass().getSimpleName();
    private float Speed;

    public ThrowAction(Vector2 targetPos) {
        super();
        this.setPosition(targetPos.x,targetPos.y);
    }

    private float calculateTime(float speed) {
        Actor actor = getActor();
        if (actor != null) {
            float targetX = getX();
            float targetY = getY();
            float currentX = actor.getX();
            float currentY = actor.getY();
            Vector2 distancia = new Vector2(currentX - targetX, currentY - targetY);
            float distancia_mod = distancia.len();
            return distancia_mod / speed;
        }

        return 0f;
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
        float dur = calculateTime(Speed);
        this.setDuration(dur);
    }

    public void setSpeed(float speed) {
        this.Speed = speed;
    }
}
