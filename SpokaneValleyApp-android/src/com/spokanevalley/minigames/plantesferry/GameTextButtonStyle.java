package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class GameTextButtonStyle extends TextButtonStyle {
	
	private final float buttonPressOffsetX = 1.0F;
	private final float buttonPressOffsetY = -1.0F;
	/* Set Button String to the attributes in buttons.pack */
	private final String upButton = "simplebutton";
	private final String downButton = "simplebutton2";
	private Skin skin;
	private BitmapFont scoreFont;

	public GameTextButtonStyle() {
		super();
		this.skin = new Skin(Assets.skinAtlas);
		this.up = this.skin.getDrawable(upButton);
		this.down = this.skin.getDrawable(downButton);
		this.pressedOffsetX = this.buttonPressOffsetX;
		this.pressedOffsetY = this.buttonPressOffsetY;
		this.scoreFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
							 Gdx.files.internal("fonts/gamefont_0.png"), false);
		this.font = this.scoreFont;
	} // End Constructor
} // End GameTextButtonStyle class
