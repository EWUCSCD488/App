package com.spokanevalley.discoveryGame.drawingHandlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * define common properties of objects
 * 
 * @author Quyen HaS
 *
 */

public abstract class AbstractGameObject {
	
	// in-game object position variables
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	// character movements variables
	public Vector2 velocity;						// velocity of character m/s
	public Vector2 terminalVelocity;				// position and negative maximum speed m/s	
	public Vector2 friction;						//opposite force to slow down character
	public Vector2 acceleration;					// 
	public Rectangle bounds;
	
	/**
	 * constructor
	 * 
	 */
	
	public AbstractGameObject(){
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		
		velocity = new Vector2();
		terminalVelocity = new Vector2(1,1);
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();
	}
	
	/**
	 * update velocity of object based on friction and accelerationS in dimension x
	 * 
	 * @param deltaTime
	 */
	
	protected void updateMotionX(float deltaTime){
		if(velocity.x != 0){
			// apply friction
			if(velocity.x >0 ){
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			}else{
				velocity.x = Math.max(velocity.x + friction.x * deltaTime, 0);
			}
		}
		
		// aplly acceleration
		velocity.x += acceleration.x * deltaTime;
		
		// check max speed of character
		velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
	}
	
	/**
	 * update velocity of object based on friction and accelerationS in dimension y
	 * 
	 * @param deltaTime
	 */
	
	protected void updateMotionY(float deltaTime){
		if (velocity.y != 0) {
			
			// Apply friction
			if (velocity.y > 0) {
				velocity.y =Math.max(velocity.y - friction.y * deltaTime, 0);
			} else {
				velocity.y =Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
		
		// Apply acceleration
		velocity.y += acceleration.y * deltaTime;
			
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		velocity.y = MathUtils.clamp(velocity.y,-terminalVelocity.y, terminalVelocity.y);
	}
	
	/**
	 * update position x and y of object
	 * 
	 * @param deltaTime
	 */
	
	public void update(float deltaTime){
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		
		// move to new position
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
	}
	
	public abstract void render(SpriteBatch batch);
}
