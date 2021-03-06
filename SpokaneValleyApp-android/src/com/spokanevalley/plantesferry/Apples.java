package com.spokanevalley.plantesferry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;

/*
 * @author Kevin Borling
 * Creates the Apple/Bubble
 * Apples grant 1 point per collision
 * Bubbles grant invincibility for 10 seconds and grants 3 points for collision with apple or monster
 * Invincibility will make the Swimming Dinosaur immune to monsters, therefore the life count will not decrease upon collision 
 */
public class Apples extends Actor {

	private PlantesFerry game;

	private Rectangle bounds = new Rectangle();
	private final String invinsibleBubble = "Bubble";

	/*
	 * Creates a Apple object
	 */
	public Apples(float paramFloat1, float paramFloat2, PlantesFerry game) {
		this.game = game;
		setWidth(Assets.apple.getRegionWidth());
		setHeight(Assets.apple.getRegionHeight());
		setPosition(paramFloat1, paramFloat2 - getHeight() / 2.0F);

		int rand = MathUtils.random(0, 50);
		if (rand == 50)
			setName(invinsibleBubble);

		addAction(Actions.moveTo(-getWidth(), getY(),
				MathUtils.random(3.0F, 5.0F)));
	}// End CheckPoint Constructor

	/*
	 * Sets bounds in which checkpoints can be spawned.
	 */
	private void updateBounds() {
		this.bounds.set(getX(), getY(), getWidth(), getHeight());
	} // End updateBounds

	public void act(float timeSeconds) {
		super.act(timeSeconds);
		updateBounds();
	} // End act

	/*
	 * Upon collision, Apple adds one point to total score, bubble adds three
	 * points to the total score. Upon collision with bubble, the player becomes
	 * invinsible for 10 seconds, allowing collision with monster without any
	 * harm to player.
	 */
	public void collision(boolean paramBoolean1, boolean paramBoolean2) {
		clearActions();
		int localScore = game.getScore();

		if (getName() == invinsibleBubble) {
			Assets.invinsibleBubbleleSound.stop();
			Assets.invinsibleBubbleleSound.play();
			game.setInvinsible(true);
			game.setInvinsibleTime(TimeUtils.nanoTime());
			localScore += 3;
		} // End if
		else {
			Assets.appleSound.stop();
			Assets.appleSound.play();
			localScore++;
		} // End else
		game.setScore(localScore);

		addAction(Actions.fadeOut(0.1F));
		Actions.removeActor();

	} // End collision

	/*
	 * Draws the Apple or Rare Invinsibility Bubble object.
	 */
	public void draw(SpriteBatch paramSpriteBatch, float paramFloat) {
		paramSpriteBatch.setColor(getColor().r, getColor().g, getColor().b,
				getColor().a);
		if (getName() == invinsibleBubble)
			paramSpriteBatch.draw(Assets.bubble, getX(), getY(),
					getWidth() / 2.0F, getHeight() / 2.0F, getWidth(),
					getHeight(), 1.0F, 1.0F, getRotation());
		else
			paramSpriteBatch.draw(Assets.apple, getX(), getY(),
					getWidth() / 2.0F, getHeight() / 2.0F, getWidth(),
					getHeight(), 1.0F, 1.0F, getRotation());
	} // End draw

	public Rectangle getBounds() {
		return this.bounds;
	} // End getBounds
}
