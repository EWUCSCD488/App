package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.spokanevalley.discoveryGame.Constants;

/**
 * define BitmapFont for game
 * small font is scaled about 75% original size
 * big font is scaled about 150% original size
 * 
 * @author Quyen Ha
 *
 */

public class AssetFonts {
	public final BitmapFont defaultSmall;
	public final BitmapFont defaultNormal;
	public final BitmapFont defaultBig;
	
	public AssetFonts(){
		// create 3 fonts using libgdx's 15 px bitmap font
		defaultSmall = new BitmapFont(Gdx.files.internal(Constants.GAMEFONTS), true);
		defaultNormal = new BitmapFont(Gdx.files.internal(Constants.GAMEFONTS), true);
		defaultBig = new BitmapFont(Gdx.files.internal(Constants.GAMEFONTS), true);
		
		// set font sizes
		defaultSmall.setScale(0.75f);
		defaultNormal.setScale(1.0f);
		defaultBig.setScale(1.5f);
		
		//anable linear texture filtering for smooth fonts
		defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
}
