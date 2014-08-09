package com.spokanevalley.bankStore;
import com.spokanevalley.app.R;

import android.content.Context;
import android.media.MediaPlayer;

public class ButtonSoundFactory {
	
	private MediaPlayer playsound1;
	private MediaPlayer playsound2;
	private MediaPlayer playsound3;
	private MediaPlayer playsound4;
	private MediaPlayer playsound5;
	private MediaPlayer playsound6;
	private MediaPlayer playsound7;
	private MediaPlayer playbackground;
	
	public ButtonSoundFactory(Context context) {
		playsound1 = MediaPlayer.create(context, R.raw.menubutton);
		playsound2 = MediaPlayer.create(context, R.raw.menubutton);
		playsound3 = MediaPlayer.create(context, R.raw.menubutton);
		playsound4 = MediaPlayer.create(context, R.raw.menubutton);
		playsound5 = MediaPlayer.create(context, R.raw.menubutton);
		playsound6 = MediaPlayer.create(context, R.raw.menubutton);
		playsound7 = MediaPlayer.create(context, R.raw.menubutton);
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
	
	public void playsound4(){
		playsound4.start();
	}
	
	public void playsound5(){
		playsound5.start();
	}
	
	public void playsound6(){
		playsound6.start();
	}
	
	public void playsound7(){
		playsound7.start();
	}
	

}
