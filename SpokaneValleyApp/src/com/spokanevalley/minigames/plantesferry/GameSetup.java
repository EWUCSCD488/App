package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Game;

public class GameSetup extends Game {
	  public static final int HEIGHT = 480;
	  public static final int WIDTH = 800;
	  GameScreen gameScreen;
	  MenuScreen menuScreen;
	  GameOverScreen gameoverScreen;
	  
	  @Override
	  public void create()
	  {
	    Assets.load();
	    this.menuScreen = new MenuScreen(this);
	    this.gameScreen = new GameScreen(this);
	    this.gameoverScreen = new GameOverScreen(this);
	    setScreen(this.menuScreen);
	  } // End create
	  @Override
	  public void dispose()
	  {
	    Assets.dispose();
	    this.gameScreen.dispose();
	    this.menuScreen.dispose();
	    this.gameoverScreen.dispose();
	  } // End dispose
	  
	} // End GameSetup

