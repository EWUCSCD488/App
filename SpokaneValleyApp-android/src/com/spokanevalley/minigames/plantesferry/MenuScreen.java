package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MenuScreen implements Screen
{
GameSetup setup;
PlantesFerry game;

private Stage stage;
private MenuTable menuTable;
private SpriteBatch paramSpriteBatch;
private GameLabel welcomeLabel;
/* Buttons */
private GameTextButton startGameButton;
private GameTextButton endGameButton;
/* Table Values */
private final float buttonPaddingBottom1 = 25.0F;
private final float buttonPaddingBottom2 = 10.0F;

public MenuScreen(GameSetup setup, PlantesFerry game)
{
	this.setup = setup;
	this.game = game;
	this.stage = new Stage();
	this.menuTable = new MenuTable();
	this.paramSpriteBatch = new SpriteBatch();

	this.welcomeLabel = new GameLabel("Welcome to Plante's Ferry");
	this.startGameButton = new GameTextButton("Start Game");
	this.endGameButton = new EndGameButton("Return to Map");
	
    this.menuTable.add(this.welcomeLabel).expandX().center().padBottom(this.buttonPaddingBottom1).row();
    this.menuTable.add(this.startGameButton).expandX().center().padBottom(this.buttonPaddingBottom2).row();
    this.menuTable.add(this.endGameButton).expandX().center().row();
    
    this.stage.addActor(this.menuTable);
}

@Override
public void resize(int width,int height)
{
    this.stage.setViewport(game.screenWidth, game.screenHeight, true);
    this.stage.getCamera().translate(-this.stage.getGutterWidth(), -this.stage.getGutterHeight(), 0.0F);
} // End resize

@Override
public void render(float delta) {
	Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(16384);
		this.stage.act(delta);
		/* Draw Background Image */
		this.paramSpriteBatch.begin();
		paramSpriteBatch.draw(Assets.menubg, 0.0F, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.paramSpriteBatch.end();

		this.stage.draw();
		
	    this.startGameButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                setup.setScreen(setup.gameScreen);
	                return true;
	        } // End touchDown
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
	    }); // End addListener
} // End render

@Override
public void show() {
	Gdx.input.setInputProcessor(this.stage);
} // End show

@Override
public void hide() {
	Gdx.input.setInputProcessor(null);
} // End hide

@Override
public void dispose() {
	  this.paramSpriteBatch.dispose();
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
