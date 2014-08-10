package com.spokanevalley.minigames.plantesferry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
 * @author Kevin Borling
 * Loads/Disposes all game assets such as fonts, sound effects, and graphics
 */
public class Assets {
	/* Atlas/Texture Region Declarations */
	private static TextureAtlas spriteAtlas;
	private static TextureAtlas bgAtlas;
	protected static TextureAtlas skinAtlas;
	protected static TextureRegion playerDino;
	protected static TextureRegion monster;
	protected static TextureRegion rareMonster;
	protected static TextureRegion apple;
	protected static TextureRegion bubble;
	protected static TextureRegion underwaterbg;
	protected static TextureRegion pauseMenubg;
	protected static TextureRegion startMenubg;
	protected static TextureRegion gameoverMenubg;
	/* Music/Sound Effect Declarations */
	protected static Music backgroundMusic;
	protected static Sound buttonSound;
	protected static Sound appleSound;
	protected static Sound monsterSound;
	protected static Sound invinsibleSound;
	protected static Sound invinsibleBubbleleSound;
	/* Game Font Declarations */
	protected static BitmapFont scoreFont;
	protected static BitmapFont buttonFont;
	protected static BitmapFont labelFont;

	/*
	 * Load the image atlas, audio, score, and sprite images. Located in assets
	 * > plantesferryAssets
	 */
	public static void load() {
		/* Load Image Atlas */
		spriteAtlas = new TextureAtlas(
				Gdx.files.internal("plantesferryAssets/gfx/texture.atlas"));
		bgAtlas = new TextureAtlas(
				Gdx.files.internal("plantesferryAssets/gfx/bgtexture.atlas"));
		skinAtlas = new TextureAtlas(
				Gdx.files.internal("plantesferryAssets/gfx/button.pack"));
		/* Load Sound Effects */
		backgroundMusic = Gdx.audio.newMusic(Gdx.files
				.internal("plantesferryAssets/sfx/rainbowbunchie.mp3"));
		buttonSound = Gdx.audio.newSound(Gdx.files
				.internal("plantesferryAssets/sfx/menubutton.wav"));
		appleSound = Gdx.audio.newSound(Gdx.files
				.internal("plantesferryAssets/sfx/bite.wav"));
		invinsibleSound = Gdx.audio.newSound(Gdx.files
				.internal("plantesferryAssets/sfx/hurt.wav"));
		invinsibleBubbleleSound = Gdx.audio.newSound(Gdx.files
				.internal("plantesferryAssets/sfx/FX051.mp3"));
		monsterSound = Gdx.audio.newSound(Gdx.files
				.internal("plantesferryAssets/sfx/youch.wav"));

		/* Load Character / Background Sprites */
		underwaterbg = spriteAtlas.findRegion("underwater");
		startMenubg = bgAtlas.findRegion("startmenu");
		pauseMenubg = bgAtlas.findRegion("pausemenu");
		gameoverMenubg = bgAtlas.findRegion("gameovermenu");
		playerDino = spriteAtlas.findRegion("waterdino");
		monster = spriteAtlas.findRegion("seaurchin");
		rareMonster = spriteAtlas.findRegion("whalemonster");
		apple = spriteAtlas.findRegion("apple32");
		bubble = spriteAtlas.findRegion("bubble64");
		/* Load Game Font */
		scoreFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
				Gdx.files.internal("fonts/gamefont_0.png"), false);
		buttonFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
				Gdx.files.internal("fonts/gamefont_0.png"), false);
		labelFont = new BitmapFont(Gdx.files.internal("fonts/gamefont.fnt"),
				Gdx.files.internal("fonts/gamefont_0.png"), false);
	} // End load

	/*
	 * Dispose the current image atlas, skin atlas, font, and sound effects
	 */
	public static void dispose() {
		spriteAtlas.dispose();
		backgroundMusic.dispose();
		appleSound.dispose();
		invinsibleSound.dispose();
		monsterSound.dispose();
	} // End dispose

} // End Assets
