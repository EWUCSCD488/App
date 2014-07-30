package com.spokanevalley.minigames.plantesferry;

import android.content.Context;

import com.badlogic.gdx.Game;

public class GameSetup extends Game {
	
	  PlantesFerry game;
	  MenuScreen menuScreen;
	  GameScreen gameScreen;
	  GameOverScreen gameoverScreen;
	  private Context context;
	  
	  public GameSetup(Context applicationContext) {
		this.setContext(applicationContext);
	  } // End Constructor

	  /*
	   * First creates a new Plantes Ferry game
	   * Next, the Menu, Game, and Game Over Screens are created
	   */
	  @Override
	  public void create()
	  {
	    Assets.load();
	    this.game = new PlantesFerry(this.context);
	    this.menuScreen = new MenuScreen(this, this.game);
	    this.gameScreen = new GameScreen(this, this.game);
	    //this.gameoverScreen = new GameOverScreen(this, this.game);
	    setScreen(this.menuScreen);
	  } // End create
	  
	  /*
	   * The Screens are properly disposed of
	   */
	  @Override
	  public void dispose()
	  {
	    Assets.dispose();
	    this.gameScreen.dispose();
	    this.menuScreen.dispose();
	    //this.gameoverScreen.dispose();
	  } // End dispose

	public Context getContext() {
		return context;
	} // End getContext

	public void setContext(Context context) {
		this.context = context;
	} // End setContext
	  
	} // End GameSetup

