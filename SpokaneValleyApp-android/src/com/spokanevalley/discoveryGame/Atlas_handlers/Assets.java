package com.spokanevalley.discoveryGame.Atlas_handlers;



import android.util.Log;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.spokanevalley.discoveryGame.Constants;

/**
 * Objects creation controller
 * 
 * @author Quyen Ha
 *
 */

public class Assets implements Disposable, AssetErrorListener {

	public static final Assets instance = new Assets();		// singleton pattern
	private AssetManager assetManager;						// object loader
	
	public AirplaneAsset airplane;					
	public AppleAsset apple;
	public DinasourAsset dinosaur;
	public RocketRockAsset ground;
	public BackgroundDiscoveryAsset background;
	public BadAppleAsset badApple;
	
	// fonts asset
	public AssetFonts fonts;
	
	
	// prevent instantiation from other classes
	private Assets(){}
	
	public void init(AssetManager assetManager){
		this.assetManager = assetManager;
		this.assetManager.setErrorListener(this);		// for debugging
		this.assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);		// load all objects in game
		this.assetManager.finishLoading();
		this.assetManager.load(Constants.TEXTURE_ATLAS_DINOSAUR, TextureAtlas.class);		// load dinosaur
		this.assetManager.finishLoading();
		
		TextureAtlas atlas = this.assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);		// call in-game object atlas
		TextureAtlas atlas_dinosaur = this.assetManager.get(Constants.TEXTURE_ATLAS_DINOSAUR);	// call dinosaur atlas
		
		// anable texture filtering for pixel smoothing
		for( Texture t : atlas.getTextures() ){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
			// create game resource objects
			airplane = new AirplaneAsset(atlas);
			apple = new AppleAsset(atlas);
			dinosaur = new DinasourAsset(atlas_dinosaur);
			ground = new RocketRockAsset(atlas);
			background = new BackgroundDiscoveryAsset(atlas);
			badApple = new BadAppleAsset(atlas);
			
			// create fonts
			fonts = new AssetFonts();
		}
		
	}
		
	@Override
	public void error(String fileName, Class type, Throwable throwable) {

	}

	@Override
	public void dispose() {
		// dispose assetmanager
		this.assetManager.dispose();
		
		// dispost fonts 
		fonts.defaultBig.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultSmall.dispose();
		
	}

}
