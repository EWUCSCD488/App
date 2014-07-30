package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameLabel extends Label {

	static GameLabelStyle style = new GameLabelStyle();
	
	public GameLabel(CharSequence text) {
		super(text, style);
	} // End Constructor
} // End GameLabel class
