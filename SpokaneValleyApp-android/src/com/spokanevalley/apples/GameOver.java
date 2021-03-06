/*
 * This is the class that creates the Game Over screen for the game. It handles the all of the button presses and ends the activity
 */

package com.spokanevalley.apples;
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
	final Apple game;
	Texture background;
	Sprite back;
	Texture button3Image;
	Rectangle button3;
	OrthographicCamera camera;
	
	private int CurrentScore;
	private int MaxScore;
	
	public GameOver(final Apple gam,int CurrentScore, int MaxScore) {
		game = gam;
		this.CurrentScore = CurrentScore ;
		this.MaxScore = MaxScore ;
		

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		//Initialize the background and the exit button
		
		background.setEnforcePotImages(false);
		background = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(background);
		back.setSize(480, 800);
		
		button3Image.setEnforcePotImages(false);
		button3Image = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTON3));
		button3 = new Rectangle();
		button3.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button3.y = 180;
		button3.width = 100;
		button3.height = 50;

		

	}

	
	//This is the main method for the menu as it handles the button presses and and displays your scores
	@Override
	public void render(float delta) 
	{

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		back.draw(game.batch);
		//game.font.setScale((float) 2);
		game.font.setColor(0.0f, 0.0f, 204.0f, 0.0f);// set font color to red
		game.font.draw(game.batch, "Game Over", 170, 650);
		game.font.draw(game.batch, "Current score : " + this.CurrentScore, 140, 600);
		game.font.draw(game.batch, "High Score :" + this.MaxScore, 140, 550);
		game.batch.draw(button3Image, button3.x, button3.y);
		game.batch.end();

		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		
		//Determine if the exit button is pressed
		
		if (Gdx.input.isTouched()) {
			if (pointInRectangle(button3, touchPos.x, touchPos.y))
			{
				Gdx.app.exit();
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
		background.dispose();

		
	}
}
