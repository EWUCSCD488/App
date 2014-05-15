package com.me.mygdxgame;



import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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

public class AppleGame implements ApplicationListener {
	
	Texture appleImage;
	Texture goldAppleImage;
	Texture rotAppleImage;
	Texture bucketImage;
	Texture backTree;
	Sprite back;
	Sound appleSound;
	Music appleMusic;
	OrthographicCamera camera;
	SpriteBatch batch;
	Rectangle bucket;
	Array<Appleob> Apples;
	long lastAppleTime;
	private int score;
	private String applesCaught;
	BitmapFont scoreFont;
	
	@Override
	public void create() {
		
		//load the images for apple and bucket, 64x64 pixels each
		appleImage =  new Texture(Gdx.files.internal(Constants.IMAGE_APPLE));			// internal files is located in assets folder
		bucketImage = new Texture(Gdx.files.internal(Constants.IMAGE_BUCKET));
		goldAppleImage = new Texture(Gdx.files.internal(Constants.IMAGE_GAPPLE));
		rotAppleImage = new Texture(Gdx.files.internal(Constants.IMAGE_RAPPLE));
		backTree.setEnforcePotImages(false);
		backTree = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(backTree);
		back.setSize(480, 800);
		
		// load the apple sound effect
		appleSound = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_APPLE));		// Sound is stored in memory 
		appleMusic = Gdx.audio.newMusic(Gdx.files.internal(Constants.SOUND_MUSIC));			// music is stored in internal and stream from it
		
		// start the background music immediately
		appleMusic.setLooping(true);
		appleMusic.play();
		
		// create camera and SpriteBatch class to draw objects to screen
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);
		
		batch = new SpriteBatch();
		
		//draw bucket
		bucket = new Rectangle();
		bucket.x = Constants.WIDTH_GAME /2 - 64/2;	// centered horizontally
		bucket.y = 20;								// 20 pixels from the bottom 		, 	be default, y is counting upward
		bucket.width = 64;
		bucket.height = 64;
		
		//initialize Apple array
		Apples = new Array<Appleob>();
		spawnApple();
		
		//initialize score
		score = 0;
	    applesCaught = "score: 0";
	    scoreFont = new BitmapFont();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);					// set color to blue
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);			// clear the screen
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);			// camera.combined is a matrix which is passed into SpriteBatch
															// from now on, SpriteBatch will render everything in coordinate system
		
		if(Gdx.input.isTouched()){				// as if screen is currently touch
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64/2;
		}
		
		// constantly update drops
		if(TimeUtils.nanoTime() - lastAppleTime > 200000000)
			spawnApple();
		
		
		
		//render Apple
		batch.begin();										// start new batch , it will collect all drawings from begin to end and submit at once
		back.draw(batch);
		batch.draw(bucketImage,bucket.x,bucket.y);			// draw bucket to batch
		
		Iterator<Appleob> iter = Apples.iterator();
		while(iter.hasNext()){
			Appleob Apple = iter.next();
			Apple.rec.y -= 600 * Gdx.graphics.getDeltaTime();
			if(Apple.rec.y + 64 <0) iter.remove();
			
				batch.draw(Apple.image,Apple.rec.x,Apple.rec.y);
				if(Apple.rec.overlaps(bucket)){
					score = score + Apple.points;
			    	applesCaught = "score: " + score;
			    	appleSound.play();
			    	iter.remove();
				}
		}
		
		
		//score color and size
		scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		scoreFont.setScale((float) 2);
		scoreFont.draw(batch, applesCaught, 190, 750); 
		batch.end();										// end batch, write all drawings to screen
		
		
		
	}
	
	private void spawnApple() {
		Appleob ob = new Appleob();
	      Rectangle Apple = new Rectangle();
	      Apple.x = MathUtils.random(0, 480-64);
	      Apple.y = 720;
	      Apple.width = 64;
	      Apple.height = 64;
	      ob.rec = Apple;
	      int rand = MathUtils.random(0, 300);
	      if(rand < 225)
	      {
	    	  ob.image = appleImage;
	      }
	      else if(rand >= 225 && rand < 250)
	      {
	    	  ob.image = goldAppleImage;
	    	  ob.points = 3;
	      }
	      else
	      {
	    	  ob.image = rotAppleImage;
	    	  ob.points = -5;
	      }
	      Apples.add(ob);
	      lastAppleTime = TimeUtils.nanoTime();
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
	      bucketImage.dispose();
	      appleSound.dispose();
	      appleMusic.dispose();
	      batch.dispose();

	}

}
