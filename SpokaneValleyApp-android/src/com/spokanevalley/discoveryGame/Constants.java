package com.spokanevalley.discoveryGame;

/**
 * define important variables in game
 * 
 * @author Quyen Ha
 *
 */

public class Constants {

		public static final float VIEWPORT_WIDTH = 5.0f;		// map length width
		public static final float VIEWPORT_HEIGHT = 5.0f;		// map length height

		public static final float VIEWPORT_GUI_WIDTH = 800.0f;		// camera view width
		public static final float VIEWPORT_GUI_HEIGHT = 480.0f;		// camera view height
		
		public static final String TEXTURE_ATLAS_OBJECTS = "disvcoveryAssets/game.pack";		// objects package
		public static final String TEXTURE_ATLAS_DINOSAUR = "disvcoveryAssets/dinasour.pack";	// dinosaur package
		
		public static final String MENU_BACKGROUND = "disvcoveryAssets/images/background.jpg";	// menu background image
		public static final String MENU_ATLAS = "disvcoveryAssets/images/menutextureatlas.pack";	//menu package contains all images for menu
		public static final String PREFERENCES = "uhhihi";				// basic small database to store variables to check if music and sound are muted or not
		
		public static final String DINOSAUR_NAME = "dino";				// name of dinosaur in dinosaur package
		
		public static final String GROUND_EDGE = "rock_edge";		 // rock edge name
		public static final String GROUND_MIDDLE = "rock_middle";		// rock middle name
		
		public static final String APPLE_NAME = "apple";			// apple name 
		public static final String BAD_APPLE_NAME = "badApple";		// bad apple name
		
		public static final String AIRPLANE1 = "airplane1";				// airplane1 name
		public static final String AIRPLANE2 = "airplane2";				// airplane2 name
		public static final String AIRPLANE3 = "airplane3";				// airplane3 name
		
		public static final String BACKGROUND_LEFT = "background_left";		// background left name
		public static final String BACKGROUND_RIGHT = "background_right";	// background right name
		
		// Location of image file for level 01
		public static final String LEVEL_01 = "disvcoveryAssets/levels/level-01test1.png";
		public static final String LEVEL_02 = "disvcoveryAssets/levels/level-01test2.png";
		public static final String LEVEL_03 = "disvcoveryAssets/levels/Map1.png";
		public static final String LEVEL_04 = "disvcoveryAssets/levels/Map2.png";
		public static final String LEVEL_05 = "disvcoveryAssets/levels/Map3.png";
		 
		// game properties
		 public static final int LIVES_START = 1;
		public static final String GAMEFONTS = "fonts/funfont.fnt";
		public static final float ITEM_BAD_APPLE_SPEEDING_UP_DURATION = 5;
		public static final float TIME_DELAY_GAME_OVER = 3;
		public static final float TIMER_GENERATE_NEW_MAP = 20;
		public static final int NUM_MAP = 4;
		
		// Sound and music 
		public static final String BACKGROUNDMUSIC1 = "disvcoveryAssets/sounds/backgroundmusic1.mp3";
		public static final String BACKGROUNDMUSIC2 = "disvcoveryAssets/sounds/backgroundmusic2.mp3";
		
		public static final String START_BUTTON_SOUND = "disvcoveryAssets/sounds/exitbutton.wav";
		public static final String EXIT_BUTTON_SOUND = "disvcoveryAssets/sounds/exitbutton.wav";
		public static final String JUMPING_SOUND = "disvcoveryAssets/sounds/jumping.wav";
		public static final String FALLING_SOUND = "disvcoveryAssets/sounds/weofalling.mp3";
		public static final String EATING_APPLE_SOUND = "disvcoveryAssets/sounds/appleBite.wav";
		public static final String EATING_BAD_APPLE_SOUND = "disvcoveryAssets/sounds/hurt.wav";
		
		
}
