package com.spokanevalley.minigames.plantesferry;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class PlantesFerry extends Table {

  private long lastMonsterSpawnTime = 0L;
  private long lastAppleSpawnTime = 0L;
  private Array <Monster> monsters;
  private Array <Apples> apples;
  public SwimmingDino playerDino;
  private ScrollingBg river;
  public final float row1 = 90.0F;
  public final float row2 = 240.0F;
  public final float row3 = 390.0F;
  
  private TextButton buttonPause;
  private TextureAtlas atlas;
  private Skin skin;
  private Table table;
  BitmapFont scoreFont;

  /*
   * Constructor for PlantesFerry Game
   * Sets the bounds, and enable content clipping
   * Adds the river, player, and array of Monsters
   */
  public PlantesFerry()
  {
	// Bounds
    setBounds(0.0F, 0.0F, 800.0F, 480.0F);
    setClip(true);
    // Character and Background
    this.river = new ScrollingBg(getWidth(), getHeight());
    addActor(this.river);
    this.playerDino = new SwimmingDino(this);
    addActor(this.playerDino);
    // Monster and Apple ArrayList
    this.monsters = new Array<Monster>();
    this.apples = new Array<Apples>();
    
	/* Pause Button Setup */
    /*
	this.atlas = new TextureAtlas(Gdx.files.internal("gfx/buttons.pack"));
	this.skin = new Skin(this.atlas);
	this.table = new Table(this.skin);
	this.table.setFillParent(true);
	this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	this.table.setOrigin(125, 1000);
	
	TextButtonStyle textButtonStyle = new TextButtonStyle();
	textButtonStyle.up = skin.getDrawable("simplebutton");
	textButtonStyle.down = skin.getDrawable("simplebutton2");
	textButtonStyle.pressedOffsetX = 1;
	textButtonStyle.pressedOffsetY = -1;
	this.scoreFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
				Gdx.files.internal("fonts/gamefont_0.png"), false);
	textButtonStyle.font = this.scoreFont;
	this.buttonPause = new TextButton("PAUSE", textButtonStyle);
	this.buttonPause.pad(20);
    this.buttonPause.setHeight(64);
    this.buttonPause.setWidth(128);
    this.buttonPause.setPosition(125, 1000);
    
    buttonPause.addListener(new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");
                System.out.println("Pressed");
                return true;
        }
        
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Released");
                System.out.println("Released");
        }
    });
    add(this.buttonPause);
    
    */
  } // End PlantesFerry Constructor

  /*
   * Spawns a River Monster in one of the three rows randomly.
   */
  private void spawnRiverMonster()
  {
    int rand = MathUtils.random(0, 2);
    float row = 0.0F;
    if (rand == 0)
    	row = 90.0F;
    if (rand == 1)
    	row = 240.0F;
    if (rand == 2)
    	row = 390.0F;
   
    Monster localRiverMonster = new Monster(getWidth(), row);
    this.monsters.add(localRiverMonster);
    addActor(localRiverMonster);
    this.lastMonsterSpawnTime = TimeUtils.nanoTime();
  } // End spawnRiverMonster
  
  private void spawnApple()
  {
    int rand = MathUtils.random(0, 2);
    float row = 0.0F;
    if (rand == 0)
    	row = 90.0F;
    if (rand == 1)
    	row = 240.0F;
    if (rand == 2)
    	row = 390.0F;
   
    Apples localApple = new Apples(getWidth(), row);
    this.apples.add(localApple);
    addActor(localApple);
    this.lastAppleSpawnTime = TimeUtils.nanoTime();
  } // End spawnApple
  
  /*
   * Iterate through the Arraylists of Monsters and Apples.
   * Remove Monsters/Apples that leave the rectangle bounds.
   * Check for collision between player, monster, and apple. 
   */
  public void act(float paramFloat)
  {	
    super.act(paramFloat);
    
    // Change monster spawn time here : 0.75E = current time
    if ((float)(TimeUtils.nanoTime() - this.lastMonsterSpawnTime) > 0.85E+009F)
    	spawnRiverMonster();
    // Change apple spawn time here : 0.45E = current time
    if ((float)(TimeUtils.nanoTime() - this.lastAppleSpawnTime) > 0.40E+009F)
    	spawnApple();
    // Change length of invinsibility here: 10.0E = current time
    if ((float)(TimeUtils.nanoTime() - Assets.isInvinsibleTime) > 10.0E+009F) {
    	Assets.isInvinsible = false;
    	Assets.isInvinsibleTime = 0L;
    }
    	

    Iterator<Monster> monsterIterator = this.monsters.iterator();
    
    Iterator<Apples> appleIterator = this.apples.iterator();
    
    // Iterate and handle collisions / out of bound elements
    for (;;)
    { 
      if (!appleIterator.hasNext())
      	  return;
      if (!monsterIterator.hasNext())
    	  return;

    
      Monster localRiverMonster = (Monster)monsterIterator.next();
      
      Apples localApple = (Apples)appleIterator.next();
      
      // Check bounds
      boundsCheck2(localApple,appleIterator);
      boundsCheck(localRiverMonster, monsterIterator);
      // Check collision
      collisionCheck2(localApple, appleIterator);
      collisionCheck(localRiverMonster, monsterIterator);
      
    } // End continuous for loop
  } // End act
  
  /*
   * Checks collision between monster and player.
   * Upon collision, the monster is removed.
   */
  private void collisionCheck(Monster localRiverMonster, Iterator<Monster> monsterIterator) {
      if (localRiverMonster.getBounds().overlaps(this.playerDino.getBounds())) {
    	  
        monsterIterator.remove();
        
        if (localRiverMonster.getX() > this.playerDino.getX()) {
        
          if (localRiverMonster.getY() > this.playerDino.getY()) {
            localRiverMonster.collision(true, true);
            this.playerDino.collision(true, true);
          } else {
            localRiverMonster.collision(true, false);
            this.playerDino.collision(true, false);
          }
        }
        else if (localRiverMonster.getY() > this.playerDino.getY()) {
          localRiverMonster.collision(false, true);
          this.playerDino.collision(false, true);
        } else {
          localRiverMonster.collision(false, false);
          this.playerDino.collision(false, false);
        }
      } // End if collision check 
      
  } // End collisionCheck
  
  /*
   * Checks if a monster is outside of the set rectangle bounds.
   * Upon being out of bounds, the monster is removed.
   */
  private void boundsCheck(Monster localRiverMonster, Iterator<Monster> monsterIterator) {
      
	  if (localRiverMonster.getBounds().x + localRiverMonster.getWidth() < 0.1F) {
        monsterIterator.remove();
        removeActor(localRiverMonster);
      }
	  
  } // End boundsCheck
  
  /*
   * Checks collision between monster and player.
   * Upon collision, the monster is removed.
   */
  private void collisionCheck2(Apples localApple, Iterator<Apples> appleIterator) {
      if (localApple.getBounds().overlaps(this.playerDino.getBounds())) {
    	  appleIterator.remove();
    	  localApple.collision(true, true);
      } // End if collision check 
      
  } // End collisionCheck
  
  /*
   * Checks if a monster is outside of the set rectangle bounds.
   * Upon being out of bounds, the monster is removed.
   */
  private void boundsCheck2(Apples localApple, Iterator<Apples> appleIterator) {
	  if (localApple.getBounds().x + localApple.getWidth() < 0.1F) {
        appleIterator.remove();
	  	removeActor(localApple);
	  }
  } // End boundsCheck
  
  /*
   * Draws the in game sprites as well as the timer font
   */
  public void draw(SpriteBatch paramSpriteBatch, float paramFloat)
  {
    paramSpriteBatch.setColor(Color.WHITE);
    super.draw(paramSpriteBatch, paramFloat);
  } // End draw
  
} // End PlantesFerry