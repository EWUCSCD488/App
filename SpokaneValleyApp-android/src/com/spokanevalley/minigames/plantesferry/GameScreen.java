package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;

/*
 * @author Kevin Borling
 * Displays the Plante's Ferry Game to the screen.
 */
public class GameScreen implements Screen, GestureDetector.GestureListener {

	GameSetup setup;
	PlantesFerry game;

	private Stage stage;
	private BitmapFont scoreFont;
	private SpriteBatch paramSpriteBatch;
	GameState state = GameState.PLAY;

	/*
	 * Set the game stage and initialized the score font.
	 */
	public GameScreen(GameSetup setup, PlantesFerry game) {
		this.setup = setup;
		this.game = game;
		this.stage = new Stage();
		this.stage.addActor(this.game);
		this.scoreFont = Assets.scoreFont;
		this.paramSpriteBatch = new SpriteBatch();
	} // End Constructor

	/*
	 * When a fling gesture is detected, try to move up or down if possible.
	 */
	public boolean fling(float paramFloat1, float paramFloat2, int paramInt) {
		if (paramFloat2 < -100.0F)
			this.game.playerDino.tryMoveUp();
		if (paramFloat2 > 100.0F)
			this.game.playerDino.tryMoveDown();
		return false;
	} // End fling

	/*
	 * Disable the Input Processor when the game is minimized.
	 */
	public void hide() {
		Gdx.input.setInputProcessor(null);
	} // End hide

	/*
	 * Renders and displays the background and moving sprites Displays the
	 * number of lives and score If life count becomes 0, the Game Over Screen
	 * is displayed
	 */
	public void render(float delta) {
		switch (this.state) {
		case PLAY:
			Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
			Gdx.gl.glClear(16384);
			this.stage.act(delta);
			this.stage.draw();
			displayStats();
			if (this.game.getLives() < 1) {
				/* Call Game Over Screen if Life = 0 */
				this.setup.gameoverScreen = new GameOverScreen(this.setup, this.game);
				this.setup.setScreen(setup.gameoverScreen);
			}
			break;
		case PAUSE:
			pause();
			break;
		default:
			break;
		} // End switch
	} // End render

	/*
	 * Displays the current score and number of lives. Also plays the background
	 * music.
	 */
	private void displayStats() {
		int lives = game.getLives();
		// Display Apple Icon and Score (Bottom Left)
		this.paramSpriteBatch.begin();
		this.paramSpriteBatch.draw(Assets.apple, 85, 15);
		this.scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		this.scoreFont.setScale(2.5f);
		this.scoreFont.draw(this.paramSpriteBatch, "" + game.getScore(), 125,
				60);
		// Display Lives (Top Right)
		if (game.getLives() > 0) {
			if (lives > 2) {
				this.paramSpriteBatch.draw(Assets.playerDino, 1625, 1000);
				this.paramSpriteBatch.draw(Assets.playerDino, 1700, 1000);
				this.paramSpriteBatch.draw(Assets.playerDino, 1775, 1000);
			}
			if (lives == 2) {
				this.paramSpriteBatch.draw(Assets.playerDino, 1625, 1000);
				this.paramSpriteBatch.draw(Assets.playerDino, 1700, 1000);
			}
			if (lives == 1)
				this.paramSpriteBatch.draw(Assets.playerDino, 1625, 1000);
		} // End Display Lives
		if (game.isInvinsible()) {
			long invinsibleTime = game.getInvinsibleTime();
			this.paramSpriteBatch.draw(Assets.bubble, 110, 1015);
			this.scoreFont
					.draw(this.paramSpriteBatch,
							""
									+ (int) ((11.0E+009F - (TimeUtils
											.nanoTime() - invinsibleTime)) / 1000000000),
							125, 1075);
		}
		this.paramSpriteBatch.end();

		// Set background music and loop it forever
		Assets.backgroundMusic.play();
		Assets.backgroundMusic.setVolume(1.0f);
		Assets.backgroundMusic.setLooping(true);
	} // End displayStats

	/*
	 * Sets the Viewport to the size of the game: 800 x 480 = current size.
	 */
	public void resize(int paramInt1, int paramInt2) {
		this.stage.setViewport(game.screenWidth, game.screenHeight, true);
		this.stage.getCamera().translate(-this.stage.getGutterWidth(),
				-this.stage.getGutterHeight(), 0.0F);
	} // End resize

	/*
	 * Pauses the background music. Displays the menu background image with
	 * text. User must tap once to resume game.
	 */
	public void pause() {
		Assets.backgroundMusic.pause();
		this.paramSpriteBatch.begin();
		this.paramSpriteBatch.draw(Assets.pauseMenubg, 0.0F, 0.0f,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		this.scoreFont.setScale(2.5f);
		this.scoreFont
				.draw(this.paramSpriteBatch, "Tap Once to Resume",
						Gdx.graphics.getWidth() / 2.8F,
						Gdx.graphics.getHeight() / 2.0F);
		this.paramSpriteBatch.end();
	} // End pause

	/*
	 * Sets the Input Processor to handle touch events. Sets the Game music to
	 * repeat.
	 */
	public void show() {
		Gdx.input.setInputProcessor(new GestureDetector(this));
	} // End show()

	public boolean tap(float paramFloat1, float paramFloat2, int tapNum,
			int paramInt2) {
		if (tapNum > 1)
			this.state = GameState.PAUSE;
		if (tapNum == 1)
			this.state = GameState.PLAY;
		return false;
	} // End tap

	/*
	 * Dispose of any sprite batch or font used in game.
	 */
	public void dispose() {
		this.stage.addAction(Actions.removeActor(this.game));
		this.paramSpriteBatch.dispose();
		this.stage.dispose();
	} // End dispose

	/* Unimplemented methods */
	public void resume() {}

	public boolean longPress(float paramFloat1, float paramFloat2) { return false; } // End longPress

	public boolean pan(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) { return false; } // End pan

	public boolean pinch(Vector2 paramVector21, Vector2 paramVector22, Vector2 paramVector23, Vector2 paramVector24) { 
		return false;
	} // End pinch

	public boolean touchDown(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) { return false; } // End touchDown

	public boolean zoom(float paramFloat1, float paramFloat2) { return false; } // End zoom

} // End GameScreen
