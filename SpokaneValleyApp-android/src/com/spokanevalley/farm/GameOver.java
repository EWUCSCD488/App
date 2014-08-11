package com.spokanevalley.farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.spokanevalley.ski.Constants;

public class GameOver implements Screen
{
	final Farm game;
	Texture backGround;
	Sprite back;
	Texture button3Image;
	Rectangle button3;
	OrthographicCamera camera;
	SpriteBatch batch;
	private BitmapFont font;

	private int CurrentScore;
	private int MaxScore;
	
	public GameOver(final Farm gam,int CurrentScore, int MaxScore) {
		game = gam;
		
		//Set score for the game and maxScore (both values that are passed in).
		this.CurrentScore = CurrentScore ;
		this.MaxScore = MaxScore ;
		batch = new SpriteBatch();
		
		font = new BitmapFont(Gdx.files.internal("fonts/funfont2.fnt"), false);

		camera = new OrthographicCamera();
		
		//Set the proportions of the screen.
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		//Set the background
		backGround.setEnforcePotImages(false);
		backGround = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(backGround);
		back.setSize(800, 480);
		
		//Set the exit button
		button3Image.setEnforcePotImages(false);
		button3Image = new Texture(Gdx.files.internal("farmAssets/exit.png"));
		button3 = new Rectangle();
		button3.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button3.y = 80;
		button3.width = 128;
		button3.height = 128;
	}

	/***
	 * Runs 60 times a second updating the screen as soon as possible.
	 */
	@Override
	public void render(float delta) 
	{

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		//start drawing
		batch.begin();
		back.draw(batch);
		font.setColor(0.0f, 0.0f, 204.0f, 1.0f);
		font.draw(batch, "Game Over", 330, 425);
		font.draw(batch, "Current score : " + this.CurrentScore, 280, 375);
		font.draw(batch, "High Score :" + this.MaxScore, 280, 325);
		batch.draw(button3Image, button3.x, button3.y);
		batch.end();

		//If screen is touched
		if (Gdx.input.isTouched()) {
			
			//Create a vector from the spot touched
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			//If exit button was touched then exit
			if (pointInRectangle(button3, touchPos.x, touchPos.y))
			{
				Gdx.app.exit();
			}

		}
	}

	/***
	 * Calculates if the incoming parameters are in the scope of the "square" of the image.
	 * @param r
	 * @param x
	 * @param y
	 * @return If the point on the screen touched hit a button or not.
	 */
	public static boolean pointInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		backGround.dispose();
		button3Image.dispose();
		batch.dispose();
		font.dispose();
	}
}
