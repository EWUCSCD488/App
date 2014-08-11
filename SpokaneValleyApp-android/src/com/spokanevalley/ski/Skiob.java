/*
 * This class is a container used to store all of the attributes for the trees and flags used in the game.
 */

package com.spokanevalley.ski;
//package com.me.mygdxgame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Skiob 
{
	public Texture image;
	public Rectangle rec;
	public int points = 3;
	public boolean isTree=false;
	public boolean isCollected=false;
	public int sound = 0;
	
	public Skiob()
	{
		rec = new Rectangle();
	}
	
	public void dispose()
	{
	}
}
