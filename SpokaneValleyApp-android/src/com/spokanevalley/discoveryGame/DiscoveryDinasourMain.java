package com.spokanevalley.discoveryGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.spokanevalley.discoveryGame.Atlas_handlers.Assets;

public class DiscoveryDinasourMain implements ApplicationListener{

	public static final String TAG = DiscoveryDinasourMain.class.getName();
	private boolean paused;
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
		
	public DiscoveryDinasourMain() {
		
	}

	@Override
	public void create() {
		// SET LIBGDX LOG LEVEL TO DEBUG
		Gdx.app.setLogLevel(com.badlogic.gdx.Application.LOG_DEBUG);
		
		//Loading asset
		Assets.instance.init(new AssetManager());
		
		//INITIALIZE CONTROLLER AND RENDERER
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		
		//game world is active on start
		paused = false;
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void render() {
		// do not update game world when paused
		if(!paused){
			
			// UPDATE GAME WORLD BY THE TIME THAT HAS PASSED
			// SINCE LAST RENDERED FRAME
			worldController.update(Gdx.graphics.getDeltaTime());
		}
			
			//SET THE CLEAR SCREEN COLOR TO : CORNFLOWER BLUE
			Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f , 0xed/255.0f, 0xff/255.0f);
			
			//CLEARS THE SCREEN
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			//Render game world to screen
			worldRenderer.render();
		
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
		paused = false;
	}

	@Override
	public void dispose() {
		
		worldRenderer.dispose();
		Assets.instance.dispose();
	}

}
