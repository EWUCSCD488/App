//package com.me.mygdxgame;
package com.spokanevalley.ski;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;



import com.badlogic.gdx.Screen;
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
//import com.spokanevalley.app.GameLauncher;
import com.spokanevalley.database.DatabaseInterface;
import com.spokanevalley.database.SpokaneValleyDatabaseHelper;

public class SkiGame implements Screen {

	final Ski Game;
	Texture flagImage;
	Texture yFlagImage;
	Texture pineTreeImage;
	Texture skierImage;
	Texture skierLImage;
	Texture skierRImage;
	Texture background;
	Texture bflagImage;
	Sprite back;
	Sound treeSound;
	Sound flagSound;
	Sound bflagSound;
	Music skiMusic;
	OrthographicCamera camera;
	SpriteBatch batch;
	Rectangle skier;
	Array<Skiob> Objects;
	long lastObTime;
	double speed = 100;
	int flag = 0;
	int spawn = 500000000;
	float lasttime = 0;
	boolean crashing = false;
	boolean left= false;
	boolean right= false;
	
	Texture buttonLImage;
	Rectangle buttonL;
	Texture buttonRImage;
	Rectangle buttonR;
	
	private int score;
	private Context context;
	
	private static final String TAG = SkiGame.class.getName();
	
	
	private String points;
	BitmapFont scoreFont;
	private int crash = 0;

