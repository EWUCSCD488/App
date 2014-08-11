package com.spokanevalley.discoveryGame.level;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.spokanevalley.discoveryGame.Constants;
import com.spokanevalley.discoveryGame.drawingHandlers.AbstractGameObject;
import com.spokanevalley.discoveryGame.drawingHandlers.Airplanes;
import com.spokanevalley.discoveryGame.drawingHandlers.Apples;
import com.spokanevalley.discoveryGame.drawingHandlers.Background;
import com.spokanevalley.discoveryGame.drawingHandlers.BadApples;
import com.spokanevalley.discoveryGame.drawingHandlers.Dinasour;
import com.spokanevalley.discoveryGame.drawingHandlers.RocketRocks;

/**
 * Loading all objects in game based on image
 * image is created by GIMP program. image will be counted pixel-by-pixel.
 * this class will go through each pixel and determine what color each pixel is
 * based on color set of objects, class will render objects
 * background will be black
 * rock is green pixel
 * dinosaur is white pixel , only draw this pixel once
 * apple is yello pixel
 * bad apple is  purple pixel
 * image is create in dimesion 128x32
 * 
 * @author Quyen Ha
 *
 */

public class LevelLoader {
	private float delay = 0;

	/**
	 * Define color for pixel and compare method
	 * 
	 * @author Quyen Ha
	 *
	 */
	
	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		ROCKETROCK(0, 255, 0), // green
		DINASOUR_SPAWMPOINT(255, 255, 255), // white
		ITEM_APPLE(255, 255, 0), // yellow
		ITEM_BAD_APPLE(255, 0, 255); // purple

		private int color;

		private BLOCK_TYPE(int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor(int color) {
			return this.color == color;
		}

		public int getColor() {
			return color;
		}

	}// enum

	// objects
	public Array<RocketRocks> rocketRocks;
	public Background background;
	public Airplanes airplanes;
	public Dinasour dinasour;
	public Array<Apples> apples;
	public Array<BadApples> badApples;
	private int numMap = 1;
	private int nextMapoffset = 10;

	/**
	 * constructor , load image into bitmap and load the first map, then load second map
	 * @param filename
	 */
	
	public LevelLoader(String filename) {
		initilizeFirstMap(filename);
		loadNextMap();
		dinasour.setJumping(false);
	}

	/**
	 * create a next random map after certain time.
	 */
	
	public void loadNextMap() {
		Random rand = new Random();

		int n = rand.nextInt(Constants.NUM_MAP) + 1;
		if (n == 1)
			inilizeNextMap(Constants.LEVEL_02);
		else if (n == 2)
			inilizeNextMap(Constants.LEVEL_03);
		else if (n == 3)
			inilizeNextMap(Constants.LEVEL_04);
		else if (n == 4) {
			inilizeNextMap(Constants.LEVEL_05);
		}

		numMap++;
	}

	/**
	 * read file and extend the map in game
	 * 
	 * @param filename
	 */
	
	private void inilizeNextMap(String filename) {

		// load image file that represenets the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		// scan pixels from top left to bottom right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				// height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				// get color of current pixel as 32 bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				// find matching color value to identify block type at (x,y)
				// point and create the correcsponding game object if there is a
				// match

				// emptyspace
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
					// do nothing
				} else if (BLOCK_TYPE.ROCKETROCK.sameColor(currentPixel)) {
					if (lastPixel != currentPixel) {
						obj = new RocketRocks();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = -2.5f;
						obj.position.set(pixelX
								+ (pixmap.getWidth() - nextMapoffset) * numMap,
								baseHeight * obj.dimension.y
										* heightIncreaseFactor + offsetHeight);
						rocketRocks.add((RocketRocks) obj);
					} else {
						rocketRocks.get(rocketRocks.size - 1).increaseLenth(1);
					}
				} else if (BLOCK_TYPE.ITEM_APPLE.sameColor(currentPixel)) { // gold
																			// coin
					obj = new Apples();
					offsetHeight = -0.75f;
					obj.position.set(pixelX
							+ (pixmap.getWidth() - nextMapoffset) * numMap,
							baseHeight * obj.dimension.y + offsetHeight);
					apples.add((Apples) obj);
					// } else if
					// (BLOCK_TYPE.PLAYER_SPAWMPOINT.sameColor(currentPixel)){
					// // dinasour
				} else if (BLOCK_TYPE.ITEM_BAD_APPLE.sameColor(currentPixel)) { // special
																				// coin
					obj = new BadApples();
					offsetHeight = -0.75f;
					obj.position.set(pixelX
							+ (pixmap.getWidth() - nextMapoffset) * numMap,
							baseHeight * obj.dimension.y + offsetHeight);
					badApples.add((BadApples) obj);
				} else {
					
				}

