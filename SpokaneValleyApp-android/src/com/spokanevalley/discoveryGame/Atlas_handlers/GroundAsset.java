package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

public class GroundAsset {
	public final AtlasRegion edge;
	public final AtlasRegion middle;
	
	public GroundAsset(TextureAtlas atlas){
		edge = atlas.findRegion(Constants.GROUND_EDGE);
		middle = atlas.findRegion(Constants.GROUND_MIDDLE);
	}
}
