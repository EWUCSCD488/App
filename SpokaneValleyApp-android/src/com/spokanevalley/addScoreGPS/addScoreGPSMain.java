package com.spokanevalley.addScoreGPS;

import android.content.Context;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.spokanevalley.database.DatabaseCustomAccess;
import com.spokanevalley.ski.Constants;
import com.spokanevalley.ski.SkiGame;

public class addScoreGPSMain extends Game {
	public  SpriteBatch batch;
	public BitmapFont font;
	private Context context;
	private String placeTitle;
	private Texture present;
	Texture backGround,buttonImage, button2Image, button3Image, howTotex;
	Rectangle button, button2, button3, howTo;
	Sprite back;
	Boolean how= false;
	OrthographicCamera camera;
	
	public addScoreGPSMain(Context context){
		this.context = context;
	}
	
	public void create() {
		batch = new SpriteBatch();
		// Set font to custom font.
		font = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"), false);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		//Exit Button
		button3Image.setEnforcePotImages(false);
		button3Image = new Texture(Gdx.files.internal("addScoreGPSAssets/exitSmall.png"));
		button3 = new Rectangle();
		button3.x = 50; // Put 1/3 of the way on the x scale
		button3.y = 50;
		button3.width = 128;
		button3.height = 128;
		
		//Present Button
		buttonImage.setEnforcePotImages(false);
		buttonImage = new Texture(Gdx.files.internal("addScoreGPSAssets/present.png"));
		button = new Rectangle();
		button.x = Constants.WIDTH_GAME / 2 - 89 / 2; // centered horizontally
		button.y = Constants.HEIGHT_GAME / 2 - 89 / 2;;
		button.width = 128;
		button.height = 128;
		
		backGround.setEnforcePotImages(false);
		backGround = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(backGround);
		back.setSize(800, 480);
	}

	public void render() {
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		back.draw(batch);
		
		font.setColor(0.0f, 0.0f, 204.0f, 1.0f);// set font color to red
		font.draw(batch, "Thank you for visiting this location.", 215, 445);
		font.draw(batch, "Here is a one time present for visiting!", 183, 395);
		font.draw(batch, "  Please click on the present to collect your prize!", 130, 370);
		batch.draw(button3Image, button3.x, button3.y);
		batch.draw(buttonImage, button.x, button.y);
		
		//touch management
		if (Gdx.input.justTouched()) 
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if (pointInRectangle(button, touchPos.x, touchPos.y))
			{
				buttonImage = new Texture(Gdx.files.internal("addScoreGPSAssets/bucket.png"));
				batch.draw(buttonImage, button.x, button.y);
				//TODO: Database call to save the prize (500 apples)
			}
			else if (pointInRectangle(button3, touchPos.x, touchPos.y))
			{
				Gdx.app.exit();
			}
		}
		batch.end();
		
	}
	
	public static boolean pointInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
		backGround.dispose();
		buttonImage.dispose();
		button3Image.dispose();
	}
}