package com.chidemgames.protectthesurvivors.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.chidemgames.protectthesurvivors.Constants;
import com.chidemgames.protectthesurvivors.factories.PhysicsFactory;

public class Survivor {

	public Body body;
	private World world;
	public int life;
	private Vector2 position;
	private float width, height, radius;
	private SurvivorState currentState;
	private Sprite sprite;
	
	public enum SurvivorState {
		OUT_OF_BASE,
		DIED,
		IN_BASE
	}
	
	public Survivor(World world, int life, Vector2 position, float radius){
		this.world = world;
		this.radius = radius;
		this.position = position;
		this.life = life;
		this.currentState = SurvivorState.IN_BASE;
		createPhysics();
		
		sprite = new Sprite(new Texture("survivor-marca2.png"));
		sprite.setBounds(0, 0, 3f * radius, 3f * radius);
		
	}
	
	public void createPhysics(){
		
		Shape survivorShape = PhysicsFactory.createCircleShape(radius);
		FixtureDef fixSurvivor = PhysicsFactory.createFixture(survivorShape, 0.8f, 0.3f, 0, false);
		
		fixSurvivor.filter.categoryBits = Constants.CATEGORY_SURVIVOR;
		fixSurvivor.filter.maskBits = Constants.MASK_SURVIVOR;
		
		body = PhysicsFactory.createBody(world, BodyType.DynamicBody, fixSurvivor, position);
		body.setUserData(this);
		
	}
	
	public void renderSprite(SpriteBatch batch){
		if (life > 0){
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
			sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
			sprite.setRotation((float) Math.toDegrees(body.getAngle()));
			sprite.draw(batch);
		}
	}
	
	
	public void onUpdate(){
		
		if (life == 0){
			currentState = SurvivorState.DIED;
		}
		
		switch (currentState){
			case IN_BASE:
				
				break;
			case OUT_OF_BASE:
				
				break;
			case DIED:
				for (Fixture fix: body.getFixtureList()){
					this.body.destroyFixture(fix);
				}
				break;
		}
		
	}
	
	public void decreaseLife(float dano){
		if (this.life - dano < 0 ){
			this.life = 0;
		} else {
			this.life -= dano;
		}
	}

}