	public SkiGame(Ski gam, Context context) {

		this.Game = gam;
		this.context = context;
		
		
		
		flagImage = new Texture(Gdx.files.internal(Constants.IMAGE_FLAG)); // internal
		bflagImage = new Texture(Gdx.files.internal(Constants.IMAGE_BFLAG));																	// files																		// folder
		skierImage = new Texture(Gdx.files.internal(Constants.IMAGE_SKIER));
		skierLImage = new Texture(Gdx.files.internal(Constants.IMAGE_SKIERL));
		skierRImage = new Texture(Gdx.files.internal(Constants.IMAGE_SKIERR));
		yFlagImage = new Texture(Gdx.files.internal(Constants.IMAGE_YFLAG));
		pineTreeImage = new Texture(Gdx.files.internal(Constants.IMAGE_PTREE));
		background.setEnforcePotImages(false);
		background = new Texture(Gdx.files.internal(Constants.BACKGROUND));
		back = new Sprite(background);
		back.setSize(800, 480);

		buttonLImage.setEnforcePotImages(false);
		buttonLImage = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTONL));
		buttonL = new Rectangle();
		buttonL.x = 10; // centered horizontally
		buttonL.y = 20;
		buttonL.width = 129;
		buttonL.height = 57;
		
		buttonRImage.setEnforcePotImages(false);
		buttonRImage = new Texture(Gdx.files.internal(Constants.IMAGE_BUTTONR));
		buttonR = new Rectangle();
		buttonR.x = 660; // centered horizontally
		buttonR.y = 20;
		buttonR.width = 129;
		buttonR.height = 57;
		
		
		// load the apple sound effect
		treeSound = Gdx.audio.newSound(Gdx.files
				.internal(Constants.SOUND_TREE)); // Sound is stored in memory
		flagSound = Gdx.audio.newSound(Gdx.files
				.internal(Constants.SOUND_FLAG));
		bflagSound = Gdx.audio.newSound(Gdx.files
				.internal(Constants.SOUND_BFLAG));
		skiMusic = Gdx.audio.newMusic(Gdx.files
				.internal(Constants.SOUND_MUSIC)); // music is stored in
													// internal and stream from
													// it

		// start the background music immediately
		skiMusic.setLooping(true);
		skiMusic.play();

		// create camera and SpriteBatch class to draw objects to screen
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH_GAME, Constants.HEIGHT_GAME);

		batch = new SpriteBatch();

		// draw skier
		skier = new Rectangle();
		skier.x = Constants.WIDTH_GAME / 2 - 64 / 2; // centered horizontally
		skier.y = 350;
		skier.width = 40;
		skier.height = 70;

		// initialize Ski array
		Objects = new Array<Skiob>();
		spawnOb();

		// initialize score
		score = 0;
		points = "SCORE: 0";
		scoreFont = new BitmapFont(Gdx.files.internal(Constants.GAME_FONT), false);
		
		// save max score to database
		DatabaseInterface.Create(context).saveInitialScoretoDatabase_SkiGame(score);
	}

	@Override
	public void resize(int width, int height) {

	}
	
	public static boolean pointInRectangle (Rectangle r, float x, float y) {
	    return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); // set color to blue
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the screen

		camera.update();

		batch.setProjectionMatrix(camera.combined); // camera.combined is a
													// matrix which is passed
													// into SpriteBatch
													// from now on, SpriteBatch
													// will render everything in
													// coordinate system

		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		left = false;
		right = false;
		if (Gdx.input.isTouched()) { // as if screen is currently touch
			if (pointInRectangle(buttonL, touchPos.x, touchPos.y))
			{
				if(skier.x!=0)
				{
				skier.x -= 300 * Gdx.graphics.getDeltaTime();
				left = true;
				if(skier.x < 0)
					skier.x = 0;
				}
			}
			if (pointInRectangle(buttonR, touchPos.x, touchPos.y))
			{
				if(skier.x!=Constants.WIDTH_GAME - skier.width)
				{
				skier.x += 300 * Gdx.graphics.getDeltaTime();
				right = true;
				if(skier.x > Constants.WIDTH_GAME - skier.width)
					skier.x = Constants.WIDTH_GAME - skier.width;
				}
			}
		}
		
		
		// constantly update drops
		if (TimeUtils.nanoTime() - lastObTime > spawn)
			spawnOb();

		// render Ski
		batch.begin(); // start new batch , it will collect all drawings from
						// begin to end and submit at once
		back.draw(batch);
		if(left)
		{
			batch.draw(skierLImage, skier.x, skier.y); // draw skier to batch
		}
		
		else if(right)
		{
			batch.draw(skierRImage, skier.x, skier.y); // draw skier to batch
		}
		
		else
		{
			batch.draw(skierImage, skier.x, skier.y); // draw skier to batch
		}


		if((lasttime) > 1)
		{
			speed+= 2;
			spawn -= 4000000;
			lasttime = 0;
			crashing= false;
		}
		else
		{
			lasttime+= Gdx.graphics.getDeltaTime();
		}
	
		checkOb();
		

		// score color and size
		scoreFont.setColor(0.0f, 0.0f, 204.0f, 1.0f);
		scoreFont.draw(batch, points, 190, 460);
		batch.draw(buttonLImage, buttonL.x, buttonL.y);
		batch.draw(buttonRImage, buttonR.x, buttonR.y);
		batch.end(); // end batch, write all drawings to screen

	}

	private void checkOb()
	{
		Iterator<Skiob> iter = Objects.iterator();
		while (iter.hasNext()) {
			Skiob ob = iter.next();

			ob.rec.y += speed * Gdx.graphics.getDeltaTime();
			if(!crashing)
			{
				if (ob.rec.y + 48 > 480)
				{
					if(!ob.isCollected && !ob.isTree)
					{
						score -= 5;
						if(score < 0)// Ensure score can't go negative
							score = 0;
						points = "SCORE: " + score;
					}
					ob.dispose();
					iter.remove();
				}

				batch.draw(ob.image, ob.rec.x, ob.rec.y);
				if (ob.rec.overlaps(skier) && ob.rec.y >= skier.y) {
					if(!ob.isCollected)
					{
						score = score + ob.points;
						ob.isCollected=true;
						if(ob.sound == 0)
						{
							treeSound.play();
						}

						else if(ob.sound == 1)
						{
							flagSound.play();
						}

						else
						{
							bflagSound.play();
						}
					}
					if(score < 0)// Ensure score can't go negative
						score = 0;
					points = "SCORE: " + score;

					if (ob.isTree) {
						crash++;
						speed = 100;
						spawn = 600000000;
						crashing = true;

					}
					if (crash > 2) {

						DatabaseInterface.Create(context).saveMaxScore_SkiGame(score);

						Game.setScreen(new GameOver(Game,score,score));//maxscore
						dispose();
					}

				}
			}

			else
			{
				ob.dispose();
				iter.remove();
			}
		}
	}
	private void spawnOb() {
		Skiob ob = new Skiob();
		Rectangle rect = new Rectangle();
		rect.x = MathUtils.random(0, 800 - 60);
		rect.y = -48;
		rect.width = 30;
		rect.height = 14;
		ob.rec = rect;
		int rand = MathUtils.random(0, 300);
		if (rand < 225) {
			ob.image = flagImage;
			ob.image = pineTreeImage;
			ob.points = 0;
			ob.isTree = true;
		} else if (rand >= 225 && rand < 240) {
			ob.sound = 3;
			ob.image = yFlagImage;
			ob.points = 40;
			rect.width = 64;
			rect.height = 14;
		} else {
			if(flag == 0)
			{
				ob.sound = 1;
				ob.image = flagImage;
				flag = 1;
			}
			
			else
			{
				ob.sound = 2;
				ob.image = bflagImage;
				flag = 0;
			}
			rect.width = 64;
			rect.height = 14;

		}
		Objects.add(ob);
		lastObTime = TimeUtils.nanoTime();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		flagImage.dispose();
		yFlagImage.dispose();
		pineTreeImage.dispose();
		skierImage.dispose();
		skierLImage.dispose();
		skierRImage.dispose();
		background.dispose();
		bflagImage.dispose();
		treeSound.dispose();
		flagSound.dispose();
		bflagSound.dispose();
		skiMusic.dispose();

	}

	@Override
	public void show() {
		skiMusic.play();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
