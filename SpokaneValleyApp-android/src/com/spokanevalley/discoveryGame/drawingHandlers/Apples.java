package com.spokanevalley.discoveryGame.drawingHandlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;

/**
 * Define apple object
 * 
 * @author Quyen Ha
 * 
 */

public class Apples extends AbstractGameObject {

	private TextureRegion apples;
	public boolean collected;

	public Apples() {
		init();
	}

	/**
	 * set up dimension, image and bound for collision
	 */

	private void init() {
		dimension.set(0.5f, 0.5f);
		apples = Assets.instance.apple.apple;
		// set bound box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false; // not collected
	}

	/**
	 * rendering
	 */

	@Override
	public void render(SpriteBatch batch) {
		if (collected)
			return;

		TextureRegion reg = null;
		reg = apples;

		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	/**
	 * 
	 * @return how many score for eating 1 apple
	 */
	
	public int getScore() {
		return 1;
	}

}
