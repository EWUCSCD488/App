package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MenuScreen implements Screen
{

GameSetup game;

private Stage stage;
private TextureAtlas atlas;
private Skin skin;
private Table menuTable;
private BitmapFont scoreFont;
private SpriteBatch paramSpriteBatch;
private TextButtonStyle textButtonStyle;
private Label welcomeLabel;
private LabelStyle labelStyle;
private TextButton startGameButton;
private TextButton endGameButton;

public MenuScreen(GameSetup game)
{
	this.game = game;
	this.stage = new Stage();
	menuTable = new Table();
	this.paramSpriteBatch = new SpriteBatch();
	
	this.scoreFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
				Gdx.files.internal("fonts/gamefont_0.png"), false);

	this.atlas = new TextureAtlas(Gdx.files.internal("plantesferryAssets/gfx/buttons.pack"));
	this.skin = new Skin(this.atlas);
	
	labelStyle = new LabelStyle();
	labelStyle.font = this.scoreFont;
	welcomeLabel = new Label("Welcome to Plante's Ferry", labelStyle);
    
	textButtonStyle = new TextButtonStyle();
	textButtonStyle.up = skin.getDrawable("simplebutton");
	textButtonStyle.down = skin.getDrawable("simplebutton2");
	textButtonStyle.pressedOffsetX = 1;
	textButtonStyle.pressedOffsetY = -1;
	textButtonStyle.font = this.scoreFont;
	
	startGameButton = new TextButton("Start game", textButtonStyle);
	startGameButton.pad(20);
    startGameButton.setHeight(64);
    startGameButton.setWidth(128);
    
	endGameButton = new TextButton("Return to Map", textButtonStyle);
	endGameButton.pad(20);
	endGameButton.setHeight(64);
	endGameButton.setWidth(128);
    
    endGameButton.addListener(new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");
                Gdx.app.exit();
                return true;
        }
        
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Released");
                System.out.println("Released");
        }
    });
    menuTable.add(welcomeLabel).expandX().center().padBottom(25.0F).row();
    menuTable.add(startGameButton).expandX().center().padBottom(10.0F).row();
    menuTable.add(endGameButton).expandX().center().row();
    menuTable.setFillParent(true);
    this.stage.addActor(menuTable);
}

@Override
public void resize(int width,int height)
{
    this.stage.setViewport(800.0F, 480.0F, true);
    this.stage.getCamera().translate(-this.stage.getGutterWidth(), -this.stage.getGutterHeight(), 0.0F);
}

@Override
public void render(float delta) {
	Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(16384);
		this.stage.act(delta);
		this.paramSpriteBatch.begin();
		paramSpriteBatch.draw(Assets.menubg, 0.0F, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.paramSpriteBatch.end();
		this.stage.draw();
		
	    startGameButton.addListener(new InputListener() {
	        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                Gdx.app.log("my app", "Pressed");
	                game.setScreen(game.gameScreen);
	                return true;
	        }
	        
	        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	                Gdx.app.log("my app", "Released");
	        }
	    });
}

@Override
public void show() {
	Gdx.input.setInputProcessor(stage);
}

@Override
public void hide() {
	Gdx.input.setInputProcessor(null);
}

@Override
public void pause() {}

@Override
public void resume() {}

@Override
public void dispose() {
	  //this.scoreFont.dispose();
	  //this.paramSpriteBatch.dispose();
	  this.stage.dispose();
}
} // End MenuScreen
