package com.spokanevalley.farm;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.spokanevalley.database.DatabaseCustomAccess;
import com.spokanevalley.ski.Ski;

public class FarmGame implements Screen {

	final Farm game;
	private Context context;
	SpriteBatch batch;
	Texture backGround, appleScore;
	Player player;
	int [] holes;
	long lastTime;
	double amountOfTicks, ns, delta, pelta, timerT;
	Vector2 hole0, hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8;
	Music backgroundMusic;
	private BitmapFont font;
	private String score, lives;
	Sprite back;
	int goodGuy, badGuy, emptyHole;
	
	private static final String FarmID = "farm42";
	private static final String tableName = "GameScoreTable";
	private static final int IDColumns = 0;
	private static final int ScoreColumns = 1;
	private static final String TAG = "FARM";
	
	public FarmGame(Farm gam, Context context) {	
		this.game = gam;
		this.context = context;
		batch = new SpriteBatch();
		player = new Player(new Vector2(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4),"farmAssets/Dinasour.png", "farmAssets/Squish.mp3", "farmAssets/appletrees.png", "farmAssets/shovel.png");
		holes = new int[11];
		appleScore = new Texture(Gdx.files.internal("farmAssets/apple.png"));
		goodGuy = 2;
		badGuy = 1;
		
		//Set initial databse score to zero
	    DatabaseCustomAccess.Create(this.context).saveInitialScoretoDatabase_GreenacresGame(0);
		
		//Setting up Timer Tick
		timerT = 0.2D;
		setTimerTick(timerT);
		
		//Setting the holes
		setHoles6();
				

		
		//Setting Score
		setScore();

		//Setting Lives
		setLives();
		//Setting background music to play
		setMusic("farmAssets/RunAmok.mp3");
		//Setting Background
		setBackground("farmAssets/url.jpg");
		

	}

	@Override
	public void dispose() {
		batch.dispose();
		backGround.dispose();
		appleScore.dispose();
		backgroundMusic.dispose();
		font.dispose();
	}

	@Override
	public void render(float delta1) {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//If end of game, end game
		if (holes[holes.length-2] == 0)
		{
			int score = saveMaxScore(holes[holes.length-1]);
			System.out.println("THIS IS THE SCORE FOR THIS GAME: " + score);
			int maxScoreScore = DatabaseCustomAccess.Create(context).saveMaxScore_GreenacresGame(holes[holes.length-1]);
			game.setScreen(new GameOver(game,score,maxScoreScore));//maxscore
			//dispose();
		}
		
		//If a timer tick has passed, generate a new array
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		pelta += (now - lastTime) / (1000000000/0.1D);
		lastTime = now;
		//If it's time then change the array
		if(delta >= 1){
			fillArray();
			delta--;
		}
		//Makes sure speed increases gradually every 10ish seconds up untill a point
		if(pelta >= 1 && timerT < 1){
			timerT += 0.1;
			ns = 1000000000 / timerT;
			pelta--;
		}
		
		//Begin Drawing
		batch.begin();
		
		//Draw Background
		back.draw(batch);
		
		//Draw the Marios, Pipes, and Flowers based on the array
		if(holes[0] == badGuy)
			batch.draw(player.getTextureMario(), hole0.x, hole0.y);
		else if(holes[0] == goodGuy)
			batch.draw(player.getTextureFlower(), hole0.x, hole0.y);
		else
			batch.draw(player.getTextureHole(), hole0.x, hole0.y);
		
		
		
		if(holes[1] == badGuy)
			batch.draw(player.getTextureMario(), hole1.x, hole1.y);
		else if(holes[1] == goodGuy)
			batch.draw(player.getTextureFlower(), hole1.x, hole1.y);
		else
			batch.draw(player.getTextureHole(), hole1.x, hole1.y);
		
		
		
		if(holes[2] == badGuy)
			batch.draw(player.getTextureMario(), hole2.x, hole2.y);
		else if(holes[2] == goodGuy)
			batch.draw(player.getTextureFlower(), hole2.x, hole2.y);
		else
			batch.draw(player.getTextureHole(), hole2.x, hole2.y);
		
		
		
		if(holes[3] == badGuy)
			batch.draw(player.getTextureMario(), hole3.x, hole3.y);
		else if(holes[3] == goodGuy)
			batch.draw(player.getTextureFlower(), hole3.x, hole3.y);
		else
			batch.draw(player.getTextureHole(), hole3.x, hole3.y);
		
		
		
		if(holes[4] == badGuy)
			batch.draw(player.getTextureMario(), hole4.x, hole4.y);
		else if(holes[4] == goodGuy)
			batch.draw(player.getTextureFlower(), hole4.x, hole4.y);
		else
			batch.draw(player.getTextureHole(), hole4.x, hole4.y);
		
		
		
		if(holes[5] == badGuy)
			batch.draw(player.getTextureMario(), hole5.x, hole5.y);
		else if(holes[5] == goodGuy)
			batch.draw(player.getTextureFlower(), hole5.x, hole5.y);
		else
			batch.draw(player.getTextureHole(), hole5.x, hole5.y);
		
		
		
		if(holes[6] == badGuy)
			batch.draw(player.getTextureMario(), hole6.x, hole6.y);
		else if(holes[6] == goodGuy)
			batch.draw(player.getTextureFlower(), hole6.x, hole6.y);
		else
			batch.draw(player.getTextureHole(), hole6.x, hole6.y);
		
		
		
		if(holes[7] == badGuy)
			batch.draw(player.getTextureMario(), hole7.x, hole7.y);
		else if(holes[7] == goodGuy)
			batch.draw(player.getTextureFlower(), hole7.x, hole7.y);
		else
			batch.draw(player.getTextureHole(), hole7.x, hole7.y);
		
		
		
		if(holes[8] == badGuy)
			batch.draw(player.getTextureMario(), hole8.x, hole8.y);
		else if(holes[8] == goodGuy)
			batch.draw(player.getTextureFlower(), hole8.x, hole8.y);
		else
			batch.draw(player.getTextureHole(), hole8.x, hole8.y);
		
		//Draw Score
		batch.draw(appleScore, 25, 100);
		font.draw(batch, Integer.toString(holes[holes.length -1]), 150, 200);
		
		//Draw Lives
		font.draw(batch, lives + Integer.toString(holes[holes.length -2]), Gdx.graphics.getWidth()/2, 200);
		
		//Check for touches
		if(Gdx.input.justTouched())
			holes = player.update(holes);

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public void setScore(){
		holes[holes.length-1]= 0; // The score so we can pass between classes
		score = "Score: ";
		lives = "Lives: ";
		font = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
				Gdx.files.internal("fonts/gamefont_0.png"), false);
		font.setScale(5);	
	}
	
