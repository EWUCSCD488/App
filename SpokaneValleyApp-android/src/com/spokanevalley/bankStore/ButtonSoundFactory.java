package com.spokanevalley.bankStore;
import com.spokanevalley.app.R;

import android.content.Context;
import android.media.MediaPlayer;

public class ButtonSoundFactory {
	
	private MediaPlayer playsound1;
	private MediaPlayer playsound2;
	private MediaPlayer playsound3;
	private MediaPlayer playbackground;
	
	public ButtonSoundFactory(Context context) {
		playsound1 = MediaPlayer.create(context, R.raw.menubutton);
		playsound2 = MediaPlayer.create(context, R.raw.dialog);
		playsound3 = MediaPlayer.create(context, R.raw.getcoupon);
		playbackground = MediaPlayer.create(context, R.raw.background);
	}
	
	public void playBackground(){
		playbackground.setLooping(true);
		playbackground.start();
	}
	
	public void stopbackground(){
		playbackground.pause();
	}
	public void playsound1(){
		playsound1.start();
	}
	
	public void playsound2(){
		playsound2.start();
	}
	
	public void playsound3(){
		playsound3.start();
	}

}
