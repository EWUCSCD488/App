package com.spokanevalley.discoveryGame.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.spokanevalley.discoveryGame.GameLogic;
import com.spokanevalley.discoveryGame.GameRender;

/**
 * Screen to display game
 * 
 * @author Quyen Ha
 *
 */

public class GameScreen extends AbstractGameScreen {

	private GameRender worldRenderer;			// game render
	private GameLogic worldController;			// game logic
	private boolean paused;
	public GameScreen(Game game) {
		super(game);
	}

	/**
	 * rendering game
	 */
	
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

	/**
	 * game reszies after resuming
	 */
	
	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	/**
	 * initialize game logic and game render again , then show screen
	 */
	
	@Override
	public void show() {
		
		worldController = new GameLogic(game);
		worldRenderer = new GameRender(worldController);
		//Gdx.input.setCatchBackKey(true);

	}

	/**
	 * it's called when game is over, returning back to menu
	 */
	
	@Override
	public void hide() {

		//Gdx.input.setCatchBackKey(false);

	}

	/**
	 *  called when game is paused
	 */
	
	@Override
	public void pause() {
		paused = true;		// if true, stop rendering logic in game
	}

	/**
	 * called when game is resumed
	 */
	
	@Override
	public void resume() {
		super.resume();
		//worldController = new WorldController(game);
		worldRenderer = new GameRender(worldController);
		// Only called on Android!
		paused = false;
	}

	/**
	 * dispose method
	 */
	
	@Override
	public void dispose() {
		worldRenderer.dispose();
		
		// TODO Auto-generated method stub

	}

}
