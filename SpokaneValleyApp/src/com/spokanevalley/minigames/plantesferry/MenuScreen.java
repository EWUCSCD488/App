package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends GameScreen
{
// setup the dimensions of the menu buttons
private static final float BUTTON_WIDTH = 300f;
private static final float BUTTON_HEIGHT = 60f;
private static final float BUTTON_SPACING = 10f;

public MenuScreen()
{}

@Override
public void resize(
    int width,
    int height )
{
    super.resize( width, height);
    final float buttonX = ( width - BUTTON_WIDTH ) / 2;
    float currentY = 280f;
	this.atlas = new TextureAtlas(Gdx.files.internal("plantesferryAssets/gfx/buttons.pack"));
	this.skin = new Skin(this.atlas);
    Label welcomeLabel = new Label( "Welcome to Plantes Ferry", this.skin );
    welcomeLabel.setX(( width - welcomeLabel.getWidth()) / 2 );
    welcomeLabel.setY( currentY + 100 );
    stage.addActor( welcomeLabel );

    TextButton startGameButton = new TextButton( "Start game", this.skin);
    startGameButton.setX(buttonX);
    startGameButton.setY(currentY);
    startGameButton.setWidth(BUTTON_WIDTH);
    startGameButton.setHeight(BUTTON_HEIGHT);
    stage.addActor( startGameButton );

    TextButton optionsButton = new TextButton( "Options", this.skin );
    optionsButton.setX(buttonX);
    optionsButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
    optionsButton.setWidth(BUTTON_WIDTH);
    optionsButton.setHeight(BUTTON_HEIGHT);
    stage.addActor( optionsButton );

    TextButton exitButton = new TextButton( "Exit", this.skin );
    exitButton.setX(buttonX);
    exitButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
    exitButton.setWidth(BUTTON_WIDTH);
    exitButton.setHeight(BUTTON_HEIGHT);
    stage.addActor( exitButton );
}
}
