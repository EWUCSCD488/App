package com.spokanevalley.discoveryGame.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.spokanevalley.discoveryGame.Constants;

public class MenuSreen extends AbstractGameScreen {
	
	private Stage stage;
	private Skin skinDinasourMenu;
	private TextureAtlas atlas;
	// menu
	private Image imgBackGround;
	private Button MenuPlay;
	private Button MenuOptions;
	private Button MenuSound;
	private Button MenuMusic;
	private boolean isMutedMusic = true;
	private boolean isMutedSound = true;
	
	private TextButtonStyle textButtonStyle_sound_music;
	private TextButtonStyle textButtonStyle_sound_noMusic;
	private TextButtonStyle textButtonStyle_sound_sound;
	private TextButtonStyle textButtonStyle_sound_noSound;
	
	// options
	/*private Window windowOptions;
	private TextButton winOptSave;
	private TextButton winOptCancel;
	private CheckBox checkSound;
	private Slider sliderSound;
	private CheckBox checkMusic;
	private Slider sliderMusic;*/
	
	
	
	
	public MenuSreen(Game game) {
		super(game);
		GameOptionPref.create().initialSave();
		isMutedMusic = GameOptionPref.create().loadMusic();
		isMutedSound = GameOptionPref.create().loadSound();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(deltaTime);
		stage.draw();
		
		//if(Gdx.input.isTouched())
			//game.setScreen(new GameScreen(game));
		
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT, false);
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	@Override
	public void hide() {
		stage.dispose();
		//skinDinasourMenu.dispose();
	}

	@Override
	public void pause() {

	}

	private void rebuildStage(){
		
		atlas = new TextureAtlas(Constants.MENU_ATLAS);
		skinDinasourMenu = new Skin(atlas);
		
		// build layers
		Table layerBackground = buildBackgroundLayer();
		Table layerControls = buildControlLayer();
		Table layerMusicMenu = buildMusicLayer();
		
		// preapre stage
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerControls);
		stack.addActor(layerMusicMenu);

	}

	private Table buildMusicLayer() {
		Table table = new Table();
		
		textButtonStyle_sound_music = createButtonStyle("musicButtonBefore");
		textButtonStyle_sound_noMusic = createButtonStyle("musicButtonAfter");
		textButtonStyle_sound_sound = createButtonStyle("soundButtonBefore");
		textButtonStyle_sound_noSound = createButtonStyle("soundButtonAfter");
		
		table.left().top();
		table.pad(10, 10, 0, 0);
		MenuMusic = new Button(textButtonStyle_sound_music);
		table.add(MenuMusic).width(Constants.VIEWPORT_GUI_WIDTH/8).height(Constants.VIEWPORT_GUI_HEIGHT/8).pad(10);
		MenuMusic.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				isMutedMusic = !isMutedMusic;
				GameOptionPref.create().save(isMutedMusic, isMutedSound);
				onClickedMusicPress();
			}


		});
		
		table.row();
		MenuSound = new Button(textButtonStyle_sound_sound);
		table.add(MenuSound).width(Constants.VIEWPORT_GUI_WIDTH/8).height(Constants.VIEWPORT_GUI_HEIGHT/8).pad(10);
		MenuSound.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				isMutedSound = !isMutedSound;
				GameOptionPref.create().save(isMutedMusic, isMutedSound);
				onClickedSoundPress();
				
			}

			
		});
		
		
		return table;
	}

	
	protected void onClickedSoundPress() {
		if(isMutedSound)
			MenuSound.setStyle(textButtonStyle_sound_sound);
		else
			MenuSound.setStyle(textButtonStyle_sound_noSound);
		
	}

	private void onClickedMusicPress() {
		if(isMutedMusic)
			MenuMusic.setStyle(textButtonStyle_sound_music);
		else
			MenuMusic.setStyle(textButtonStyle_sound_noMusic);
		
	}
	
	private TextButtonStyle createButtonStyle(String filename){
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skinDinasourMenu.getDrawable(filename);
		//textButtonStyle_start.down = skinDinasourMenu.getDrawable("startButtonafter");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = 1;
		
		return textButtonStyle;
	}
	
	private Table buildControlLayer() {
		Table table = new Table();
		//table.setBounds(0, 0, Constants.VIEWPORT_GUI_WIDTH/4, Constants.VIEWPORT_GUI_HEIGHT/4);
		
		TextButtonStyle textButtonStyle_start = new TextButtonStyle();
		textButtonStyle_start.up = skinDinasourMenu.getDrawable("startButtonbefore");
		textButtonStyle_start.down = skinDinasourMenu.getDrawable("startButtonafter");
		textButtonStyle_start.pressedOffsetX = 1;
		textButtonStyle_start.pressedOffsetY = 1;
		
		TextButtonStyle textButtonStyle_option = new TextButtonStyle();
		textButtonStyle_option.up = skinDinasourMenu.getDrawable("optionButtonBefore");
		textButtonStyle_option.down = skinDinasourMenu.getDrawable("optionButtonAfter");
		textButtonStyle_option.pressedOffsetX = 1;
		textButtonStyle_option.pressedOffsetY = 1;
		
		
		table.right().bottom();
		table.pad(0, 0, 50, 50);
		MenuPlay = new Button(textButtonStyle_start);
		table.add(MenuPlay).width(Constants.VIEWPORT_GUI_WIDTH/5).height(Constants.VIEWPORT_GUI_HEIGHT/5).pad(10);
		MenuPlay.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onClickedStartPress();
				
			}
		});
		
		table.row();
		MenuOptions = new Button(textButtonStyle_option);
		table.add(MenuOptions).width(Constants.VIEWPORT_GUI_WIDTH/5).height(Constants.VIEWPORT_GUI_HEIGHT/5).pad(10);
		MenuOptions.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onClickedOptionPress();
				
			}

			
		});
		
		
		return table;
	}

	private Table buildBackgroundLayer() {
		Table table = new Table();
		table.setBounds(0, 0, Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		imgBackGround = new Image(skinDinasourMenu,"background");
		table.add(imgBackGround);
		return table;
	}

	private void onClickedStartPress() {
		game.setScreen(new GameScreen(game));
		
	}
	
	private void onClickedOptionPress() {
		// TODO Auto-generated method stub
		
	}
}
