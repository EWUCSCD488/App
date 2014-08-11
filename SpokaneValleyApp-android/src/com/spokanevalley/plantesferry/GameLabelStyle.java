package com.spokanevalley.plantesferry;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/*
 * @author Kevin Borling
 * Custom Label Style used in the Main and Game Over Menus
 */
public class GameLabelStyle extends LabelStyle {

	private BitmapFont labelFont;

	public GameLabelStyle() {
		super();
		this.labelFont = Assets.labelFont;
		this.labelFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		this.labelFont.setScale(1.25f);
		this.font = this.labelFont;
	} // End Constructor
} // End GameLabelStyle class
