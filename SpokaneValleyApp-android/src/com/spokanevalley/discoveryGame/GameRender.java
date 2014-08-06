package com.spokanevalley.discoveryGame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;

public class GameRender implements Disposable {

	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;

	private SpriteBatch batch;
	private GameLogic worldController;

	public GameRender(GameLogic worldController) {
		this.worldController = worldController;
		init();
	}

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

	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}

	public void render() {
		renderWorld(batch);
		renderGUI(batch);

	}

	private void renderGuiGameOverMessages(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth / 2
				- Assets.instance.fonts.defaultBig.getScaleX();
		float y = cameraGUI.viewportHeight / 2
				- Assets.instance.fonts.defaultBig.getScaleY();

		if (worldController.isGameOver()) {
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.drawMultiLine(batch, "GAME OVER", x, y, 0,
					BitmapFont.HAlignment.CENTER);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}

	private void renderGuiScore(SpriteBatch batch) {
		float x = 70;
		float y = 40;
		batch.draw(Assets.instance.coin.coin, x, y, 50, 50, 100, 100, 0.35f,
				0.35f, 0);
		// DRAW FONTS HERE
		Assets.instance.fonts.defaultBig.draw(batch,
				"" + worldController.score, x + 75, y + 37);
	}

	private void renderGUIExtraLive(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - Constants.LIVES_START * 30;
		float y = 40;

		for (int i = 0; i < Constants.LIVES_START; i++) {
			if (worldController.getLives() <= i)
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.dinasour.cuteDinasour, x + i * 50, y,
					50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);

		}
	}

	private void renderGuiFeatherPowerup(SpriteBatch batch) {
		float x = 70;
		float y = 80;
		float timeLeftSpecialPowerup = worldController.level.dinasour.timeLeftSpecialCoinPowerUp;
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
			batch.draw(Assets.instance.specialCoin.Specialcoin, x, y, 50, 50, 100, 100,
					0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Assets.instance.fonts.defaultSmall.draw(batch, ""
					+ (int) timeLeftSpecialPowerup, x + 60, y + 57);
		}else{
			worldController.level.dinasour.terminalVelocity.set(4.0f, worldController.level.dinasour.terminalVelocity.y);
			//worldController.level.dinasour.velocity.x = worldController.level.dinasour.terminalVelocity.x* 10000;
		}
	}

	private void renderGUI(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();

		// draw collected gold coins icon + text
		// (anchored to top left edge)
		renderGuiScore(batch);
		// draw collected feather icon (anchored to top left edge)
		renderGuiFeatherPowerup(batch);
		// draw extra lives icon + text (anchored to top right edge)
		renderGUIExtraLive(batch);
		renderGuiGameOverMessages(batch);
		batch.end();
	}

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

	@Override
	public void dispose() {
		batch.dispose();
	}
}
