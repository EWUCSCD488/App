package com.spokanevalley.discoveryGame.drawingHandlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;

/**
 * Create cloud objects at first objects only contains cloud, but I added more
 * airplanes and helicopter objects
 * 
 * @author Quyen Ha
 * 
 */

public class Airplanes extends AbstractGameObject {

	private float length;
	private Array<TextureRegion> regClouds;
	private Array<Cloud> clouds;

	
	/**
	 * private class to define cloud properties and rendering
	 * 
	 * @author Quyen Ha
	 *
	 */
	private class Cloud extends AbstractGameObject {
		private TextureRegion regCloud;

		public Cloud() {
		}

		public void setRegion(TextureRegion region) {
			regCloud = region;
		}

		@Override
		public void render(SpriteBatch batch) {
			TextureRegion reg = regCloud;
			batch.draw(reg.getTexture(), position.x + origin.x, position.y
					+ origin.y, origin.x, origin.y, dimension.x, dimension.y,
					scale.x, scale.y, rotation, reg.getRegionX(),
					reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
		}
	}// end Cloud

	public Airplanes(float length) {
		this.length = length;			// how many airplanes in one random
		init();
	}

	/**
	 * initialize airplanes
	 */
	
	private void init() {
		dimension.set(1.5f, 0.75f);
		regClouds = new Array<TextureRegion>();
		regClouds.add(Assets.instance.airplane.airplane1);
		regClouds.add(Assets.instance.airplane.airplane2);
		regClouds.add(Assets.instance.airplane.airplane3);
		int distFac = 5;			// distance between 2 airplanes
		int numClouds = (int) (length / distFac);
		clouds = new Array<Cloud>(2 * numClouds);
		for (int i = 0; i < numClouds; i++) {
			Cloud cloud = spawnCloud();
			cloud.position.x = i * distFac;
			clouds.add(cloud);
		}
	}

	/**
	 * create cloud,airplane,helicopter in random order
	 * @return
	 */
	
	private Cloud spawnCloud() {
		Cloud cloud = new Cloud();
		cloud.dimension.set(dimension);
		// select random cloud image
		cloud.setRegion(regClouds.random());
		// position
		Vector2 pos = new Vector2();
		pos.x = length + 10; // position after end of level
		pos.y += 1.75; // base position
		// random additional position
		pos.y += MathUtils.random(0.0f, 0.2f)
				* (MathUtils.randomBoolean() ? 1 : -1);
		cloud.position.set(pos);
		return cloud;
	}

	/**
	 * rendering
	 */
	
	@Override
	public void render(SpriteBatch batch) {
		for (Cloud cloud : clouds)
			cloud.render(batch);
	}
}
