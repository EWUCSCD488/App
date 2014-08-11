package com.spokanevalley.discoveryGame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.spokanevalley.discoveryGame.drawingHandlers.AbstractGameObject;

/**
 * 
 * Create a set view for game
 * Change the offet will create more view in front of dinasour
 * Change zoom variable will make the view smaller or bigger
 * 
 * @author Quyen Ha
 *
 */

public class CameraHelper {
	
	private AbstractGameObject target;			// camera will focus on this target
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	private final float offset = 3;
	
	private Vector2 position;
	private float zoom;
	
	/**
	 * constructor
	 */
	
	public CameraHelper(){
		position = new Vector2();
		zoom = 1.7f;				// set up zoom
	}
	
	/**
	 * update camera positionS
	 * @param deltaTime
	 */
	
	public void update(float deltaTime){
		if(!hasTarget()) return;
		
		position.x = target.position.x + target.origin.x;
		//position.y = target.position.y + target.origin.y;
		
		// Prevent camera from moving down too far
		position.y = Math.max(-0.5f, position.y);
	}
	
	/**
	 * set camera position
	 * @param x
	 * @param y
	 */
	
	public void setPosition(float x, float y){
		this.position.set(x, y);
	}
	
	/**
	 * get camera position
	 * @return
	 */
	
	public Vector2 getPosition(){ return position; }
	
	/**
	 * add more zoom to camera
	 * @param amount
	 */
	
	public void addZoom(float amount){ setZoom(zoom + amount); }
	
	/**
	 * set zoom for camera in range of max zoom and min zoom
	 * @param zoom
	 */
	
	public void setZoom(float zoom){
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	/**
	 * get zoom of camera
	 * @return
	 */
	
	public float getZoom(){ return zoom; }
	
	/**
	 * set focus of camera
	 * @param target
	 */
	
	public void setTarget(AbstractGameObject target) { this.target = target; }
	
	/**
	 * get focus for camera
	 * @return
	 */
	
	public AbstractGameObject getTarget() { return this.target; }
	
	/**
	 * check if camera has target or not
	 * @return
	 */
	
	public boolean hasTarget(){ return target!= null; }
	
	/**
	 *  check if camera is focusing on this target or not
	 * @param target
	 * @return
	 */
	
	public boolean hasTarget(AbstractGameObject target){
		return hasTarget() && this.target.equals(target);
	}
	
	/**
	 * pass in a camera
	 * 
	 * @param camera
	 */
	
	public void applyTo(OrthographicCamera camera){
		camera.position.x = position.x + offset;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
	
}
