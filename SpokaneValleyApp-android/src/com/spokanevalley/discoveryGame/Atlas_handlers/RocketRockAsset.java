package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

/**
 * Create rock object in game
 * rock will contain 2 things, middle rock and edge rock
 * 
 * @author Quyen Ha
 *
 */

public class RocketRockAsset {
	public final AtlasRegion edge;
	public final AtlasRegion middle;
	
	public RocketRockAsset(TextureAtlas atlas){
		edge = atlas.findRegion(Constants.GROUND_EDGE);
		middle = atlas.findRegion(Constants.GROUND_MIDDLE);
	}
}
