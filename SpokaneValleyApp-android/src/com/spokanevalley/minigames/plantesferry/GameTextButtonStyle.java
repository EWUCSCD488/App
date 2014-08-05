package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/*
 * @author Kevin Borling
 * Custom Game TextButton Style which sets the button image, located in plantesferryAssets/gfx/buttons.pack
 * Sets the font for the button
 */
public class GameTextButtonStyle extends TextButtonStyle {

	private final float buttonPressOffsetX = 1.0F;
	private final float buttonPressOffsetY = -1.0F;
	/* Set Button String to the attributes in buttons.pack */
	private final String upButton = "simplebutton";
	private final String downButton = "simplebutton2";
	private Skin skin;
	private BitmapFont buttonFont;

	public GameTextButtonStyle() {
		super();
		this.skin = new Skin(Assets.skinAtlas);
		this.up = this.skin.getDrawable(upButton);
		this.down = this.skin.getDrawable(downButton);
		this.pressedOffsetX = this.buttonPressOffsetX;
		this.pressedOffsetY = this.buttonPressOffsetY;
		this.buttonFont = Assets.buttonFont;
		this.buttonFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		this.font = this.buttonFont;
	} // End Constructor
} // End GameTextButtonStyle class
