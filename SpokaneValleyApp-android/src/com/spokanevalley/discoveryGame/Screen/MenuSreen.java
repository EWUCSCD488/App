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

/*
 * @author : Quyen Ha
 * Create menu screen for Disvovery Park game.
 */
public class MenuSreen extends AbstractGameScreen {

	private Stage stage;
	private Skin skinDinasourMenu; // skin holds all images load from atlas
	private TextureAtlas atlas; // atlas is loaded from package to save memory
								// and convienent to use
	// menu
	private Image imgBackGround; // background image for menu
	private Button MenuPlay; // PLay button in menu
	private Button ExitOptions; // exit game button
	private Button MenuSound; // mute sound effects or leave music on
	private Button MenuMusic; // mute background music or leave music on


	private TextButtonStyle textButtonStyle_sound_music; // music Button
															// background, fonts
															// and other
															// attributes
	private TextButtonStyle textButtonStyle_sound_noMusic; // no music Button
															// background, fonts
															// and other
															// attributes
	private TextButtonStyle textButtonStyle_sound_sound; // sound Button
															// background, fonts
															// and other
															// attributes
	private TextButtonStyle textButtonStyle_sound_noSound; // no sound Button
															// background, fonts
															// and other
															// attributes

	/*
	 * Constructor for MenuScreen , will call from Libgdx database to get values
	 * if music and sound are muted or not
	 */
	public MenuSreen(Game game) {
		super(game);
		GameMusicSoundPref.create();
		GameMusicSoundPref.create().playbackground1();
	}

