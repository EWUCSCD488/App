package com.spokanevalley.discoveryGame.drawingHandlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.spokanevalley.discoveryGame.Constants;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;
import com.spokanevalley.discoveryGame.Screen.GameMusicSoundPref;

/**
 * Difine Dinosaur in game
 * 
 * @author Quyen Ha
 *
 */

public class Dinasour extends AbstractGameObject {

	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
  
	public enum VIEW_DIRECTION {
		LEFT, RIGHT
	}

	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}

	private TextureRegion regDinasour;
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasbadAppleSpeedingUp;
	public float timeLeftBadAppleSpeedingUp;

	/**
	 * initializing
	 */
	
	public Dinasour() {
		init();
	}

	/**
	 * set properties for dinosaur
	 */
	
	public void init() {
		dimension.set(1, 1);
		regDinasour = Assets.instance.dinosaur.cuteDinosaur;

		// center images on game onject
		origin.set(dimension.x / 2, dimension.y / 2);

		// bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);

		// set physics values
		terminalVelocity.set(4.0f, 7.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);

		// view direction
		viewDirection = VIEW_DIRECTION.RIGHT;

		// jump state
		jumpState = JUMP_STATE.JUMP_FALLING;
		timeJumping = 0;

		// power ups
		hasbadAppleSpeedingUp = false;
		timeLeftBadAppleSpeedingUp = 0;
	}

	/**
	 * set jumping state for dinosaur
	 * 
	 * @param jumpKeyPressed
	 */
	
	public void setJumping(boolean jumpKeyPressed) {
		switch (jumpState) {
		case GROUNDED: // character standing on the rock or flatform
			if (jumpKeyPressed) {
				// start counting jump time from the beginning
				timeJumping = 0;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;

		case JUMP_RISING: // character fumping from the rock or flatform
			if (!jumpKeyPressed) {
				jumpState = JUMP_STATE.JUMP_FALLING;
				GameMusicSoundPref.create().playJumping();
			}
			break;

		case FALLING: // character falling on the rock or flatform
			break;

		case JUMP_FALLING: // character FALLING FOWNN the rock or flatform
			if (jumpKeyPressed && hasbadAppleSpeedingUp) {
				timeJumping = JUMP_TIME_OFFSET_FLYING;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;

		default:
			break;
		}
	}

	/**
	 * set speed up dinosaur
	 * 
	 * @param pickedUp
	 */
	
	public void setBadAppleSpeedUp(boolean pickedUp) {
		hasbadAppleSpeedingUp = pickedUp;
		if (pickedUp) {
			timeLeftBadAppleSpeedingUp = Constants.ITEM_BAD_APPLE_SPEEDING_UP_DURATION;
			//velocity.x = terminalVelocity.x*5;			// double speed for special coins
		}
		else{
			//velocity.x = terminalVelocity.x;
		}
		
	}

	/**
	 * check if dinasour is still in speeding up time or not
	 * @return
	 */
	
	public boolean hasbadAppleSpeedingUp() {
		return hasbadAppleSpeedingUp && timeLeftBadAppleSpeedingUp > 0;
	}

	/**
	 * rendering
	 */
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		// Set special color when game object has a feather power-up
		if (hasbadAppleSpeedingUp)
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		// Draw image
		reg = regDinasour;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT,
				false);
		// Reset color to white
		batch.setColor(1, 1, 1, 1);
	}

	/**
	 * update based on FPS of game
	 */
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		if (velocity.x != 0) {
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT
					: VIEW_DIRECTION.RIGHT;
		}

		if (timeLeftBadAppleSpeedingUp > 0) {
			timeLeftBadAppleSpeedingUp -= deltaTime;
			if (timeLeftBadAppleSpeedingUp < 0) {
				// disable power up
				timeLeftBadAppleSpeedingUp = 0;
				setBadAppleSpeedUp(false);
			}// if(timeLeftSpecialCoinPowerUp <0
		}// if(timeLeftSpecialCoinPowerUp >0 ){
	}// update

	/**
	 * update dinosaur state based on FPS
	 */
	
	@Override
	protected void updateMotionY(float deltaTime) {
		switch (jumpState) {
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			break;
		case JUMP_RISING:
			// Keep track of jump time
			timeJumping += deltaTime;
			// Jump time left?
			if (timeJumping <= JUMP_TIME_MAX) {
				// Still jumping
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			// Add delta times to track jump time
			timeJumping += deltaTime;
			// Jump to minimal height if jump key was pressed too short
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
				// Still jumping
				velocity.y = terminalVelocity.y;
			}
		}
		if (jumpState != JUMP_STATE.GROUNDED)
			super.updateMotionY(deltaTime);
	}

}
