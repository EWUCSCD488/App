/*
 * This is the main class for the Appple Catch mini game. This controls everything in the main game screen such as spawning the apples
 * as well as keeping track of the score and uploading it to the database.
 */
package com.spokanevalley.apples;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.spokanevalley.database.DatabaseCustomAccess;

public class AppleGame implements Screen {

	final Apple Game;
	Texture appleImage;
	Texture goldAppleImage;
	Texture rotAppleImage;
	Texture bucketImage;
	Texture backTree;
	Sprite back;
	Sound appleSound;
	OrthographicCamera camera;
	SpriteBatch batch;
	Rectangle bucket;
	Array<Appleob> Apples;
	long lastAppleTime;
	long speed;
	int lastpoint = 55;
	int spawn = 600000000;
	
	
	private int score;
	private Context context;
	
	
	
	private String applesCaught;
	BitmapFont scoreFont;
	private int badApples = 0;

	public AppleGame(Apple gam,Context context) {

		this.Game = gam;
		this.context = context;
		
		
		// load the images for apple and bucket, 48x48 pixels each
		setImages();
		setSound();

		// create camera and SpriteBatch class to draw objects to screen
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);

		batch = new SpriteBatch();

		// draw bucket
		bucket = new Rectangle();
		bucket.x = Constants.WIDTH_GAME / 2 - 64 / 2; // centered horizontally
		bucket.y = 20; // 20 pixels from the bottom , be default, y is counting
						// upward
		bucket.width = 64;
		bucket.height = 64;

		// initialize Apple array
		Apples = new Array<Appleob>();
		spawnApple();

		// initialize score
		score = 0;
		applesCaught = "SCORE: 0";
		scoreFont = new BitmapFont(Gdx.files.internal(Constants.GAME_FONT), false);
		
		// save max score to database
		DatabaseCustomAccess.Create(context).saveInitialScoretoDatabase_AppleGame(score);
		
		
	}

	@Override
	public void resize(int width, int height) {

	}
	
	//This is the main method for the game in that almost all of the objects are rendered here and the score is updated as the player catches apples
	//This method constantly runs its while loop until the player has caught 3 bad apples which at that point the sprites are disposed and the game over screen is called

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		if (Gdx.input.isTouched()) 
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		
		
		setSpawn();
		
		// constantly update drops
		if (TimeUtils.nanoTime() - lastAppleTime > spawn)
			spawnApple();

		// render Apple
		batch.begin(); // start new batch , it will collect all drawings from
						// begin to end and submit at once
		back.draw(batch);
		batch.draw(bucketImage, bucket.x, bucket.y); // draw bucket to batch
		
		
		checkApple();

		// score color and size
		scoreFont.setColor(0.0f, 0.0f, 204.0f, 0.0f);
		//scoreFont.setScale((float) 2);
		scoreFont.draw(batch, applesCaught, 190, 750);
		batch.end(); // end batch, write all drawings to screen

	}

	//This method handles all of the objects currently on the screen and checks if the the apple is caught or is off the screen
	private void checkApple()
	{
		Iterator<Appleob> iter = Apples.iterator();
		while (iter.hasNext()) 
		{
			Appleob Apple = iter.next();

			setSpeed();

			Apple.rec.y -= speed * Gdx.graphics.getDeltaTime();
			if (Apple.rec.y + 48 < 0)
				iter.remove();

			batch.draw(Apple.image, Apple.rec.x, Apple.rec.y);
			if (Apple.rec.overlaps(bucket)) 
			{
				score = score + Apple.points;
				if(score < 0)// Ensure score can't go negative
					score = 0;
				applesCaught = "SCORE: " + score;
				appleSound.play();
				if (Apple.isBad) {
					badApples++;
				}
				//save the scores to the database as the game ends and update the highscore if needed
				if (badApples > 2) {
					DatabaseCustomAccess.Create(context).saveMaxScore_AppleGame(score);

					Game.setScreen(new GameOver(Game,score,DatabaseCustomAccess.Create(context).saveMaxScore_AppleGame(0))); //maxScore*****
					dispose();
				}
				Apple.dispose();
				iter.remove();
			}
		}
	}

	//This method computes which apple should be spawned and assigns it to the arraylist of apple objects
	private void spawnApple() {
		Appleob ob = new Appleob();
		Rectangle Apple = new Rectangle();
		Apple.x = MathUtils.random(0, 480 - 48);
		Apple.y = 720;
		Apple.width = 48;
		Apple.height = 48;
		ob.rec = Apple;
		int rand = MathUtils.random(0, 300);
		//regular apple
		if (rand < 225) {
			ob.image = appleImage;
		}
		//gold apple
		else if (rand >= 225 && rand < 250) {
			ob.image = goldAppleImage;
			ob.points = 3;
		}
		//bad apple
		else {
			ob.image = rotAppleImage;
			ob.points = -5;
			ob.isBad = true;

		}
		Apples.add(ob);
		lastAppleTime = TimeUtils.nanoTime();
	}

	//initilize all of the textures used by the game as well as the background
	private void setImages()
	{
		appleImage = new Texture(Gdx.files.internal(Constants.IMAGE_APPLE)); // internal
		// files																		// folder
		bucketImage = new Texture(Gdx.files.internal(Constants.IMAGE_BUCKET));
		goldAppleImage = new Texture(Gdx.files.internal(Constants.IMAGE_GAPPLE));
		rotAppleImage = new Texture(Gdx.files.internal(Constants.IMAGE_RAPPLE));
		backTree.setEnforcePotImages(false);
		backTree = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(backTree);
		back.setSize(480, 800);
	}
	
	private void setSound()
	{
		// load the apple sound effect
		appleSound = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_APPLE));
		
	}
	
	//This method sets the apple's spawn rate based on the current score of the game
	private void setSpawn()
	{
		if(score < 25)
		{
			spawn = 600000000;
		}
		
		
		else if(score >= 25 && score < 45)
		{
			spawn = 500000000;
		}
		
		else if(score >= 45 && score < 65)
		{
			spawn = 400000000;
		}
		
		else if(score >= 65 && score < 85)
		{
			spawn = 300000000;
		}
		else if(score >= 85 && score < 105)
		{
			spawn = 200000000;
		}
		
		else
		{
			if(score > lastpoint + 20 && spawn > 50000000)
			{
				spawn -= 25000000;
				lastpoint=score;
			}
		}
	}
	
	//This method sets the speed of the falling apples based on the current score of the game
	private void setSpeed()
	{
		if(score < 25)
		{
			speed = 200;
		}
		
		
		else if(score >= 25 && score < 45)
		{
			speed = 300;
		}
		
		else if(score >= 45 && score < 65)
		{
			speed = 400;
		}
		
		else if(score >= 65 && score < 85)
		{
			speed = 500;
		}
		else if(score >= 85 && score < 105)
		{
			speed = 600;
		}
		
		else
		{
			if(score > lastpoint + 20 && speed <1000)
			{
				speed += 25;
				lastpoint=score;
			}
		}
	}
	
	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		appleImage.dispose();
		goldAppleImage.dispose();
		rotAppleImage.dispose();
		bucketImage.dispose();
		backTree.dispose();
		appleSound.dispose();


	}

	@Override
	public void show() {
		

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
