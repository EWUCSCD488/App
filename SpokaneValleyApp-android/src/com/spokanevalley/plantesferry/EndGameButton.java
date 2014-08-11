package com.spokanevalley.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/*
 * @author Kevin Borling
 * Custom End Game Button which exits Plante's Ferry game
 * This button is displayed on Main and Game Over Menus
 */
public class EndGameButton extends GameTextButton {

	public EndGameButton(String text) {
		super(text);

		this.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Assets.buttonSound.play();
				Gdx.app.exit();
				return true;
			} // End touchDown
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {}
		}); // End addListener
	} // End Constructor
} // End EndGameButton class
