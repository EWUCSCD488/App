/*
 * @author Kevin Borling
 * Class for loading the initial splash screen
 */
package com.spokanevalley.app.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spokanevalley.app.tween.SpriteAccessor;

public class Splash implements Screen{

	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	
	@Override
	public void render(float delta) {
		// Set background color behind image as a backup
		Gdx.gl.glClearColor(0, 0, 0, 1);
		// Implement background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Update splash animation
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		// Set Splash background image
		Texture splashTexture= new Texture("gfx/new-map-final.png");// Replace this temporary image with real splash.
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Splash starts from alpha transparency of 0 and fades in.
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		// Set the actual animation, including the object and the duration.
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
		// Fade out the splash screen with a 2 second delay.
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(0).delay(2).start(tweenManager);
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
