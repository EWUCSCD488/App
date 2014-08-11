package com.spokanevalley.discoveryGame.drawingHandlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;

/**
 * Define background
 * 
 * @author Quyen Ha
 * 
 */
public class Background extends AbstractGameObject {

	private TextureRegion regMountainLeft;
	private TextureRegion regMountainRight;
	private int length;

	/**
	 * pass in how long background can be
	 * 
	 * @param leng
	 */

	public Background(int leng) {
		this.length = leng;
		init();
	}

	/**
	 * set up properties of background
	 */

	private void init() {
		dimension.set(20, 9);

		regMountainLeft = Assets.instance.background.backgroundLeft;
		regMountainRight = Assets.instance.background.backgroundRight;

		// shift moutain and extend length
		origin.x = -dimension.x * 2;
		length += dimension.x * 2;
	}

	/**
	 * rendering
	 * 
	 * @param batch
	 * @param offsetX
	 * @param offsetY
	 * @param tintColor
	 */
	
	private void drawMountain(SpriteBatch batch, float offsetX, float offsetY) {
		TextureRegion reg = null;
		float xRel = dimension.x * offsetX;
		float yRel = dimension.y * offsetY;
		// mountains span the whole level
		int mountainLength = 0;
		mountainLength += MathUtils.ceil(length / (2 * dimension.x));
		mountainLength += MathUtils.ceil(0.5f + offsetX);
		for (int i = 0; i < mountainLength; i++) {
			// mountain left
			reg = regMountainLeft;
			batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y
					+ yRel, origin.x, origin.y, dimension.x, dimension.y,
					scale.x, scale.y, rotation, reg.getRegionX(),
					reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
			xRel += dimension.x;
			// mountain right
			reg = regMountainRight;
			batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y
					+ yRel, origin.x, origin.y, dimension.x, dimension.y,
					scale.x, scale.y, rotation, reg.getRegionX(),
					reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
			xRel += dimension.x;
		}
		// reset color to white
		batch.setColor(1, 1, 1, 1);
	}

	@Override
	public void render(SpriteBatch batch) {
		drawMountain(batch, 0, -0.19f);			// (0,-19f) will set rocks at 40% of background from bottom
	}

}
