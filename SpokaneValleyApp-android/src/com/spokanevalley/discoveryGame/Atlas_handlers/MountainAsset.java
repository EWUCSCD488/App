package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

public class MountainAsset {
	public final AtlasRegion moutainLeft;
	public final AtlasRegion moutainRight;
	
	public MountainAsset(TextureAtlas atlas){
		moutainLeft = atlas.findRegion(Constants.MOUNTAIN_LEFT);
		moutainRight = atlas.findRegion(Constants.MOUNTIAN_RIGHT);
	}
}
