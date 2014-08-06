package com.spokanevalley.discoveryGame.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.spokanevalley.discoveryGame.GameLogic;
import com.spokanevalley.discoveryGame.GameRender;

public class GameScreen extends AbstractGameScreen {

	private static final String TAG = GameScreen.class.getName();

	private GameRender worldRenderer;
	private GameLogic worldController;
	private boolean paused;
	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void render(float deltaTime) {
		if (!paused) {										// Do not update game world when paused.
			worldController.update(deltaTime);				// Update game world by the time that has passed
															// since last rendered frame.
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f,
				0xff / 255.0f);								// Sets the clear screen color to Blue

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);			// Clears the screen
		worldRenderer.render();								// Render game world to screen
		}

	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void show() {
		
		worldController = new GameLogic(game);
		worldRenderer = new GameRender(worldController);
		//Gdx.input.setCatchBackKey(true);

	}

	@Override
	public void hide() {
		//
		//Gdx.input.setCatchBackKey(false);

	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		super.resume();
		//worldController = new WorldController(game);
		worldRenderer = new GameRender(worldController);
		// Only called on Android!
		paused = false;
	}

	@Override
	public void dispose() {
		worldRenderer.dispose();
		// TODO Auto-generated method stub

	}

}
