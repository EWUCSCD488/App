package com.spokanevalley.farm;

import android.content.Context;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Farm extends Game {
	public  SpriteBatch batch;
	public BitmapFont font;
	private Context context;
	
	public Farm(Context context){
		this.context = context;
	}
	
	public void create() {
		batch = new SpriteBatch();
		// Set font to custom font.
		font = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"), false);
		this.setScreen(new MainMenuScreen(this,context));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();

	}
}