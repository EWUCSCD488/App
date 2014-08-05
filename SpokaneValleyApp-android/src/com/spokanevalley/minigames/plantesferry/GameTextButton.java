package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/*
 * @author Kevin Borling
 * Custom Text Button which is styles by Game Text Button Style
 * Current dimensions for button are 128 x 64, with a padding of 20
 */
public class GameTextButton extends TextButton {

	static GameTextButtonStyle style = new GameTextButtonStyle();

	private final float buttonHeight = 64.0F;
	private final float buttonLength = 128.0F;
	private final float buttonPadding = 20.0F;

	public GameTextButton(String text) {
		super(text, style);
		this.pad(this.buttonPadding);
		this.setHeight(this.buttonHeight);
		this.setWidth(this.buttonLength);
	} // End Constructor

} // End GameButton class

