package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
/*
 * @author Kevin Borling
 * Custom Label which is pre-styled by the Game Label Style
 */
public class GameLabel extends Label {

	static GameLabelStyle style = new GameLabelStyle();
	
	public GameLabel(CharSequence text) {
		super(text, style);
	} // End Constructor
} // End GameLabel class
