package com.spokanevalley.ski;
//package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameOver implements Screen
{
	final Ski game;
	Texture background;
	Sprite back;
	Texture button3Image;
	Rectangle button3;
	OrthographicCamera camera;
	
	private int CurrentScore;
	private int MaxScore;
	
	public GameOver(final Ski gam,int CurrentScore, int MaxScore) {
		game = gam;
		this.CurrentScore = CurrentScore ;
		this.MaxScore = MaxScore ;
		

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		button3Image.setEnforcePotImages(false);
		button3Image = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTON3));
		button3 = new Rectangle();
		button3.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button3.y = 80;
		button3.width = 100;
		button3.height = 50;
		
		// grab the score from the game
		
		

	}

	@Override
	public void render(float delta) 
	{
		background.setEnforcePotImages(false);
		background = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(background);
		back.setSize(800, 480);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		back.draw(game.batch);
		//game.font.setScale((float) 2);
		game.font.setColor(0.0f, 0.0f, 204.0f, 1.0f);// set font color to red
		game.font.draw(game.batch, "Game Over", 280, 425);
		game.font.draw(game.batch, "Current score : " + this.CurrentScore, 200, 375);
		game.font.draw(game.batch, "High Score :" + this.MaxScore, 200, 325);
		game.batch.draw(button3Image, button3.x, button3.y);
		game.batch.end();

		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		if (Gdx.input.isTouched()) {
			if (pointInRectangle(button3, touchPos.x, touchPos.y))
			{
			dispose();
			}
		}
	}

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
		// TODO Auto-generated method stub
		
	}
}
