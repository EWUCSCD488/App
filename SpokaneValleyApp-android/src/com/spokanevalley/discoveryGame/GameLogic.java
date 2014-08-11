package com.spokanevalley.discoveryGame;

import android.content.Context;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.spokanevalley.database.DatabaseCustomAccess;
import com.spokanevalley.discoveryGame.Screen.GameMusicSoundPref;
import com.spokanevalley.discoveryGame.Screen.MenuSreen;
import com.spokanevalley.discoveryGame.drawingHandlers.Apples;
import com.spokanevalley.discoveryGame.drawingHandlers.BadApples;
import com.spokanevalley.discoveryGame.drawingHandlers.Dinasour;
import com.spokanevalley.discoveryGame.drawingHandlers.Dinasour.JUMP_STATE;
import com.spokanevalley.discoveryGame.drawingHandlers.RocketRocks;
import com.spokanevalley.discoveryGame.level.LevelLoader;

/**
 * Handle the game logic 
 * change speed for dinosaur in handling input part
 * 
 * @author Quyen Ha
 *
 */

public class GameLogic implements GestureListener {
	///private static final String TAG = GameLogic.class.getName();
	
	Context context = null;					// save context for backup
	public LevelLoader level;				// level will load the map and objects
	public CameraHelper cameraHelper;		// set focus on character and changing zoom
	public int lives;						// how many lines dinosaur has
	public int score;						// score for game
	private float timeSinceCollision;		// time after dinosaur contacts with rocks , bad apple or apple
	private Rectangle r1 = new Rectangle();	
	private Rectangle r2 = new Rectangle();
	private float timeLeftGameOverDelay;	// delay time to show game over
	private Task task;						// task to create more map after certain time
	private Game game;						// 

	/**
	 * Constructor passes in the game and initialize the game at first
	 * 
	 * @param game
	 */
	
	public GameLogic(Game game) {
		this.game = game;
		init();
	}

	/**
	 * check if game is over or not
	 * 
	 * @return true if over, false if not over
	 */
	
	public boolean isGameOver() {
		return lives <= 0;
	}

	/**
	 * check if player is falling off the rock or not
	 * 
	 * @return
	 */
	
	public boolean isPlayerFallingDownTheRock() {
		return level.dinasour.position.y < -5;			// check if position y of dinosaur is too long
	}
	
	/**
	 * Menu is callled when game over and time delay of game over is up
	 * 
	 */

	private void callMenu() {
		// switch to menu screen
		game.setScreen(new MenuSreen(game));
	}

	/**
	 * initialize the game : 
	 * set camera 
	 * set Gesture to handle input in game
	 * determine how many lives dinosaur can have
	 * initialize level in game
	 * register the game in database
	 * 
	 */
	
	public void init() {
		cameraHelper = new CameraHelper();
		Gdx.input.setInputProcessor(new GestureDetector(this));
		lives = Constants.LIVES_START;
		initLevel();
		DatabaseCustomAccess.Create(context).saveInitialScoretoDatabase_DiscoveryGame(score);
	}

	/**
	 * check what happens next after dinosaur contacts with rocks
	 * 
	 * @param rock
	 */
	
	private void onCollisionHeadWithRock(RocketRocks rock) {
		Dinasour dinasour = level.dinasour;			// get dinasour from level
		float heightDifference = Math.abs(dinasour.position.y			// calculate the height different 
				- (rock.position.y + rock.bounds.height));
		if (heightDifference > 0.25f) {						// 0.25 is a guess number. it has been tested many times to produce this number
			boolean hitLeftEdge = dinasour.position.x > (rock.position.x + rock.bounds.width / 2.0f);
			if (hitLeftEdge) {
				dinasour.position.x = rock.position.x + rock.bounds.width; // move character by dimension of left piece of rock
			} else {
				dinasour.position.x = rock.position.x - dinasour.bounds.width; // 
			}
			return;
		}
		
		switch (dinasour.jumpState) {			// determine what status of dinasour
		case GROUNDED:					// still in the rock, do nothing
			break;
		case FALLING:					// falling of the rock
		case JUMP_FALLING:				// falling after jumping on the rock
			dinasour.position.y = rock.position.y + dinasour.bounds.height
					+ dinasour.origin.y;				// update position of dinosaur based position of the rock it falls onto
			dinasour.jumpState = JUMP_STATE.GROUNDED;	// will be on the rock for next checking
			break;
		case JUMP_RISING:{								// jumping
				
				dinasour.position.y = rock.position.y + dinasour.bounds.height			// calculate height up
					+ dinasour.origin.y;
				break;
			}
		}
	}

	/**
	 * what happens when Dinosaur hits a apple
	 * 
	 * @param apple
	 */
	
