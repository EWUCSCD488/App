package com.spokanevalley.discoveryGame.Atlas_handlers;



import android.util.Log;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.spokanevalley.discoveryGame.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public CloudAsset cloud;
	public CoinAsset coin;
	public DinasourAsset dinasour;
	public GroundAsset ground;
	public MountainAsset mountain;
	public SkyAsset sky;
	public SpecialCoinAsset specialCoin;
	
	// fonts asset
	public AssetFonts fonts;
	
	
	// prevent instantiation from other classes
	private Assets(){}
	
	public void init(AssetManager assetManager){
		this.assetManager = assetManager;
		this.assetManager.setErrorListener(this);
		this.assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		this.assetManager.finishLoading();
		this.assetManager.load(Constants.TEXTURE_ATLAS_DINASOUR, TextureAtlas.class);
		this.assetManager.finishLoading();
		Log.d(TAG, "Number of assets loaded: "+ this.assetManager.getAssetNames().size);
		
		for(String element :  this.assetManager.getAssetNames()){
			Log.d(TAG, "asset : "+ element);
		}
		
		TextureAtlas atlas = this.assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		TextureAtlas atlas_dinasour = this.assetManager.get(Constants.TEXTURE_ATLAS_DINASOUR);
		// anable texture filtering for pixel smoothing
		for( Texture t : atlas.getTextures() ){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
			// create game resource objects
			cloud = new CloudAsset(atlas);
			coin = new CoinAsset(atlas);
			dinasour = new DinasourAsset(atlas_dinasour);
			ground = new GroundAsset(atlas);
			mountain = new MountainAsset(atlas);
			sky = new SkyAsset(atlas);
			specialCoin = new SpecialCoinAsset(atlas);
			
			// create fonts
			fonts = new AssetFonts();
		}
		
	}
		
	@Override
	public void error(String fileName, Class type, Throwable throwable) {
		Log.d(TAG, "cound not load "+ fileName + " at " + (Exception)throwable);
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
