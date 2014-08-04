package com.spokanevalley.farm;
//package com.me.mygdxgame;
import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.spokanevalley.ski.Constants;

public class MainMenuScreen implements Screen {

	final Farm game;
	Texture backGround,buttonImage, button2Image, button3Image, howTotex;
	Rectangle button, button2, button3, howTo;
	Sprite back;
	Boolean how= false;
	OrthographicCamera camera;
	public BitmapFont font;

	
	private Context context;

	public MainMenuScreen(final Farm gam, Context context) {
		game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		buttonImage.setEnforcePotImages(false);
		buttonImage = new Texture(Gdx.files.internal("farmAssets/button_play.png"));
		button = new Rectangle();
		button.x = Constants.WIDTH_GAME / 3 - 64; // Put 2/3 of the way on the x scale
		button.y = 50;
		button.width = 128;
		button.height = 128;
		
		button3Image.setEnforcePotImages(false);
		button3Image = new Texture(Gdx.files.internal("farmAssets/button_stop.png"));
		button3 = new Rectangle();
		button3.x = (Constants.WIDTH_GAME / 3)*2 - 64; // Put 1/3 of the way on the x scale
		button3.y = 50;
		button3.width = 128;
		button3.height = 128;
		
		this.context = context;
		create();
	}

	public void create() {
		backGround.setEnforcePotImages(false);
		backGround = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(backGround);
		back.setSize(800, 480);
	}
	
	@Override
	public void render(float delta) {
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		back.draw(game.batch);
		
		game.font.setColor(0.0f, 0.0f, 204.0f, 1.0f);// set font color to red
		game.font.draw(game.batch, "Welcome to the Greenacres Farm Game! ", 210, 445);
		game.font.draw(game.batch, "Try growing your appletrees by keeping your neighbor dinosaur from", 30, 395);
		game.font.draw(game.batch, "  stealing your apples but be carefull not to hit your own appletrees.", 30, 370);
		game.font.draw(game.batch, "             Destroy three of your trees and it's game over..."         , 30, 345);
		game.batch.draw(buttonImage, button.x, button.y);
		game.batch.draw(button3Image, button3.x, button3.y);
			
		game.batch.end();
		
		if (Gdx.input.justTouched()) 
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			camera.unproject(touchPos);
			
			if (pointInRectangle(button, touchPos.x, touchPos.y))
			{
				dispose();
				game.setScreen(new FarmGame(game,context));
			}

			else if (pointInRectangle(button3, touchPos.x, touchPos.y))
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
		backGround.dispose();
		buttonImage.dispose();
		button3Image.dispose();
	}
}