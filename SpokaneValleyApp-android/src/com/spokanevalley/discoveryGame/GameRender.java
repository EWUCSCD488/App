package com.spokanevalley.discoveryGame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;
import com.spokanevalley.discoveryGame.Screen.GameMusicSoundPref;

/**
 * Main  class to render everything in game
 * set up camera
 * render score on top left corner
 * render live on top right corner
 * 
 * @author Quyen Ha
 *
 */

public class GameRender implements Disposable {

	private OrthographicCamera camera;			// camera for setting map
	private OrthographicCamera cameraGUI;		// camera for camera view

	private SpriteBatch batch;					
	private GameLogic worldController;			// logic controlling board in game

	public GameRender(GameLogic worldController) {
		this.worldController = worldController;			// pass in logic controller
		init();
	}

	/**
	 * set up camera for map and gui camera
	 */
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
				Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		camera.zoom -= 4;

		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // filp y axis
		cameraGUI.update();
		//cameraGUI.zoom -= 0.5;
	}

	/**
	 * render the map with camera
	 * 
	 * @param batch
	 */
	
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}

	/**
	 * render the map with camera first
	 * then render GUI for camera
	 */
	
	public void render() {
		renderWorld(batch);
		renderGUI(batch);

	}

	/**
	 * Draw game over messages on screen after game is over
	 * 
	 * @param batch
	 */
	
	private void renderGuiGameOverMessages(SpriteBatch batch) {
		float x = Constants.VIEWPORT_GUI_WIDTH / 2
				+ 100f;
		float y = Constants.VIEWPORT_GUI_HEIGHT / 2;

		if (worldController.isGameOver()) {
	
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.drawMultiLine(batch, "GAME OVER", x, y, 0,
					BitmapFont.HAlignment.CENTER);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}

	/**
	 * Draw score on gui camera at top left corner
	 * 
	 * @param batch
	 */
	
	private void renderGuiScore(SpriteBatch batch) {
		float x = 70;
		float y = 40;
		batch.draw(Assets.instance.apple.apple, x, y, 50, 50, 100, 100, 0.35f,
				0.35f, 0);
		// DRAW FONTS HERE
		Assets.instance.fonts.defaultBig.draw(batch,
				"" + worldController.score, x + 75, y + 37);
	}

	/**
	 * draw lives on screen at top right corner
	 * @param batch
	 */
	
	private void renderGUIExtraLive(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - Constants.LIVES_START * 30;
		float y = 40;

		for (int i = 0; i < Constants.LIVES_START; i++) {
			if (worldController.getLives() <= i)
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.dinosaur.cuteDinosaur, x + i * 50, y,
					50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);

		}
	}

	/**
	 * draw icon to indicate speed up after eating bad apple is in process
	 * 
	 * @param batch
	 */
	
	private void renderGuiBadAppleSpeedingup(SpriteBatch batch) {
		float x = 70;
		float y = 80;
		float timeLeftSpecialPowerup = worldController.level.dinasour.timeLeftBadAppleSpeedingUp;
		if (timeLeftSpecialPowerup > 0) {
			// Start icon fade in/out if the left power-up time
			// is less than 4 seconds. The fade interval is set
			// to 5 changes per second.
			
			worldController.level.dinasour.terminalVelocity.set(6.0f, worldController.level.dinasour.terminalVelocity.y);
			
			if (timeLeftSpecialPowerup < 4) {
				if (((int) (timeLeftSpecialPowerup * 5) % 2) != 0) {
					batch.setColor(1, 1, 1, 0.5f);
				}
				
				//worldController.level.dinasour.velocity.x = worldController.level.dinasour.velocity.x* 100000;  // speed it up
				
			}
			batch.draw(Assets.instance.badApple.badApple, x, y, 50, 50, 100, 100,
					0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Assets.instance.fonts.defaultSmall.draw(batch, ""
					+ (int) timeLeftSpecialPowerup, x + 60, y + 57);
		}else{
			worldController.level.dinasour.terminalVelocity.set(4.0f, worldController.level.dinasour.terminalVelocity.y);
			//worldController.level.dinasour.velocity.x = worldController.level.dinasour.terminalVelocity.x* 10000;
		}
	}

	/**
	 * render GUI with GUi camera
	 * @param batch
	 */
	
	private void renderGUI(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();

		// draw collected gold coins icon + text
		// (anchored to top left edge)
		renderGuiScore(batch);
		// draw collected feather icon (anchored to top left edge)
		renderGuiBadAppleSpeedingup(batch);
		// draw extra lives icon + text (anchored to top right edge)
		renderGUIExtraLive(batch);
		renderGuiGameOverMessages(batch);
		batch.end();
	}

	/**
	 * calculate map after resuming
	 * 
	 * @param width
	 * @param height
	 */
	
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();

		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = Constants.VIEWPORT_GUI_HEIGHT
				/ (float) height * (float) width;
		cameraGUI.position.sub(cameraGUI.viewportWidth / 2,
				cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}

	/**
	 * dispose method for memory handling
	 */
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
