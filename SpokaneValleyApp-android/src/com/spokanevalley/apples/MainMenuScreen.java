package com.spokanevalley.apples;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {

	final Apple game;
	Texture backTree;
	Texture buttonImage;
	Rectangle button;
	Sprite back;
	OrthographicCamera camera;
	
	private Context context;

	public MainMenuScreen(final Apple gam/*,Context context*/) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		buttonImage.setEnforcePotImages(false);
		buttonImage = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTON));
		button = new Rectangle();
		button.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button.y = 20;
		button.width = 89;
		button.height = 39;
		
		//this.context = context;

	}

	@Override
	public void render(float delta) {
		backTree.setEnforcePotImages(false);
		backTree = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(backTree);
		back.setSize(480, 800);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		back.draw(game.batch);
		//game.font.setScale((float) 2);
		game.font.setColor(0.0f, 0.0f, 204.0f, 1.0f);// set font color to red
		game.font.draw(game.batch, "Welcome to Apple Catch! ", 90, 445);
		game.font.draw(game.batch, "Tap the button to play", 102, 395);
		game.batch.draw(buttonImage, button.x, button.y);
		game.batch.end();

		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		if (Gdx.input.justTouched()) 
		{
			if (pointInRectangle(button, touchPos.x, touchPos.y))
			{
				
			game.setScreen(new AppleGame(game,context)); //changed to game,context *****
			dispose();
			}
		}
	}

	public static boolean pointInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}