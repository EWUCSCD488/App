package com.spokanevalley.apples;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Appleob 
{
	public Texture image;
	public Rectangle rec;
	public int points = 1;
	public boolean isBad=false;
	
	public Appleob()
	{
		rec = new Rectangle();
	}
	
	public void dispose()
	{
		image.dispose();
	}
}