				lastPixel = currentPixel;
			} // second for loop

		}// first for loop

		// decoration
		airplanes = new Airplanes(pixmap.getWidth() * numMap);
		airplanes.position.set(0, 2);
		background = new Background(pixmap.getWidth() * numMap);
		background.position.set(-3, -3);

		// free memory
		pixmap.dispose();

	}

	/**
	 * read image with dinosaur
	 * @param filename
	 */
	
	public void initilizeFirstMap(String filename) {

		dinasour = null;
		apples = new Array<Apples>();
		badApples = new Array<BadApples>();

		rocketRocks = new Array<RocketRocks>();
		// load image file that represenets the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		// scan pixels from top left to bottom right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				// height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				// get color of current pixel as 32 bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				// find matching color value to identify block type at (x,y)
				// point and create the correcsponding game object if there is a
				// match

				// emptyspace
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
					// do nothing
				} else if (BLOCK_TYPE.ROCKETROCK.sameColor(currentPixel)) {
					if (lastPixel != currentPixel) {
						obj = new RocketRocks();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = -2.5f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y
								* heightIncreaseFactor + offsetHeight);
						rocketRocks.add((RocketRocks) obj);
					} else {
						rocketRocks.get(rocketRocks.size - 1).increaseLenth(1);
					}
				} else if (BLOCK_TYPE.ITEM_APPLE.sameColor(currentPixel)) { // gold
																			// coin
					obj = new Apples();
					offsetHeight = -1.5f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y
							+ offsetHeight);
					apples.add((Apples) obj);
				} else if (BLOCK_TYPE.DINASOUR_SPAWMPOINT
						.sameColor(currentPixel)) { // dinasour
					obj = new Dinasour();
					offsetHeight = -3.0f;
					float offsetWidth = 2.0f;
					obj.position.set(pixelX + offsetWidth, baseHeight
							* obj.dimension.y + offsetHeight);
					dinasour = (Dinasour) obj;
					dinasour.setJumping(false);

				} else if (BLOCK_TYPE.ITEM_BAD_APPLE.sameColor(currentPixel)) { // special
																				// coin
					obj = new BadApples();
					offsetHeight = -1.5f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y
							+ offsetHeight);
					badApples.add((BadApples) obj);
				} else {

				}

				lastPixel = currentPixel;
			} // second for loop

		}// first for loop

		// decoration
		airplanes = new Airplanes(pixmap.getWidth() * numMap);
		airplanes.position.set(0, 2);
		background = new Background(pixmap.getWidth() * numMap);
		background.position.set(-3, -3);

		// free memory
		pixmap.dispose();

	}

	/**
	 * rendering objects
	 * 
	 * @param batch
	 */
	
	public void render(SpriteBatch batch) {
		// Draw Mountains
		background.render(batch);
		// Draw Clouds
		airplanes.render(batch);

		// Draw gold coins
		for (Apples coin : apples)
			coin.render(batch);

		// Draw special Coins
		for (BadApples coin : badApples)
			coin.render(batch);

		// Draw Rocks
		for (RocketRocks rock : rocketRocks)
			rock.render(batch);

		// Draw Player character
		dinasour.render(batch);
	}

	/**
	 * update objects based on FPS
	 * 
	 * @param deltaTime
	 */
	
	public void update(float deltaTime) {
		airplanes.update(deltaTime);
		for (RocketRocks rock : rocketRocks)
			rock.update(deltaTime);
		for (Apples apple : apples)
			apple.update(deltaTime);
		for (BadApples badApple : badApples)
			badApple.update(deltaTime);

		delay += deltaTime;
		if (delay > 1.0f)
			dinasour.update(deltaTime);
	}

}
