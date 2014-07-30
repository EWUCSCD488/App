package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class GameLabelStyle extends LabelStyle {
	
	private BitmapFont scoreFont;
	
	public GameLabelStyle() {
		super();
		this.scoreFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
							 Gdx.files.internal("fonts/gamefont_0.png"), false);
		this.font = this.scoreFont;
	} // End Constructor
} // End GameLabelStyle class
