package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
  /* Atlas/Texture Region Declarations */
  private static TextureAtlas atlas;
  public static TextureAtlas skinAtlas;
  public static TextureRegion playerDino;
  public static TextureRegion monster;
  public static TextureRegion rareMonster;
  public static TextureRegion apple;
  public static TextureRegion bubble;
  public static TextureRegion underwaterbg;
  public static TextureRegion menubg;
  /* Music/Sound Effect Declarations */
  public static Music backgroundMusic;
  public static Sound appleSound;
  public static Sound monsterSound;
  public static Sound invinsibleSound;
  public static Sound invinsibleBubbleleSound;
  
  public static BitmapFont scoreFont;
  
  /*
   * Load the image atlas, audio, score, and sprite images. Located in assets > plantesferryAssets
   */
  public static void load()
  {
	/* Load Image Atlas */
    atlas = new TextureAtlas(Gdx.files.internal("plantesferryAssets/gfx/texture.atlas"));
    skinAtlas = new TextureAtlas(Gdx.files.internal("plantesferryAssets/gfx/buttons.pack"));
    /* Load Sound Effects */
    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("plantesferryAssets/sfx/rainbowbunchie.mp3"));
    appleSound = Gdx.audio.newSound(Gdx.files.internal("plantesferryAssets/sfx/bite.wav"));
    invinsibleSound = Gdx.audio.newSound(Gdx.files.internal("plantesferryAssets/sfx/hurt.wav"));
    invinsibleBubbleleSound = Gdx.audio.newSound(Gdx.files.internal("plantesferryAssets/sfx/FX051.mp3"));
    monsterSound = Gdx.audio.newSound(Gdx.files.internal("plantesferryAssets/sfx/youch.wav"));
    
    /* Load Character / Background Sprites */
    underwaterbg = atlas.findRegion("underwater");
    menubg = atlas.findRegion("menu");
    playerDino = atlas.findRegion("waterdino");
    monster = atlas.findRegion("seaurchin");
    rareMonster = atlas.findRegion("whalemonster");
    apple = atlas.findRegion("apple32");
    bubble = atlas.findRegion("bubble64");
    /* Load Game Font */
	scoreFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
			Gdx.files.internal("fonts/gamefont_0.png"), false);
  } // End load
  
  /*
   * Dispose the current image atlas, skin atlas, font, and sound effects
   */
  public static void dispose()
  {
    atlas.dispose();
    //skinAtlas.dispose();
    //scoreFont.dispose();
    backgroundMusic.dispose();
    appleSound.dispose();
    invinsibleSound.dispose();
    monsterSound.dispose();
  } // End dispose
  
} // End Assets
