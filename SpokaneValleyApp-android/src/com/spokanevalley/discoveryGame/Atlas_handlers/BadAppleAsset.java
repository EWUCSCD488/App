package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

public class BadAppleAsset {
public final AtlasRegion Specialcoin;
	
	public BadAppleAsset(TextureAtlas atlas){
		Specialcoin = atlas.findRegion(Constants.COIN_NAME);
	}
}
