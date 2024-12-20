package com.chidemgames.protectthesurvivors.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.chidemgames.protectthesurvivors.Constants;
import com.chidemgames.protectthesurvivors.factories.PhysicsFactory;

public class WallBox {

	private Body body;
	private World world;
	private int life;
	private float width, height;
	private WallBoxType type;
	private Vector2 position;
	private boolean isBox = true;
	private WallBoxState currentState = WallBoxState.NORMAL;
	
	public enum WallBoxType {
		STATIC,
		DINAMIC,
		KINEMATIC
	}
	
	public enum WallBoxState {
		DESTROIED,
		NORMAL
	}
	
	public WallBox(World world, int life, Vector2 position, float width, float height, WallBoxType type, boolean isBox){
		this.world = world;
		this.life = life;
		this.isBox = isBox;
		this.position = position;
		this.type = type;
		this.width = width;
		this.height = height;
		createPhysics();
	}
	
	public void createPhysics(){
		
		Shape wallShape;
		
		if (isBox){
			wallShape = PhysicsFactory.createBoxShape(width, height, new Vector2(0, 0), 0);
		} else {
			wallShape = PhysicsFactory.createChainShape(
					new Vector2[] {
						new Vector2(0, height/2),
						new Vector2(width, height),
						new Vector2(width, height/2),
						new Vector2(0, 0),
						new Vector2(0, height/2)
					}
			);
		}
		
		FixtureDef fixWall = PhysicsFactory.createFixture(wallShape, 0.2f, 0.1f, 0, false);
		
		fixWall.filter.categoryBits = Constants.CATEGORY_WALLBOX;
		fixWall.filter.maskBits = Constants.MASK_WALLBOX;
		BodyType bodyType;
		
		switch (type){
			case DINAMIC:
				bodyType = BodyType.DynamicBody;
				break;
			case STATIC:
				bodyType = BodyType.StaticBody;
				break;
			case KINEMATIC:
				bodyType = BodyType.KinematicBody;
				break;
			default:
				bodyType = BodyType.StaticBody;
				break;
		}
		
		body = PhysicsFactory.createBody(world, bodyType, fixWall, position);
		body.setUserData(this);
		
	}

	public void decreaseLife(float dano){
		if (this.life - dano < 0 ){
			this.life = 0;
		} else {
			this.life -= dano;
		}
	}
	
	public void onUpdate(){
		
		switch (currentState) {
			case DESTROIED:
				for (Fixture fix: body.getFixtureList()){
					this.body.destroyFixture(fix);
				}
				break;
	
			default:
				break;
		}
		
		if (life == 0){
			currentState = WallBoxState.DESTROIED;
		}
		
	}
	
}
