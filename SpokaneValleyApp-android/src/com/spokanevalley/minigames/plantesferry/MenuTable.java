package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/*
 * @author Kevin Borling
 * Custom Table which is set the fill the container of its parent(stage)
 */
public class MenuTable extends Table {
	public MenuTable() {
		this.setFillParent(true);
		this.padTop(65.0f);
		this.padRight(25.0f);
	} // End Constructor
} // End MenuTable class
