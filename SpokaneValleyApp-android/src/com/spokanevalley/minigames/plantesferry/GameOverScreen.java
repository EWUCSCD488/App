package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spokanevalley.database.DatabaseCustomAccess;

/*
 * @author Kevin Borling
 * Displays the Game Over Menu to the screen
 * Will ask the user to "Try Again" or "Return to Map"
 */
public class GameOverScreen implements Screen {
	GameSetup setup;
	PlantesFerry game;
	GameState state;

	private Stage stage;
	private MenuTable menuTable;
	private SpriteBatch paramSpriteBatch;
	/* Labels */
	private GameLabel gameoverLabel;
	private GameLabel scoreLabel;
	private GameLabel highscoreLabel;
	/* Buttons */
	private GameTextButton restartGameButton;
	private EndGameButton endGameButton;
	/* Table Values */
	private final float buttonPaddingBottom1 = 25.0F;
	private final float buttonPaddingBottom2 = 10.0F;

	public GameOverScreen(GameSetup setup, PlantesFerry game) {

		this.setup = setup;
		this.game = game;
		this.stage = new Stage();
		this.paramSpriteBatch = new SpriteBatch();
		/* Save High Score to database */
		int score = game.getScore();
		int maxScore = DatabaseCustomAccess.Create(setup.getContext())
				.saveMaxScore_PlantesFerryGame(score);

		this.menuTable = new MenuTable();

		/* Create Labels and Buttons */
		this.gameoverLabel = new GameLabel("Game Over");
		this.scoreLabel = new GameLabel("Score: " + score);
		this.highscoreLabel = new GameLabel("High Score: " + maxScore);
		this.restartGameButton = new GameTextButton("Try Again");
		this.endGameButton = new EndGameButton("Return to Map");

		/* Add buttons and score labels to table */
		this.menuTable.add(this.gameoverLabel).expandX().center()
				.padBottom(this.buttonPaddingBottom1).row();
		this.menuTable.add(this.scoreLabel).expandX().center()
				.padBottom(this.buttonPaddingBottom2).row();
		this.menuTable.add(this.highscoreLabel).expandX().center()
				.padBottom(this.buttonPaddingBottom1).row();
		this.menuTable.add(this.restartGameButton).expandX().center()
				.padBottom(this.buttonPaddingBottom2).row();
		this.menuTable.add(this.endGameButton).expandX().center().row();

		this.stage.addActor(this.menuTable);

	} // End Constructor

	/*
	 * Resizes screen to set Resolution: Currently 800.0F x 480.0F
	 */
	@Override
	public void resize(int width, int height) {
		this.stage.setViewport(this.game.screenWidth, this.game.screenHeight,
				true);
		this.stage.getCamera().translate(-this.stage.getGutterWidth(),
				-this.stage.getGutterHeight(), 0.0F);
	} // End resize

	/*
	 * Renders the Game Over Screen Displays the end game score, high score and
	 * asks user to try again or return to map
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(16384);
		this.stage.act(delta);
		/* Draw Background Image */
		this.paramSpriteBatch.begin();
		this.paramSpriteBatch.draw(Assets.menubg, 0.0F, 0.0f,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.paramSpriteBatch.end();
		this.stage.draw();
	} // End render

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.stage);
		Assets.backgroundMusic.stop();
		/* Add Listener to Restart Game Button */
		this.restartGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.removeAllActors();
				setup.setScreen(setup.gameScreen);
				Assets.buttonSound.stop();
				Assets.buttonSound.play();
				game.setScore(0);
				game.setLives(3);
				return true;
			} // End touchDown

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		});
	} // End show

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	} // End hide

	@Override
	public void dispose() {
		this.paramSpriteBatch.dispose();
		this.menuTable.reset();
		this.stage.dispose();
	} // End dispose

	/* Unimplemented Methods */
	@Override
	public void pause() {}
	@Override
	public void resume() {}
} // End MenuScreen