	private void onCollisionWithApple(Apples apple) {
		apple.collected = true;				// coin is hit
		score += apple.getScore();			// add score to current score
		GameMusicSoundPref.create().playEatingApple();		// play sound
	}

	/**
	 * check what happens after dinosaur hits bad apple
	 * 
	 * @param badApple
	 */
	
	private void onCollisionDinosautWithBadApples(BadApples badApple) {
		badApple.collected = true;			// bad apples is hit
		score += badApple.getScore();		// add score of bad apple to current score
		GameMusicSoundPref.create().playEatingBadApple();			// play sound
		level.dinasour.setBadAppleSpeedUp(true);					// trigger 
		//Gdx.app.log(TAG, "Special collected");
	}
	
	/**
	 * check if dinosour hits rocks, apples or bad apples
	 * 
	 */
	
	private void testCollisions() {
		r1.set(level.dinasour.position.x, level.dinasour.position.y,
				level.dinasour.bounds.width, level.dinasour.bounds.height);
		// Test collision: dinosaur <-> Rocks
		for (RocketRocks rock : level.rocketRocks) {
			r2.set(rock.position.x, rock.position.y, rock.bounds.width,
					rock.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionHeadWithRock(rock);
			// IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}
		// Test collision: dinosaur <-> apple
		for (Apples goldcoin : level.apples) {
			if (goldcoin.collected)
				continue;
			r2.set(goldcoin.position.x, goldcoin.position.y,
					goldcoin.bounds.width, goldcoin.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithApple(goldcoin);
			break;
		}
		// Test collision: dinosaur <-> bad apple
		for (BadApples feather : level.badApples) {
			if (feather.collected)
				continue;
			r2.set(feather.position.x, feather.position.y,
					feather.bounds.width, feather.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionDinosautWithBadApples(feather);
			break;
		}
	}

	/**
	 * return how many lives dinosaur still has
	 * @return
	 */
	
	public int getLives() {
		return lives;
	}

	/**
	 * initialize level and object in game
	 */
	
	private void initLevel() {
		level = null;
		level = new LevelLoader(Constants.LEVEL_01);			// load level 1 with dinosaur
		
		 Timer.schedule(task = new Task(){
             @Override
             public void run() {
            	 level.loadNextMap();				// every 22s(time to almost finish a map), load the next to game
             }
         }
         ,22,22);
		
		score = 0;
		
		cameraHelper.setTarget(level.dinasour);			// focus camera to follow dinosaur
	}

	/**
	 * update game objects based on FPS
	 * check if game is over or not
	 * call menu and save/compare current score to max score in database
	 * 
	 * @param deltaTime
	 */
	
	public void update(float deltaTime) {

		// handleInputGame(deltaTime);

		if (isGameOver()) {
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0){
				callMenu();
				DatabaseCustomAccess.Create(context).saveMaxScore_DiscoveryGame(score);
			}
				
		} else {
			handleInputGame(deltaTime);
		}

		if (timeLeftGameOverDelay >= 0) {
			level.update(deltaTime);
			testCollisions();
			cameraHelper.update(deltaTime);

			if (!isGameOver() && isPlayerFallingDownTheRock()) {
				lives--;
				if (isGameOver()){
					GameMusicSoundPref.create().playFalling();
					task.cancel();
					timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
				}else {
					initLevel();
					// level.dinasour.update(deltaTime);
					// level.dinasour.velocity.x = 0;
				}
			}
		}

	}

	/**
	 * set up speed for dinosaur
	 * 
	 * @param deltaTime
	 */
	
	private void handleInputGame(float deltaTime) {
		if (cameraHelper.hasTarget(level.dinasour)) {

			timeSinceCollision += deltaTime;
			if (timeSinceCollision > 2.0f) {
				if (Gdx.app.getType() != ApplicationType.Desktop) {
					level.dinasour.velocity.x = level.dinasour.terminalVelocity.x;
				}
			} else {
				// ignore the collision
			}

		}
	}

	/**
	 * touch to jump
	 */
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub

		level.dinasour.setJumping(true);
		level.dinasour.setJumping(false);

		return false;
	}

	/**
	 * tap to jump
	 * set up this method for more responsiveness
	 */
	
	@Override
	public boolean tap(float x, float y, int count, int button) {

		level.dinasour.setJumping(true);
		level.dinasour.setJumping(false);

		return false;
	}

	/**
	 * long press to jump
	 * set up this method for more responsiveness
	 */
	
	@Override
	public boolean longPress(float x, float y) {

		level.dinasour.setJumping(true);
		level.dinasour.setJumping(false);

		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// moveSelectedSprite( 0 , -5* Gdx.graphics.getDeltaTime() );
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

}
