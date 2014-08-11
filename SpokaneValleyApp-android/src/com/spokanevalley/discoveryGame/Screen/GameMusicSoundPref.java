package com.spokanevalley.discoveryGame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.google.android.gms.internal.ex;
import com.spokanevalley.discoveryGame.Constants;

/**
 * Using libgdx database into handle sound in game
 * 
 * @author Quyen Ha
 *
 */

public class GameMusicSoundPref {

	private static GameMusicSoundPref gameOptionPref = null;
	public static final String TAG = GameMusicSoundPref.class.getName();
	
	private boolean isMutedMusic = true; // load from Libgdx database to check
			// for music on or off
	private boolean isMutedSound = true; // load from Libgdx database to check
	// for sound on or off
	
	private static final String MusicKey = "Music";
	private static final String SoundKey = "Sound";
	
	private static boolean isCleaned = false;
	
	private static Music backgroundMusic1 = null;
	private static Music backgroundMusic2 = null;
	private static Sound playButton;
	private static Sound exitButton;
	private static Sound jumping;
	private static Sound falling;
	private static Sound eatingApple;
	private static Sound eatingBadApple;
	
	
	private static Preferences prefs;				// prevent duplicate 
	
	public static GameMusicSoundPref create(){
		if(gameOptionPref == null)
			gameOptionPref = new GameMusicSoundPref();
		if(isCleaned){
			createSound();
			isCleaned = false;
		}
		
		return gameOptionPref;
	}
	
	/**
	 * load values from database
	 * if values are not in database, initialize them
	 */
	
	private GameMusicSoundPref() {
		prefs = null;
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);	
		initialSave();
		createSound();
	}

	/**
	 * check if music is muted or not
	 * @return
	 */
	
	public boolean getMusic(){
		return isMutedMusic;
	}
	
	
	/**
	 * check if sound is muted or not
	 * @return
	 */
	public boolean getSound(){
		return isMutedSound;
	}
	
	/**
	 * after users click on music , it will determine music is muted or not
	 */
	
	public void MusicLogicChange(){
		isMutedMusic = !isMutedMusic;
		prefs.putBoolean(MusicKey, isMutedMusic);
		playbackground1();
	}
	
	/**
	 * after users click on sound , it will determine sound is muted or not
	 */
	
	public void SOundLogicChange(){
		isMutedSound = !isMutedSound;
		prefs.putBoolean(SoundKey, isMutedSound);
	}
	
	/**
	 * check and initialize database if values are not in database
	 */
	
	public void initialSave(){
		if(!prefs.contains(MusicKey))
			prefs.putBoolean(MusicKey, isMutedMusic);
		else{
			prefs.getBoolean(MusicKey);
		}
		if(!prefs.contains(SoundKey))
			prefs.putBoolean(SoundKey, isMutedSound);
		else
			isMutedSound = prefs.getBoolean(SoundKey);
		
		prefs.flush();			// call this to make sure changes are applied to database
	}
	
	/**
	 * dispose all sound objects for memory hanndling
	 */
	
	public void disposeSound(){
		backgroundMusic1.dispose();
		backgroundMusic2.dispose();
		playButton.dispose();
		exitButton.dispose();
		jumping.dispose();
		falling.dispose();
		eatingApple.dispose();
		eatingBadApple.dispose();
		isCleaned = true;
		//gameOptionPref = null;
	}
	
	/**
	 * create sounds
	 */
	
	private static void createSound()
	{
		// load the apple sound effect
		backgroundMusic1 = Gdx.audio.newMusic(Gdx.files.internal(Constants.BACKGROUNDMUSIC1));
		backgroundMusic2	= Gdx.audio.newMusic(Gdx.files.internal(Constants.BACKGROUNDMUSIC2));;
		playButton 	= Gdx.audio.newSound(Gdx.files.internal(Constants.START_BUTTON_SOUND));
		exitButton 	= Gdx.audio.newSound(Gdx.files.internal(Constants.EXIT_BUTTON_SOUND));
		jumping 	= Gdx.audio.newSound(Gdx.files.internal(Constants.JUMPING_SOUND));
		falling 	= Gdx.audio.newSound(Gdx.files.internal(Constants.FALLING_SOUND));
		eatingApple = Gdx.audio.newSound(Gdx.files.internal(Constants.EATING_APPLE_SOUND));
		eatingBadApple = Gdx.audio.newSound(Gdx.files.internal(Constants.EATING_BAD_APPLE_SOUND));
	}
	
	/**
	 * play  menu background music
	 */
	
	public void playbackground1(){
		if(backgroundMusic1 != null && backgroundMusic2 !=null){
			if(isMutedMusic == true){
					if(!backgroundMusic1.isPlaying()){
						backgroundMusic2.stop();
						backgroundMusic1.setLooping(true);
						backgroundMusic1.play();
					}
				}
			else{
				backgroundMusic1.stop();
				backgroundMusic2.stop();
			}
		}
	}
	
	/**
	 * play background in-game music
	 */
	
	public void playbackground2(){
		if(backgroundMusic1 != null &&  backgroundMusic2 !=null){
			if(isMutedMusic){
				if(!backgroundMusic2.isPlaying()){
					backgroundMusic1.stop();
					backgroundMusic2.setLooping(true);
					//backgroundMusic2.setVolume(0.25f);
					backgroundMusic2.play();
				}
			}else{
				backgroundMusic1.stop();
				backgroundMusic2.stop();
			}
		}
	}
	
	/**
	 *  play start button sound
	 */
	
	public void playStartButton(){
		if(isMutedSound)
		playButton.play();
	}
	
	/**
	 * play exit button sound
	 */
	
	public void playExitButton(){
		if(isMutedSound)
		exitButton.play();
	}
	
	/**
	 * play sound when dinosaur is jumping
	 */
	
	public void playJumping(){
		if(isMutedSound)
		jumping.play();
	}
	
	/**
	 * play sound when dinosaur is falling
	 */
	
	public void playFalling(){
		if(isMutedSound){
			//backgroundMusic1.stop();
			//backgroundMusic2.stop();
			falling.play();
		}
	}
	
	/**
	 * play sound when dinosaur is eating apple
	 */
	
	public void playEatingApple(){
		if(isMutedSound)
		eatingApple.play();
	}
	
	/**
	 * play sound when dinosaur is eating bad apple
	 */
	
	public void playEatingBadApple(){
		if(isMutedSound)
		eatingBadApple.play();
	}
	
	/**
	 * stop background music 1
	 */
	
	public void stopBackground1(){
		if(backgroundMusic1 != null)
		backgroundMusic1.stop();
	}
	
	/**
	 * stop background music 2
	 */
	
	public void stopBackground2(){
		if(backgroundMusic2 != null)
		backgroundMusic2.stop();
	}
	
	/**
	 * get jumping sound
	 * @return
	 */
	
	public Sound getJumping(){
		
		return jumping;
	}
	
}
