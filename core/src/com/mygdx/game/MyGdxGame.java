package com.mygdx.game;

		import com.badlogic.gdx.ApplicationAdapter;
		import com.badlogic.gdx.Gdx;
		import com.badlogic.gdx.graphics.GL20;
		import com.badlogic.gdx.graphics.OrthographicCamera;
		import com.badlogic.gdx.graphics.Texture;
		import com.badlogic.gdx.graphics.g2d.BitmapFont;
		import com.badlogic.gdx.graphics.g2d.SpriteBatch;
		import com.badlogic.gdx.graphics.g2d.TextureAtlas;
		import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
		import com.badlogic.gdx.utils.viewport.FitViewport;
		import com.badlogic.gdx.utils.viewport.Viewport;
		import com.badlogic.gdx.graphics.Color;

public class MyGdxGame extends ApplicationAdapter {
	GameStage stage;

	public static TextureAtlas textureAtlas;
	private Texture circuloTexture;
	private float score;
	private String yourScoreName;
	private SpriteBatch batch;
	BitmapFont yourBitmapFontName;
	private BitmapFont font;
	public int size = 16;
	public Color color = Color.WHITE;





	@Override
	public void create () {
		textureAtlas = new TextureAtlas(Gdx.files.internal("numeros.atlas"));
		circuloTexture = new Texture(Gdx.files.internal("circulo2.png"));
		OrthographicCamera camera = new OrthographicCamera();
		Viewport viewport = new FitViewport(Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT,camera);
		stage = new GameStage(viewport,Constants.DIMENSION);
		Gdx.input.setInputProcessor(stage);
		score = 21;
		yourScoreName = "" + score;
		yourBitmapFontName = new BitmapFont();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);

	}

	@Override
	public void render () {
		cls();
		batch.begin();
		if(stage.getToThrow().getX() == 6.3f && stage.getToThrow().getY() == 12.1f){
			score -= 0.33;
			yourScoreName = "" + (int)score;
		}
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.draw(batch, yourScoreName, 300, 400);
		batch.draw(circuloTexture,80,65,450,280);
		batch.end();
		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width,height,true);
	}

	@Override
	public void dispose () {
		stage.dispose();
		textureAtlas.dispose();
	//	generator.dispose();
	}

	public static void cls() {
		Gdx.gl.glClearColor(0x46/255.0f, 0x45/255.0f, 0x54/255.0f, 0xff/255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
