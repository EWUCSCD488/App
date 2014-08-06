package com.spokanevalley.discoveryGame.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.spokanevalley.discoveryGame.Constants;

public class GameMusicSoundPref {

	private static GameMusicSoundPref gameOptionPref = null;
	public static final String TAG = GameMusicSoundPref.class.getName();
	
	public boolean sound = true;
	public boolean music = true;
	
	public float volSound;
	public float volMusic;
	
	private static final String MusicKey = "Music";
	private static final String SoundKey = "Sound";
	
	private Preferences prefs;
	
	public static GameMusicSoundPref create(){
		if(gameOptionPref == null)
			gameOptionPref = new GameMusicSoundPref();
		return gameOptionPref;
	}
	
	private GameMusicSoundPref() {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}

	public boolean loadMusic(){
		return prefs.getBoolean(MusicKey);
	}
	
	public boolean loadSound(){
		return prefs.getBoolean(SoundKey);
	}
	
	public void initialSave(){
		if(!prefs.contains(MusicKey))
			prefs.putBoolean(MusicKey, music);
		if(!prefs.contains(SoundKey))
		prefs.putBoolean(MusicKey, sound);
		prefs.flush();
	}
	
	public void save(boolean isCheckedMusic,boolean isCheckedSound){
		prefs.putBoolean(MusicKey, isCheckedMusic);
		prefs.putBoolean(MusicKey, isCheckedSound);
		prefs.flush();
	}
}
