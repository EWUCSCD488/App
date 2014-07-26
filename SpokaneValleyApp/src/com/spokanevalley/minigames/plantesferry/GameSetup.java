package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Game;

public class GameSetup extends Game {
  public static final int HEIGHT = 480;
  public static final int WIDTH = 800;
  private GameScreen gameScreen;
  
  //private MenuScreen menuScreen;
  
  public void create()
  {
    Assets.load();
    //this.menuScreen = new MenuScreen();
    //setScreen(this.menuScreen);
    this.gameScreen = new GameScreen();
    setScreen(this.gameScreen);
  } // End create
  
  public void dispose()
  {
    Assets.dispose();
    //this.menuScreen.dispose();
    this.gameScreen.dispose();
  } // End dispose
  
} // End GameSetup
