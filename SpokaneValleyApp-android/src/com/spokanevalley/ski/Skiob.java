package com.spokanevalley.ski;
//package com.me.mygdxgame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Skiob 
{
	public Texture image;
	public Rectangle rec;
	public int points = 20;
	public boolean isTree=false;
	public boolean isCollected=false;
	public int sound = 0;
	
	Skiob()
	{
		rec = new Rectangle();
	}
	
}
