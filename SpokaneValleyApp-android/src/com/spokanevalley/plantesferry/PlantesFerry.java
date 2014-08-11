package com.spokanevalley.plantesferry;

import java.util.Iterator;

import android.content.Context;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.spokanevalley.database.DatabaseCustomAccess;

/*
 * @author Kevin Borling
 * Main Game class of Plante's Ferry
 * Sets the Scrolling Background from ScrollingBg class
 * Sets the boundaries and the three rows used for spawning and player movement
 * Creates user controlled Swimming Dinosaur from SwimmingDino class
 * Creates Monster from Monster class
 * Creates Apple/Bubble from Apples class
 * Adds the created objects into individual Array Lists which are continuously iterated throughout the game
 */
public class PlantesFerry extends Table {

	protected SwimmingDino playerDino;
	protected ScrollingBg river;
	private Context context;
	private long lastMonsterSpawnTime = 0L;
	private long lastAppleSpawnTime = 0L;
	private Array<Monster> monsters;
	private Array<Apples> apples;
	private Apples localApple;
	private Monster localRiverMonster;
	/* Screen Size and Row Values */
	protected final float row1 = 90.0F;
	protected final float row2 = 240.0F;
	protected final float row3 = 390.0F;
	protected final float screenWidth = 800.0F;
	protected final float screenHeight = 480.0F;

	private int score;
	private int lives;
	/* Invinsibility Stats Declarations */
	private boolean isInvinsible = false;
	private long isInvinsibleTime = 0L;

	/*
	 * Constructor for PlantesFerry Game Sets the bounds, and enable content
	 * clipping Adds the river, player, and array of Monsters Initializes score
	 * and life count
	 */
	public PlantesFerry(Context context) {
		this.context = context;
		this.setBounds(0.0F, 0.0F, this.screenWidth, this.screenHeight);
		this.setClip(true);
		// Character and Background
		this.river = new ScrollingBg(getWidth(), getHeight());
		addActor(this.river);
		this.playerDino = new SwimmingDino(this);
		addActor(this.playerDino);
		// Monster and Apple ArrayList
		this.monsters = new Array<Monster>();
		this.apples = new Array<Apples>();
		// Initialize score and lives
		this.setScore(0);
		this.setLives(3);

		DatabaseCustomAccess.Create(this.context)
				.saveInitialScoretoDatabase_PlantesFerryGame(0);
	} // End PlantesFerry Constructor

	/*
	 * Spawns a River Monster in one of the three rows randomly.
	 */
	private void spawnRiverMonster() {
		int rand = MathUtils.random(0, 2);
		float row = 0.0F;
		if (rand == 0)
			row = this.row1;
		if (rand == 1)
			row = this.row2;
		if (rand == 2)
			row = this.row3;

		this.localRiverMonster = new Monster(getWidth(), row, this);
		this.monsters.add(localRiverMonster);
		addActor(localRiverMonster);
		this.lastMonsterSpawnTime = TimeUtils.nanoTime();
	} // End spawnRiverMonster

	/*
	 * Spawns an Apple in one of the three rows randomly.
	 */
	private void spawnApple() {
		int rand = MathUtils.random(0, 2);
		float row = 0.0F;
		if (rand == 0)
			row = this.row1;
		if (rand == 1)
			row = this.row2;
		if (rand == 2)
			row = this.row3;

		this.localApple = new Apples(getWidth(), row, this);
		this.apples.add(localApple);
		addActor(localApple);
		this.lastAppleSpawnTime = TimeUtils.nanoTime();
	} // End spawnApple

	/*
	 * Iterate through the Arraylists of Monsters and Apples. Remove
	 * Monsters/Apples that leave the rectangle bounds. Check for collision
	 * between player, monster, and apple.
	 */
	public void act(float paramFloat) {
		super.act(paramFloat);

		// Change monster spawn time here : 0.75E = current time
		if ((float) (TimeUtils.nanoTime() - this.lastMonsterSpawnTime) > 0.89E+009F)
			spawnRiverMonster();
		// Change apple spawn time here : 0.45E = current time
		if ((float) (TimeUtils.nanoTime() - this.lastAppleSpawnTime) > 0.45E+009F)
			spawnApple();
		// Change length of invinsibility here: 10.0E = current time
		if ((float) (TimeUtils.nanoTime() - this.isInvinsibleTime) > 10.0E+009F) {
			setInvinsible(false);
			setInvinsibleTime(0L);
		}

		Iterator<Monster> monsterIterator = this.monsters.iterator();
		Iterator<Apples> appleIterator = this.apples.iterator();

		// Iterate and handle collisions / out of bound elements
		for (;;) {
			if (!appleIterator.hasNext())
				return;
			if (!monsterIterator.hasNext())
				return;

			Monster localRiverMonster = (Monster) monsterIterator.next();
			Apples localApple = (Apples) appleIterator.next();

			// Check bounds
			appleBoundsCheck(localApple, appleIterator);
			monsterBoundsCheck(localRiverMonster, monsterIterator);
			// Check collision
			appleCollisionCheck(localApple, appleIterator);
			monsterCollisionCheck(localRiverMonster, monsterIterator);

		} // End continuous for loop
	} // End act

