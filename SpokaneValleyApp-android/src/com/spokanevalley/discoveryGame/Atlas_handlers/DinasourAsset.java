package com.spokanevalley.discoveryGame.Atlas_handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.spokanevalley.discoveryGame.Constants;

/**
 * Define AtlasRegion for dinosaur
 * 
 * @author Quyen Ha
 *
 */

public class DinasourAsset {
	public final AtlasRegion cuteDinosaur;
	public DinasourAsset(TextureAtlas atlas){
		cuteDinosaur  = atlas.findRegion(Constants.DINOSAUR_NAME);
	}
}
