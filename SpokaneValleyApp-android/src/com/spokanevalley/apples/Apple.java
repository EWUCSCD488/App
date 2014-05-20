package com.spokanevalley.apples;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Apple extends Game {
	public  SpriteBatch batch;
	public BitmapFont font;
	private Context context;
	public Apple(Context context){
		this.context = context;
	}
	
	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this,context));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();

	}
}
