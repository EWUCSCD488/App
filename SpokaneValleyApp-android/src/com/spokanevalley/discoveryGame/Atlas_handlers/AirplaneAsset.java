package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

/**
 * define AtlasRegion for airplane , cloud and helicopter
 * 
 * @author Quyen Ha
 *
 */

public class AirplaneAsset {
	public final AtlasRegion airplane1;
	public final AtlasRegion airplane2;
	public final AtlasRegion airplane3;
	
	public AirplaneAsset(TextureAtlas atlas){
		airplane1 = atlas.findRegion(Constants.AIRPLANE1);
		airplane2 = atlas.findRegion(Constants.AIRPLANE2);
		airplane3 = atlas.findRegion(Constants.AIRPLANE3);
		
	}
}
