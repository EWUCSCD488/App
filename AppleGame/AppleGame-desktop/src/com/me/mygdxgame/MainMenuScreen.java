package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainMenuScreen implements Screen {

	final Apple game;
	Texture backTree;
	Sprite back;

	OrthographicCamera camera;

	public MainMenuScreen(final Apple gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);

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
		game.font.setScale((float) 2);
		game.font.draw(game.batch, "Welcome to Apple Catch! ", 90, 445);
		game.font.draw(game.batch, "Tap anywhere to begin", 102, 395);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new AppleGame(game));
			dispose();
		}
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