	public void setLives(){
		holes[holes.length -2] = 3;
	}
	
	public void setBackground(String background){
		backGround = new Texture(Gdx.files.internal(background));
		back = new Sprite(backGround);
		back.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void setMusic(String music){
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(music));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}
	
	public void setHoles6(){
		hole0 = new Vector2(player.getPosition().x, player.getPosition().y);
		hole1 = new Vector2((player.getPosition().x)*2, player.getPosition().y);
		hole2 = new Vector2((player.getPosition().x)*3, player.getPosition().y);
		hole3 = new Vector2(player.getPosition().x, (player.getPosition().y)*2);
		hole4 = new Vector2((player.getPosition().x)*2, (player.getPosition().y)*2);
		hole5 = new Vector2((player.getPosition().x)*3, (player.getPosition().y)*2);
		hole6 = new Vector2(player.getPosition().x, (player.getPosition().y)*3);
		hole7 = new Vector2((player.getPosition().x)*2, (player.getPosition().y)*3);
		hole8 = new Vector2((player.getPosition().x)*3, (player.getPosition().y)*3);
	}
	
	public void setTimerTick(double howFast){
		lastTime = System.nanoTime();
		amountOfTicks = howFast;
		ns = 1000000000 / amountOfTicks;
		delta = 0;
		pelta = 0;
	}
	
	public void fillArray(){
		//Fill the array with random ints from 0 to 2
		for(int i = 0; i < holes.length -2; i++){
			//If person still hasn't hit the bad object subtract points
			if(holes[i] == badGuy)
				holes[holes.length -1] -= 1;
			else if(holes[i] == goodGuy)
				holes[holes.length -1] += 1;
			holes[i] = (int) (Math.random()*(3-0)+0);
		}
	}
	
	private int saveMaxScore(int CurrentScore) {
		Cursor cursor= (DatabaseCustomAccess.Create(context)).getDatabase().getScoreData(tableName,FarmID);
		
		int MaxScore = 0;
		while(cursor.moveToNext()){
			//loading each element from database
			String ID = cursor.getString(IDColumns);
			int MaxScore_temp =  Integer.parseInt(cursor.getString(ScoreColumns));
				if(MaxScore_temp > MaxScore)
				{
					MaxScore = MaxScore_temp ;
				Log.d(TAG,"MaxScore is"+ MaxScore_temp+ " with ID : " + ID);}
				System.out.println("MaxScore is"+ MaxScore_temp+ " with ID : " + ID + "****************************************************");

		} // travel to database result
		
		if(MaxScore < CurrentScore){
			long RowIds = (DatabaseCustomAccess.Create(context)).getDatabase().updateScoreTable(tableName, FarmID, String.valueOf(holes[holes.length-1]));
			if (RowIds == -1)
				Log.d(TAG, "Error on inserting columns");
			System.out.println(holes[holes.length-1] + "****************************************************");
			return CurrentScore;
		}
		System.out.println("****************************hello***********************");

		return MaxScore;
		
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
