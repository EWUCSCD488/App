package com.spokanevalley.bankStore;
import com.spokanevalley.app.R;

import android.content.Context;
import android.media.MediaPlayer;
/**
 * @author Quyen Ha
 * Eastern Washington University
 * Responsible for maintaining and handling the sound effects when a user clicks on:
 * the info boxes, the icons, the buttons inside of the mall or bank.
 * Also plays the background music while on the main map
 */
public class ButtonSoundFactory {
	
	private MediaPlayer playsound1;
	private MediaPlayer playsound2;
	private MediaPlayer playsound3;
	private MediaPlayer playbackground;
	
	/**
	 * Loads the sounds from res/raw folder
	 * @param context
	 */
	public ButtonSoundFactory(Context context) {
		playsound1 = MediaPlayer.create(context, R.raw.menubutton);
		playsound2 = MediaPlayer.create(context, R.raw.dialog);
		playsound3 = MediaPlayer.create(context, R.raw.getcoupon);
		playbackground = MediaPlayer.create(context, R.raw.background);
	} // End ButtonSoundFactory
	
	/**
	 * Plays background music while on the main map
	 */
	public void playBackground(){
		playbackground.setLooping(true);
		playbackground.start();
	} // End playBackground
	
	/**
	 * Pauses the background music if user enters a select location
	 */
	public void stopbackground(){
		playbackground.pause();
	} // End stopbackground
	
	/**
	 * Plays the sound for each button, info box, and icons
	 */
	public void playsound1(){
		playsound1.start();
	} // End playsound1
	
	/**
	 * Plays the sound for dialog prompt
	 */
	public void playsound2(){
		playsound2.start();
	} // End playsound2
	
	/**
	 * Plays the sound when a user purchases a coupon
	 */
	public void playsound3(){
		playsound3.start();
	} // End playsound3

}