	/*
	 * Draw Menu with this method. This method will be called 60 times /s
	 * (60fps)
	 */
	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(deltaTime); // update everything in stage
		stage.draw(); // draw stage out
	}

	/*
	 * this method will be called to adjust menu resolution based on
	 * VIEWPORT_GUI_WIDTH and VIEWPORT_GUI_HEIGHT in Constants class
	 */
	@Override
	public void resize(int width, int height) {
		stage.setViewport(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT, false);
	}

	/*
	 * this method is called when menu is loaded it creates the stage of menu
	 * and sets it clickable
	 */

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	/*
	 * this method is called when users click play or when they get our of menu
	 * it disposes skin, atlas and stage to prevent leaking memories
	 */

	@Override
	public void hide() {
		skinDinasourMenu.dispose();
		GameMusicSoundPref.create().stopBackground1();
		GameMusicSoundPref.create().stopBackground2();
		GameMusicSoundPref.create().disposeSound();
		atlas.dispose();
		stage.dispose();
	}

	/*
	 * 
	 *	this method is called when users hit home button or app switching button at bottom Android bar
	 *	I don't use this method because menu will call show method after they get back to game.
	 */
	
	@Override
	public void pause() {

	}

	/*
	 *  Create 3 layers : background,controls and musicSound, load them into Stack and load Stack into stage for displaying
	 */
	
	private void rebuildStage() {

		atlas = new TextureAtlas(Constants.MENU_ATLAS);				// load atlas package stored in assets/discoveryAssets/images
		skinDinasourMenu = new Skin(atlas);							// load atlas package into skin for building layers

		// build layers
		Table layerBackground = buildBackgroundLayer();				// build background layer
		Table layerControls = buildControlLayer();					// build play,exit buttons layer
		Table layerMusicMenu = buildMusicLayer();					// build music,sound buttons layer

		// preapre stage
		stage.clear();												// make sure stage is empty before using
		Stack stack = new Stack();									// create stack (libgdx stack)
		stage.addActor(stack);										// add stack to stage
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH,					// set size for menu
				Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);									// add background at bottom
		stack.add(layerControls);									// then add controls on top of background
		stack.addActor(layerMusicMenu);								// add music/sound buttons on top of background

	}

	/*
	 *  Build music layers. 
	 */
	
	private Table buildMusicLayer() {
		Table table = new Table();												// create table

		textButtonStyle_sound_music = createButtonStyle("musicButtonBefore");			// load image with music
		textButtonStyle_sound_noMusic = createButtonStyle("musicButtonAfter");			// load image without music
		textButtonStyle_sound_sound = createButtonStyle("soundButtonBefore");			// load image with sound
		textButtonStyle_sound_noSound = createButtonStyle("soundButtonAfter");			// load image without sound

		table.left().top();							// draw buttons at top left corner
		table.pad(10, 10, 0, 0);					// 10pixels away from top and left edges
		MenuMusic = new Button(textButtonStyle_sound_music);					// load music button
		onClickedMusicPress();
		table.add(MenuMusic).width(Constants.VIEWPORT_GUI_WIDTH / 8)			// set sizes based on screen resolution
				.height(Constants.VIEWPORT_GUI_HEIGHT / 8).pad(10);
		MenuMusic.addListener(new ChangeListener() {							// if click, then button image to no music and reverse

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameMusicSoundPref.create().MusicLogicChange();								// switch logic
				onClickedMusicPress();											// draw image based on logic above
			}

		});

		table.row();													// draw sound underneath music button
		MenuSound = new Button(textButtonStyle_sound_sound);				// load button
		onClickedSoundPress();
		table.add(MenuSound).width(Constants.VIEWPORT_GUI_WIDTH / 8)		// set size based on screen resolution
				.height(Constants.VIEWPORT_GUI_HEIGHT / 8).pad(10);			// 10 pixels away from music button
		MenuSound.addListener(new ChangeListener() {					// set what happens when users click

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameMusicSoundPref.create().SOundLogicChange();							// switch logic
				onClickedSoundPress();										// draw background based on logic above

			}

		});

		return table;
	}
	
	/*
	 * draw sound background after sound button is clicked
	 */
	
	protected void onClickedSoundPress() {
		if (GameMusicSoundPref.create().getSound())
			MenuSound.setStyle(textButtonStyle_sound_sound);
		else
			MenuSound.setStyle(textButtonStyle_sound_noSound);

	}

	/*
	 * draw music background after music button is clicked
	 */
	
	private void onClickedMusicPress() {
		if (GameMusicSoundPref.create().getMusic())
			MenuMusic.setStyle(textButtonStyle_sound_music);
		else
			MenuMusic.setStyle(textButtonStyle_sound_noMusic);

	}

	/*
	 * create properties for button
	 */
	
	private TextButtonStyle createButtonStyle(String filename) {
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skinDinasourMenu.getDrawable(filename);
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = 1;

		return textButtonStyle;
	}

	/*
	 * build start and exit buttons 
	 */
	
	private Table buildControlLayer() {
		Table table = new Table();

		TextButtonStyle textButtonStyle_start = new TextButtonStyle();		// set style for button
		textButtonStyle_start.up = skinDinasourMenu							// background before clicking
				.getDrawable("startButtonbefore");	
		textButtonStyle_start.down = skinDinasourMenu						// background after clicking	
				.getDrawable("startButtonafter");
		textButtonStyle_start.pressedOffsetX = 1;
		textButtonStyle_start.pressedOffsetY = 1;

		TextButtonStyle textButtonStyle_option = new TextButtonStyle();
		textButtonStyle_option.up = skinDinasourMenu
				.getDrawable("optionButtonBefore");
		textButtonStyle_option.down = skinDinasourMenu
				.getDrawable("optionButtonAfter");
		textButtonStyle_option.pressedOffsetX = 1;
		textButtonStyle_option.pressedOffsetY = 1;

		table.right().bottom();							// draw buttons at bottom right corner
		table.pad(0, 0, 50, 50);						
		MenuPlay = new Button(textButtonStyle_start);
		table.add(MenuPlay).width(Constants.VIEWPORT_GUI_WIDTH / 5)
				.height(Constants.VIEWPORT_GUI_HEIGHT / 5).pad(10);
		MenuPlay.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onClickedStartPress();
				GameMusicSoundPref.create().playStartButton();
				GameMusicSoundPref.create().playbackground2();

			}
		});

		table.row();													
		ExitOptions = new Button(textButtonStyle_option);
		table.add(ExitOptions).width(Constants.VIEWPORT_GUI_WIDTH / 5)
				.height(Constants.VIEWPORT_GUI_HEIGHT / 5).pad(10);
		ExitOptions.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameMusicSoundPref.create().playExitButton();
				onClickedExitPress();

			}

		});

		return table;
	}

	/*
	 *  create background for menu
	 */
	
	private Table buildBackgroundLayer() {
		Table table = new Table();
		table.setBounds(0, 0, Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT);
		imgBackGround = new Image(skinDinasourMenu, "background");
		table.add(imgBackGround);
		return table;
	}

	/*
	 * if users click start button , game will start
	 */
	
	private void onClickedStartPress() {
		game.setScreen(new GameScreen(game));

	}

	private void onClickedExitPress() {
		Gdx.app.exit();
	}
}
