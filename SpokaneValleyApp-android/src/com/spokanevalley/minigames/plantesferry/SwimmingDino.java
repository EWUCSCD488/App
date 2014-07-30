package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class SwimmingDino extends Actor {
	
  PlantesFerry game;
  private Rectangle bounds = new Rectangle();
  private int row;

  /*
   * Constructor for the swimming dinosaur
   * Starts the character in the middle row
   * The character can only move up or down between rows
   */
  public SwimmingDino(PlantesFerry game)
  {
    this.game = game;
    setWidth(Assets.playerDino.getRegionWidth());
    setHeight(Assets.playerDino.getRegionHeight());
    this.row = 1;
    game.getClass();
    setPosition(100.0F, game.row2 - getHeight() / 2.0F);
  } // End Constructor
  
  /*
   * Divides the rectangle container into 3 rows.
   * This allows the user to move between these 3 rows upon swiping.
   * The speed of switching a row is 0.35
   */
  private void moveToLane(int paramInt)
  {
    this.row = paramInt;
    switch (paramInt)
    {
    case 0: 
      float f3 = getX();
      this.game.getClass();
      addAction(Actions.moveTo(f3, this.game.row1 - getHeight() / 2.0F, 0.35F));
      return;
    case 1: 
      float f2 = getX();
      this.game.getClass();
      addAction(Actions.moveTo(f2, this.game.row2 - getHeight() / 2.0F, 0.35F));
      return;
    case 2:
      float f1 = getX();
      this.game.getClass();
      addAction(Actions.moveTo(f1, this.game.row3 - getHeight() / 2.0F, 0.35F));
      return;
    default: 
        return;
    } // End switch
  } // End moveToLane
  
  /*
   * Updates the bounds of the game screen
   */
  private void updateBounds()
  {
    this.bounds.set(getX(), getY(), getWidth(), getHeight());
  } // End updateBounds
  
  public void act(float paramFloat)
  {
    super.act(paramFloat);
    updateBounds();
  } // End act
  
  /*
   * Draws the swimming dinosaur and invinsibility bubble if dino collides with bubble
   */
  public void draw(SpriteBatch paramSpriteBatch, float paramFloat)
  {
    paramSpriteBatch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
    if(this.game.isInvinsible())
    	paramSpriteBatch.draw(Assets.bubble, getX(), getY(), getWidth() / 2.0F, getHeight() / 2.0F, getWidth(), getHeight(), 1.0F, 1.0F, getRotation());
    paramSpriteBatch.draw(Assets.playerDino, getX(), getY(), getWidth() / 2.0F, getHeight() / 2.0F, getWidth(), getHeight(), 1.0F, 1.0F, getRotation());
  } // End draw
  
  /*
   * Get bounds of rectangle container
   */
  public Rectangle getBounds()
  {
    return this.bounds;
  } // End getBounds
  
  /* 
   * Move player up if possible
   */
  public void tryMoveDown()
  {
    if ((getActions().size == 0) && (this.row != 0))
      moveToLane(-1 + this.row);
  } // End tryMoveDown
  
  /*
   * Move player down if possible
   */
  public void tryMoveUp()
  {
    if ((getActions().size == 0) && (this.row != 2))
      moveToLane(1 + this.row);
  } // End tryMoveUp
  
  /* 
   * Upon collision, removes the main character from the game.
   */
  public void collision(boolean paramBoolean1, boolean paramBoolean2)
  {
	int lives = this.game.getLives();
	
	if(!this.game.isInvinsible())
	{
		lives--;
		if(lives > -1)
			this.game.setLives(lives);
		else
			this.game.setLives(0);
	} // End not invinsible check
  } // End collision
  
} // End SwimmingDino