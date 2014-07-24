package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseButton extends Game {

    Stage stage;
    TextButton button;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    GameScreen screen;
    
    @Override
    public void create() {      
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        //buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");
        button = new TextButton("Button1", textButtonStyle);
        stage.addActor(button);
    }

    @Override
    public void render() {      
        super.render();
        stage.draw();
        
    button.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			System.out.println("The button has been pressed!");
			screen.pause();
		}
	});

    } // End render
    
} // End Button
