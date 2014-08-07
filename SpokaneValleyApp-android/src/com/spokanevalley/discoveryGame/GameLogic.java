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
import com.spokanevalley.database.DatabaseInterface;
import com.spokanevalley.discoveryGame.Screen.GameMusicSoundPref;
import com.spokanevalley.discoveryGame.Screen.MenuSreen;
import com.spokanevalley.discoveryGame.drawingHandlers.Apples;
import com.spokanevalley.discoveryGame.drawingHandlers.Dinasour;
import com.spokanevalley.discoveryGame.drawingHandlers.Dinasour.JUMP_STATE;
import com.spokanevalley.discoveryGame.drawingHandlers.RocketRocks;
import com.spokanevalley.discoveryGame.drawingHandlers.BadApples;
import com.spokanevalley.discoveryGame.level.LevelLoader;

public class GameLogic implements GestureListener {
	private static final String TAG = GameLogic.class.getName();
	
	Context context = null;
	public LevelLoader level;
	public CameraHelper cameraHelper;
	public int lives;
	public int score;
	private float timeSinceCollision;
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	private float timeLeftGameOverDelay;

	private Game game;

	public GameLogic(Game game) {
		this.game = game;
		init();
	}

	public boolean isGameOver() {
		return lives <= 0;
	}

	public boolean isPlayerInWater() {
		return level.dinasour.position.y < -5;
	}

	private void callMenu() {
		// switch to menu screen
		game.setScreen(new MenuSreen(game));
	}

	public void init() {
		// initTestObjects();

		cameraHelper = new CameraHelper();
		Gdx.input.setInputProcessor(new GestureDetector(this));
		lives = Constants.LIVES_START;
		initLevel();
		DatabaseInterface.Create(context).saveInitialScoretoDatabase_DiscoveryGame(score);

	}

	private void onCollisionHeadWithRock(RocketRocks rock) {
		Dinasour dinasour = level.dinasour;
		float heightDifference = Math.abs(dinasour.position.y
				- (rock.position.y + rock.bounds.height));
		if (heightDifference > 0.25f) {
			boolean hitLeftEdge = dinasour.position.x > (rock.position.x + rock.bounds.width / 2.0f);
			if (hitLeftEdge) {
				dinasour.position.x = rock.position.x + rock.bounds.width;
			} else {
				dinasour.position.x = rock.position.x - dinasour.bounds.width;
			}
			return;
		}
		switch (dinasour.jumpState) {
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			dinasour.position.y = rock.position.y + dinasour.bounds.height
					+ dinasour.origin.y;
			dinasour.jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:{
				
				dinasour.position.y = rock.position.y + dinasour.bounds.height
					+ dinasour.origin.y;
				break;
			}
		}
	}

	private void onCollisionWithCoin(Apples coin) {
		coin.collected = true;
		score += coin.getScore();
		GameMusicSoundPref.create().playEatingApple();
		Gdx.app.log(TAG, "Gold coin collected");
	}

	private void onCollisionBunnyWithSpecialCoins(BadApples specialCoin) {
		specialCoin.collected = true;
		score += specialCoin.getScore();
		GameMusicSoundPref.create().playEatingBadApple();
		level.dinasour.setSpecialCoinPowerUp(true);
		Gdx.app.log(TAG, "Special collected");
	}
	
	private void testCollisions() {
		r1.set(level.dinasour.position.x, level.dinasour.position.y,
				level.dinasour.bounds.width, level.dinasour.bounds.height);
		// Test collision: Bunny Head <-> Rocks
		for (RocketRocks rock : level.rocketRocks) {
			r2.set(rock.position.x, rock.position.y, rock.bounds.width,
					rock.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionHeadWithRock(rock);
			// IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}
		// Test collision: Bunny Head <-> Gold Coins
		for (Apples goldcoin : level.apples) {
			if (goldcoin.collected)
				continue;
			r2.set(goldcoin.position.x, goldcoin.position.y,
					goldcoin.bounds.width, goldcoin.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionWithCoin(goldcoin);
			break;
		}
		// Test collision: Bunny Head <-> Feathers
		for (BadApples feather : level.badApples) {
			if (feather.collected)
				continue;
			r2.set(feather.position.x, feather.position.y,
					feather.bounds.width, feather.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionBunnyWithSpecialCoins(feather);
			break;
		}
	}

	public int getLives() {
		return lives;
	}

	private void initLevel() {
		level = null;
		level = new LevelLoader(Constants.LEVEL_01);
		score = 0;
		cameraHelper.setTarget(level.dinasour);
	}

	private Pixmap createProceduralPixmap(int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		// Draw a yellow-colored X shape on square
		pixmap.setColor(1, 1, 0, 1);

		pixmap.drawLine(0, 0, width, height);
		pixmap.drawLine(width, 0, 0, height);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, width, height);
		return pixmap;
	}

	public void update(float deltaTime) {

		// handleInputGame(deltaTime);

		if (isGameOver()) {
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0){
				callMenu();
				DatabaseInterface.Create(context).saveMaxScore_DiscoveryGame(score);
			}
				
		} else {
			handleInputGame(deltaTime);
		}

		if (timeLeftGameOverDelay >= 0) {
			level.update(deltaTime);
			testCollisions();
			cameraHelper.update(deltaTime);

			if (!isGameOver() && isPlayerInWater()) {
				lives--;
				if (isGameOver()){
					GameMusicSoundPref.create().playFalling();
					timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
				}else {
					initLevel();
					// level.dinasour.update(deltaTime);
					// level.dinasour.velocity.x = 0;
				}
			}
		}

	}

	private void handleInputGame(float deltaTime) {
		if (cameraHelper.hasTarget(level.dinasour)) {
			// Player Movement
			/*
			 * if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			 * level.dinasour.velocity.x = -level.dinasour.terminalVelocity.x; }
			 * else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			 * level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
			 * } else {
			 */
			// Execute auto-forward movement on non-desktop platform

			timeSinceCollision += deltaTime;
			if (timeSinceCollision > 2.0f) {
				if (Gdx.app.getType() != ApplicationType.Desktop) {
					level.dinasour.velocity.x = level.dinasour.terminalVelocity.x;
				}
			} else {
				// ignore the collision
			}

		}
		// Bunny Jump
		/*
		 * if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE))
		 * level.bunnyHead.setJumping(true); } else {
		 * level.bunnyHead.setJumping(false); } }
		 */
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub

		level.dinasour.setJumping(true);
		level.dinasour.setJumping(false);

		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// Log.d(TAG, "current  at : "+ testSprites[selectedSprite].getX() +
		// " , "+ testSprites[selectedSprite].getY());
		// moveSelectedSprite( 0 , 5* Gdx.graphics.getDeltaTime() );
		// Log.d(TAG, "tap at : "+ x + " , "+ y);

		/*
		 * selectedSprite = (selectedSprite+1)% testSprites.length;
		 * if(cameraHelper.hasTarget()){
		 * cameraHelper.setTarget(testSprites[selectedSprite]); }
		 * cameraHelper.setTarget(cameraHelper.hasTarget() ? null :
		 * testSprites[selectedSprite]);
		 */

		level.dinasour.setJumping(true);
		level.dinasour.setJumping(false);

		return false;
	}

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
