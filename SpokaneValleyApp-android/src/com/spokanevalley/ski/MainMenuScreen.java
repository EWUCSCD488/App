package com.spokanevalley.ski;
//package com.me.mygdxgame;
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

	final Ski game;
	Texture backGround;
	Texture buttonImage;
	Rectangle button;
	Texture button2Image;
	Rectangle button2;
	Texture button3Image;
	Rectangle button3;
	Texture howTotex;
	Rectangle howTo;
	Sprite back;
	Boolean how= false;
	OrthographicCamera camera;
	
	private Context context;

	public MainMenuScreen(final Ski gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		buttonImage.setEnforcePotImages(false);
		buttonImage = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTON));
		button = new Rectangle();
		button.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button.y = 200;
		button.width = 100;
		button.height = 50;
		
		button2Image.setEnforcePotImages(false);
		button2Image = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTON2));
		button2 = new Rectangle();
		button2.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button2.y = 140;
		button2.width = 100;
		button2.height = 50;
		
		button3Image.setEnforcePotImages(false);
		button3Image = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTON3));
		button3 = new Rectangle();
		button3.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button3.y = 80;
		button3.width = 100;
		button3.height = 50;
		
		howTotex.setEnforcePotImages(false);
		howTotex = new Texture(Gdx.files.internal(Constants.HOWTO_SKI));
		howTo = new Rectangle();
		howTo.x = 0; // centered horizontally
		howTo.y = 0;
		howTo.width = 800;
		howTo.height = 480;
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
		if(!how)
		{
			//game.font.setScale((float) 2);
			game.font.setColor(0.0f, 0.0f, 204.0f, 1.0f);// set font color to red
			game.font.draw(game.batch, "Welcome to Ski Game! ", 280, 445);
			game.font.draw(game.batch, "Tap the button to play", 280, 395);
			game.batch.draw(buttonImage, button.x, button.y);
			game.batch.draw(button2Image, button2.x, button2.y);
			game.batch.draw(button3Image, button3.x, button3.y);
		}
		
		else
		{
			game.batch.draw(howTotex, howTo.x, howTo.y);
		}
		game.batch.end();

		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		if (Gdx.input.justTouched()) 
		{
			if(!how)
			{
				if (pointInRectangle(button, touchPos.x, touchPos.y))
				{

					game.setScreen(new SkiGame(game,context));
					dispose();
				}

				else if (pointInRectangle(button2, touchPos.x, touchPos.y))
				{

					how = true;
					
				}

				else if (pointInRectangle(button3, touchPos.x, touchPos.y))
				{
					Gdx.app.exit();
				}
			}
			else
			{
				
				how = false;
				
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
		howTotex.dispose();
		backGround.dispose();
		buttonImage.dispose();
		button2Image.dispose();
		button3Image.dispose();
        game.getScreen().dispose();

	}
}