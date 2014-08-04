package com.spokanevalley.apples;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameOver implements Screen
{
	final Apple game;
	Texture backTree;
	Sprite back;

	OrthographicCamera camera;
	
	private int CurrentScore;
	private int MaxScore;
	
	public GameOver(final Apple gam,int CurrentScore, int MaxScore) {
		game = gam;
		this.CurrentScore = CurrentScore ;
		this.MaxScore = MaxScore ;
		

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		backTree.setEnforcePotImages(false);
		backTree = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(backTree);
		back.setSize(480, 800);
		
		// grab the score from the game
		
		

	}

	@Override
	public void render(float delta) 
	{

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		back.draw(game.batch);
		//game.font.setScale((float) 2);
		game.font.setColor(0.0f, 0.0f, 204.0f, 1.0f);// set font color to red
		game.font.draw(game.batch, "Game Over", 170, 425);
		game.font.draw(game.batch, "Current score : " + this.CurrentScore, 170, 525);
		game.font.draw(game.batch, "High Score :" + this.MaxScore, 170, 625);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			//Gdx.app.exit();
			dispose();
		}
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
		backTree.dispose();
        game.getScreen().dispose();
		
		game.batch.dispose();
		
	}
}
