/**
 * 
 */
package com.spokanevalley.farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
/**
 * @author akozlov
 *
 */
public class Player{

	private static final long serialVersionUID = 1L;
	Vector2 position;
	Texture mario, flower, pipe;
	Sound squashSound;
	Vector2 hole0, hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8;
	
	public Player(Vector2 position, String textureLoc, String squashSound, String retroFlower, String pipe){
		this.position = position;
		this.mario = new Texture(Gdx.files.internal(textureLoc));
		this.squashSound = Gdx.audio.newSound(Gdx.files.internal(squashSound));
		this.flower = new Texture(Gdx.files.internal(retroFlower));
		this.pipe = new Texture(Gdx.files.internal(pipe));
		setHoles6();
	}
	
	public int [] update(int [] holes){//first row of 3
		if(Gdx.input.getX() >= (hole0.x-100) && Gdx.input.getX() <= (hole0.x+180) 
				&& Gdx.input.getY() >= (hole0.y-100) && Gdx.input.getY() <= (hole0.y+100)
				&& (holes[0] == 1 || holes[0] == 2)){
			holes = doScore(0, holes);
		}
		else if(Gdx.input.getX() >= (hole1.x-100) && Gdx.input.getX() <= (hole1.x+180) 
				&& Gdx.input.getY() >= (hole1.y-100) && Gdx.input.getY() <= (hole1.y+100)
				&& (holes[1] == 1 || holes[1] == 2)){
			holes = doScore(1, holes);
		}
		else if(Gdx.input.getX() >= (hole2.x-100) && Gdx.input.getX() <= (hole2.x+180) 
				&& Gdx.input.getY() >= (hole2.y-100) && Gdx.input.getY() <= (hole2.y+100)
				&& (holes[2] == 1 || holes[2] == 2)){
			holes = doScore(2, holes);
		}//second row of three
		else if(Gdx.input.getX() >= (hole3.x-100) && Gdx.input.getX() <= (hole3.x+180) 
				&& Gdx.input.getY() >= (hole3.y-100) && Gdx.input.getY() <= (hole3.y+100)
				&& (holes[3] == 1 || holes[3] == 2)){
			holes = doScore(3, holes);
		}
		else if(Gdx.input.getX() >= (hole4.x-100) && Gdx.input.getX() <= (hole4.x+180) 
				&& Gdx.input.getY() >= (hole4.y-100) && Gdx.input.getY() <= (hole4.y+100)
				&& (holes[4] == 1 || holes[4] == 2)){
			holes = doScore(4, holes);
		}
		else if(Gdx.input.getX() >= (hole5.x-100) && Gdx.input.getX() <= (hole5.x+180) 
				&& Gdx.input.getY() >= (hole5.y-100) && Gdx.input.getY() <= (hole5.y+100)
				&& (holes[5] == 1 || holes[5] == 2)){
			holes = doScore(5, holes);
		}//third row of three
		else if(Gdx.input.getX() >= (hole6.x-100) && Gdx.input.getX() <= (hole6.x+180) 
				&& Gdx.input.getY() >= (hole6.y-100) && Gdx.input.getY() <= (hole6.y+100)
				&& (holes[6] == 1 || holes[6] == 2)){
			holes = doScore(6, holes);
		}
		else if(Gdx.input.getX() >= (hole7.x-100) && Gdx.input.getX() <= (hole7.x+180) 
				&& Gdx.input.getY() >= (hole7.y-100) && Gdx.input.getY() <= (hole7.y+100)
				&& (holes[7] == 1 || holes[7] == 2)){
			holes = doScore(7, holes);
		}
		else if(Gdx.input.getX() >= (hole8.x-100) && Gdx.input.getX() <= (hole8.x+180) 
				&& Gdx.input.getY() >= (hole8.y-100) && Gdx.input.getY() <= (hole8.y+100)
				&& (holes[8] == 1 || holes[8] == 2)){
			holes = doScore(8, holes);
		}
		
		//For Testing
		if(Gdx.input.isTouched()){
			System.out.println("X:      " + Gdx.input.getX() + "          Y: " + Gdx.input.getY());
			System.out.println("hole0X: " + hole0.x + "     hole0Y: " + hole0.y);
			System.out.println("--------------------------------------------------------------------");
		}
		
		return holes;
	}
	
	public Vector2 getPosition(){
		return position;
	}
	
	public void setPosition(Vector2 position){
		this.position = position;
	}

	public Texture getTextureMario(){
		return this.mario;
	}
	
	public Texture getTextureFlower(){
		return this.flower;
	}
	
	public Texture getTextureHole(){
		return this.pipe;
	}
	
	private int [] doScore(int arraySpot, int [] holes){
		if(holes[arraySpot] == 1)
			holes[holes.length-1] += 1;
		else
		{
			holes[holes.length -2] -= 1;
		}
		holes[arraySpot] = 0;
		squashSound.play();
		return holes;
	}
	
	public void setHoles6(){
		hole0 = new Vector2(position.x, (position.y)*3);
		hole1 = new Vector2((position.x)*2, (position.y)*3);
		hole2 = new Vector2((position.x)*3, (position.y)*3);
		hole3 = new Vector2(position.x, (position.y)*2);
		hole4 = new Vector2((position.x)*2, (position.y)*2);
		hole5 = new Vector2((position.x)*3, (position.y)*2);
		hole6 = new Vector2(position.x, position.y);
		hole7 = new Vector2((position.x)*2, position.y);
		hole8 = new Vector2((position.x)*3, position.y);
	}
}
