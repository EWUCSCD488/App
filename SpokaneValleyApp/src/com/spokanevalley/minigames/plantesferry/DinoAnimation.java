package com.spokanevalley.minigames.plantesferry;

/*
 * @author Kevin Borling
 * Animation Class for Swimming Dinosaur
 */



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class DinoAnimation implements ApplicationListener {
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0;
    private Animation swimUpAnimation, swimDownAnimation, swimAnimation;
    @Override
    public void create() {        
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("assets/gfx/dinoanimation.atlas"));
        //animation = new Animation(1/15f, textureAtlas.getRegions()); // 15 FPS
        
        this.setSwimUpAnimation(new Animation(0.1f,
                (textureAtlas.findRegion("waterdino")),
                (textureAtlas.findRegion("waterdino1")),
                (textureAtlas.findRegion("waterdino2")),
                (textureAtlas.findRegion("waterdino3")),
                (textureAtlas.findRegion("waterdino4"))));

        this.setSwimDownAnimation(new Animation(0.1f,
                (textureAtlas.findRegion("waterdino5")),
                (textureAtlas.findRegion("waterdino6")),
                (textureAtlas.findRegion("waterdino7"))));
        
        this.setSwimAnimation(new Animation(0.1f,
        		(textureAtlas.findRegion("waterdino")),
                (textureAtlas.findRegion("waterdino1")),
                (textureAtlas.findRegion("waterdino2")),
                (textureAtlas.findRegion("waterdino3")),
                (textureAtlas.findRegion("waterdino4")),
                (textureAtlas.findRegion("waterdino5")),
                (textureAtlas.findRegion("waterdino6")),
                (textureAtlas.findRegion("waterdino7"))));
        

       //Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    @Override
    public void render() {        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        setElapsedTime(getElapsedTime() + Gdx.graphics.getDeltaTime());
        batch.draw(animation.getKeyFrame(getElapsedTime(), true), 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

	public float getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Animation getSwimUpAnimation() {
		return swimUpAnimation;
	}

	public void setSwimUpAnimation(Animation swimUpAnimation) {
		this.swimUpAnimation = swimUpAnimation;
	}

	public Animation getSwimDownAnimation() {
		return swimDownAnimation;
	}

	public void setSwimDownAnimation(Animation swimDownAnimation) {
		this.swimDownAnimation = swimDownAnimation;
	}

	public Animation getSwimAnimation() {
		return swimAnimation;
	}

	public void setSwimAnimation(Animation swimAnimation) {
		this.swimAnimation = swimAnimation;
	}
}
