package com.mygdx.game;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.InputAdapter;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.scenes.scene2d.Action;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.scenes.scene2d.InputEvent;
        import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
        import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

        import java.util.ArrayList;

public class Casilla extends Actor {
    protected TextureRegion graphic;
    Ficha ficha;
    ArrayList<Casilla> adyacentes;
    private int value;
    private float lado;
    private boolean throwable;
    private float y_incremento=0.0f;
    private float x_incremento=0.0f;
    private float control=0.0f;
    private double grados=0.0f;

    public Casilla(float x, float y, final int value, float lado, final boolean throwable) {
        this.throwable = throwable;
        this.lado=lado;
        this.value = value;
        adyacentes= new ArrayList<Casilla>();
        crearFicha();
        graphic = MyGdxGame.textureAtlas.findRegion(String.valueOf(value));
        this.setBounds(x,y,lado,lado);
        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                GameStage stage = (GameStage) Casilla.this.getStage();
                Casilla beingThrown = stage.getToThrow();
                beingThrown.disparar(Casilla.this);
                Gdx.input.setInputProcessor(new InputAdapter());

                return true;
            }

        });



    }

    public Casilla() {
    }

    private void crearFicha(){
        ficha=new Ficha(value);
    }

    protected void  setVecinos(Casilla c){
        this.adyacentes.add(c);

    }

    protected ArrayList<Casilla> getAdyacentes(){
        return adyacentes;
    }

    protected int getVecinos(){
        return this.adyacentes.size();
    }

    public Vector2 getPosition() {
        return new Vector2(getX(),getY());
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(graphic,this.getX(),this.getY(),this.getWidth(),this.getHeight());
        if (this.throwable==true && control<= 360) {
            grados = Math.toRadians((double)control);
            x_incremento = 4.4f + 5.2f * (float) Math.cos(grados);
            y_incremento = 4.5f + 5.2f * (float) Math.sin(grados);
            this.setY(2.5f+x_incremento);
            this.setX(1.8f+y_incremento);
            control++;

        } else {
            control = 0.0f;
        }

    }

    public void changeValue(int newValue) {
        if (newValue>8)
            newValue=1;
        this.value = newValue;
        this.graphic = MyGdxGame.textureAtlas.findRegion(String.valueOf(value));
    }

    public void disparar(final Casilla targetCas) {
        if (!this.throwable)
            return;

        final Vector2 startingPos = new Vector2(this.getX(), this.getY());
        Vector2 targetPos = targetCas.getPosition();
        ThrowAction throwAction = new ThrowAction(targetPos);
        throwAction.setSpeed(Constants.THROW_SPEED);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(throwAction);
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                targetCas.changeValue(Casilla.this.value);
                contraerVecinos(targetCas);
                return true;
            }

        });


        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {

                Ficha ficha = new Ficha(value);
                Casilla.this.changeValue(ficha.generarFicha(8));
                Casilla.this.setPosition(startingPos.x, startingPos.y);
                Gdx.input.setInputProcessor(targetCas.getStage());
                return true;
            }
        });

        this.addAction(sequenceAction);

    }
    public void contraerVecinos(Casilla c){
        int value=c.value;
        int cont=0;
        for(Casilla casilla:c.adyacentes){
            if (casilla.value==value)
                cont++;
        }
        if (cont>1 )
            for(Casilla casilla:c.adyacentes){
                if (casilla.value==value) {
                    //contraerVecinos(casilla);
                    casilla.changeValue(0);
                    c.changeValue(value+1);
                }
            }
    }
}





