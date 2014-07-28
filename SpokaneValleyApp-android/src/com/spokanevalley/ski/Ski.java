package com.spokanevalley.ski;
//package com.me.mygdxgame;
import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ski extends Game {
	public  SpriteBatch batch;
	public BitmapFont font;
//	private Context context;
	public Ski(Context context){
		this.context = context;
	}
	
	public void create() {
		batch = new SpriteBatch();
		// Set font to custom font.
		font = new BitmapFont(Gdx.files.internal(Constants.GAME_FONT), false);
		this.setScreen(new MainMenuScreen(this, context));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();

	}
}
