package com.spokanevalley.discoveryGame.drawingHandlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;

/**
 * define rocks
 * 
 * @author Quyen Ha
 *
 */

public class RocketRocks extends AbstractGameObject {
	
	private TextureRegion regEdgeLeft;
	private TextureRegion regMiddle;
	private int length;
	
	public RocketRocks(){
		init();
	}
	
	/**
	 * set up properties
	 */
	
	private void init() {
		dimension.set(1, 1.5f);
		regEdgeLeft = Assets.instance.ground.edge;
		regMiddle = Assets.instance.ground.middle;
		// starting length at 1
		length = 1;
		bounds.set(0, 0, dimension.x * length, dimension.y);
	}
	
	/**
	 * set length of rocks
	 * @param length
	 */
	
	public void setLength(int length){
		this.length = length;
		// update boucing box for collision detection
		bounds.set(0, 0, dimension.x * length, dimension.y);
	}
	
	/**
	 * increase lenth of rocks
	 * @param amount
	 */
	
	public void increaseLenth(int amount){
		setLength(this.length + amount);
	}

	/**
	 * rendering
	 */
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		float relX = 0;
		float relY = 0;
		
		// draw left edge
		reg = regEdgeLeft;
		relX -=dimension.x /4;
		
		batch.draw(reg.getTexture(),
				position.x + relX,position.y + relY,
				origin.x,origin.y,
				dimension.x/4,dimension.y,
				scale.x,scale.y,
				rotation,
				reg.getRegionX(),reg.getRegionY(),
				reg.getRegionWidth(),reg.getRegionHeight(),
				false,false);
		
		// draw middle
		relX = 0 ;
		reg = regMiddle;
		for(int i = 0 ; i < length; i++){
			batch.draw(reg.getTexture(),
					position.x + relX,position.y+ relY,
					origin.x,origin.y,
					dimension.x,dimension.y,
					scale.x,scale.y,
					rotation,
					reg.getRegionX(),reg.getRegionY(),
					reg.getRegionWidth(),reg.getRegionHeight(),
					false,false);
			relX += dimension.x;
		}
		
		// Draw right edge
		 reg = regEdgeLeft;
		 
		 batch.draw(reg.getTexture(),
				 position.x + relX, position.y + relY,
				 origin.x + dimension.x / 8, origin.y,
				 dimension.x / 4, dimension.y,
				 scale.x, scale.y,
				 rotation,
				 reg.getRegionX(), reg.getRegionY(),
				 reg.getRegionWidth(), reg.getRegionHeight(),
				 true, false);		// rotate left edge to create right edge
		 
		
	}

}
