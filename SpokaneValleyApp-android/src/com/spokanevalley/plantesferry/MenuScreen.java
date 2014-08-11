package com.spokanevalley.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/*
 * @author Kevin Borling
 * Displays the Main Menu to the screen
 * Displays welcome message, "Welcome to Plante's Ferry"
 * Will ask user to "Start Game" or "Return to Map"
 */
public class MenuScreen implements Screen {
	private GameSetup setup;
	private PlantesFerry game;

	private Stage stage;
	private MenuTable menuTable;
	private SpriteBatch paramSpriteBatch;
	/* Buttons */
	private GameTextButton startGameButton;
	private GameTextButton endGameButton;
	/* Table Values */
	private final float buttonPaddingBottom2 = 10.0F;

	/*
	 * Sets the stage, creates main menu table, along with the labels/buttons
	 */
	public MenuScreen(GameSetup setup, PlantesFerry game) {
		this.setup = setup;
		this.game = game;
		this.stage = new Stage();
		this.menuTable = new MenuTable();
		this.paramSpriteBatch = new SpriteBatch();

		//this.welcomeLabel = new GameLabel("Welcome to Plante's Ferry");
		this.startGameButton = new GameTextButton("Start Game");
		this.endGameButton = new EndGameButton("Return to Map");

		//this.menuTable.add(this.welcomeLabel).expandX().center().padBottom(this.buttonPaddingBottom1).row();
		this.menuTable.add(this.startGameButton).expandX().center()
				.padBottom(this.buttonPaddingBottom2).row();
		this.menuTable.add(this.endGameButton).expandX().center().row();

		this.stage.addActor(this.menuTable);
	} // End MenuScreen constructor

	/*
	 * Sets the Viewport to the size of the game: 800 x 480 = current size.
	 */
	@Override
	public void resize(int width, int height) {
		this.stage.setViewport(game.screenWidth, game.screenHeight, true);
		this.stage.getCamera().translate(-this.stage.getGutterWidth(),
				-this.stage.getGutterHeight(), 0.0F);
	} // End resize

	/*
	 * Draws the menu to the screen
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(16384);
		this.stage.act(delta);
		/* Draw Background Image */
		this.paramSpriteBatch.begin();
		paramSpriteBatch.draw(Assets.startMenubg, 0.0F, 0.0f,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.paramSpriteBatch.end();
		this.stage.draw();
	} // End render

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this.stage);
		
		Assets.backgroundMusic.play();
		Assets.backgroundMusic.setLooping(true);
		Assets.backgroundMusic.setVolume(0.25f);
		/* Start Game button Listener */
		this.startGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Assets.backgroundMusic.stop();
				setup.setScreen(setup.gameScreen);
				Assets.buttonSound.stop();
				Assets.buttonSound.play();
				return true;
			} // End touchDown

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
			}
		}); // End addListener
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

	/*
	 * Unimplemented Methods
	 */
	@Override
	public void pause() {}
	@Override
	public void resume() {}

} // End MenuScreen