	/*
	 * Checks collision between monster and player. Upon collision, the monster
	 * is removed.
	 */
	private void monsterCollisionCheck(Monster localRiverMonster,
			Iterator<Monster> monsterIterator) {
		if (localRiverMonster.getBounds().overlaps(this.playerDino.getBounds())) {

			monsterIterator.remove();

			if (localRiverMonster.getX() > this.playerDino.getX()) {

				if (localRiverMonster.getY() > this.playerDino.getY()) {
					localRiverMonster.collision(true, true);
					this.playerDino.collision(true, true);
				} else {
					localRiverMonster.collision(true, false);
					this.playerDino.collision(true, false);
				}
			} else if (localRiverMonster.getY() > this.playerDino.getY()) {
				localRiverMonster.collision(false, true);
				this.playerDino.collision(false, true);
			} else {
				localRiverMonster.collision(false, false);
				this.playerDino.collision(false, false);
			}
		} // End if collision check
	} // End collisionCheck

	/*
	 * Checks if a monster is outside of the set rectangle bounds. Upon being
	 * out of bounds, the monster is removed.
	 */
	private void monsterBoundsCheck(Monster localRiverMonster,
			Iterator<Monster> monsterIterator) {

		if (localRiverMonster.getBounds().x + localRiverMonster.getWidth() < 0.1F) {
			monsterIterator.remove();
			removeActor(localRiverMonster);
		}

	} // End boundsCheck

	/*
	 * Checks collision between apple and player. Upon collision, the apple is
	 * removed.
	 */
	private void appleCollisionCheck(Apples localApple,
			Iterator<Apples> appleIterator) {
		if (localApple.getBounds().overlaps(this.playerDino.getBounds())) {
			appleIterator.remove();
			localApple.collision(true, true);
		} // End if collision check

	} // End collisionCheck

	/*
	 * Checks if a apple is outside of the set rectangle bounds. Upon being out
	 * of bounds, the apple is removed.
	 */
	private void appleBoundsCheck(Apples localApple,
			Iterator<Apples> appleIterator) {
		if (localApple.getBounds().x + localApple.getWidth() < 0.1F) {
			appleIterator.remove();
			removeActor(localApple);
		}
	} // End boundsCheck
	
	/*
	 * Called when Game Over is reached.
	 * Removes all actors from screen and clears the Array of apples and monsters
	 */
	public void removeAllActors() {
		for(int x = 0; x < this.apples.size; x++) {
			removeActor(this.apples.get(x));
		} // Remove apples
		for(int x = 0; x < this.monsters.size; x++) {
			removeActor(this.monsters.get(x));
		} // Remove apples
		this.apples.clear();
		this.monsters.clear();
	} // End removeAllActors
	
	/*
	 * Draws the in game sprites
	 */
	public void draw(SpriteBatch paramSpriteBatch, float paramFloat) {
		paramSpriteBatch.setColor(Color.WHITE);
		super.draw(paramSpriteBatch, paramFloat);
	} // End draw

	public int getScore() {
		return this.score;
	} // End getScore

	public void setScore(int score) {
		this.score = score;
	} // End setScore

	public int getLives() {
		return this.lives;
	} // End getLives

	public void setLives(int lives) {
		this.lives = lives;
	} // End setLives

	public boolean isInvinsible() {
		return this.isInvinsible;
	} // End isInvinsible

	public void setInvinsible(boolean isInvinsible) {
		this.isInvinsible = isInvinsible;
	} // End setInvinsible

	public long getInvinsibleTime() {
		return this.isInvinsibleTime;
	} // End getInvinsibleTime

	public void setInvinsibleTime(long isInvinsibleTime) {
		this.isInvinsibleTime = isInvinsibleTime;
	} // End setInvinsibleTime

} // End PlantesFerry class
