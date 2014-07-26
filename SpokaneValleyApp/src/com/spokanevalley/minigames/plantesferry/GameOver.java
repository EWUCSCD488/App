package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class GameOver implements ApplicationListener {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage; //** stage holds the Button **//
    private BitmapFont font; //** same as that used in Tut 7 **//
    private TextureAtlas buttonsAtlas; //** image of buttons **//
    private Skin buttonSkin; //** images are used as skins of the button **//
    private TextButton button; //** the button - the only actor in program **//
    GameScreen screen;
    
    public GameOver() {
    	camera = new OrthographicCamera();
    	buttonsAtlas = new TextureAtlas("buttons.pack"); //** button atlas image **// 
        font = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
					Gdx.files.internal("fonts/gamefont_0.png"), false); //** font **//
        buttonSkin = new Skin();
        //stage = new Stage(800, 480, true);        //** window is stage **//
    }
    @Override
    public void create() {        
        
        camera.setToOrtho(false, 800, 480); //** w/h ratio = 1.66 **//
        
        batch = new SpriteBatch();
        
       
        
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        //screen.getStage().clear();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//
        
        TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("simplebutton");
        style.down = buttonSkin.getDrawable("simplebutton2");
        style.font = font;
        
        button = new TextButton("PRESS ME", style); //** Button text and style **//
        button.setPosition(100, 100); //** Button location **//
        button.setHeight(64); //** Button Height **//
        button.setWidth(128); //** Button Width **//
        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                    return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("my app", "Released");
            }
        });
        
       // screen.getStage().addActor(button);
    }

    @Override
    public void dispose() {
        batch.dispose();
        buttonSkin.dispose();
        buttonsAtlas.dispose();
        font.dispose();
        //screen.getStage().dispose();
}

    @Override
    public void render() {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
       // screen.getStage().act();
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
