package com.chidemgames.protectthesurvivors.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.chidemgames.protectthesurvivors.Constants;
import com.chidemgames.protectthesurvivors.factories.PhysicsFactory;

public class Bullet {
	
	public Body body;
	public Tower tower;
	private World world;
	private boolean inWeapon = true;
	public boolean isActive = true;
	public BulletState currentState;
	
	public enum BulletState {
		MOVING,
		DESTROYED
	}

	public void setInWeapon(boolean inWeapon){
		this.inWeapon = inWeapon;
	}
	
	public boolean inWeapon(){
		return this.inWeapon;
	}
	
	public Bullet(World world, Tower tower, Vector2 position, float angle){
		this.tower = tower;
		this.world = world;
		createPhysics(position, angle);
	}
	
	public void createPhysics(Vector2 pos, float angle){
		
		Shape bulletShape = PhysicsFactory.createBoxShape(0.08f, 0.08f, new Vector2(0, 0), angle);
		FixtureDef fixBullet = PhysicsFactory.createFixture(bulletShape, 0.1f, 0.2f, 0.4f, true);
		fixBullet.filter.categoryBits = Constants.CATEGORY_BULLET;
		fixBullet.filter.maskBits = Constants.MASK_BULLET;
		fixBullet.filter.groupIndex = Constants.GROUP_BULLET;
		
		body = PhysicsFactory.createBody(world, BodyType.DynamicBody, fixBullet, pos);
		body.setUserData(this);
		
	}
	
	public void setTransform(Vector2 pos, float angle){
		this.body.setTransform(pos, angle);
	}
	
	public void onUpdate(){
		if (!inWeapon && body.getLinearVelocity().x == 0){
			if (body.getFixtureList().size > 0){
				body.destroyFixture(body.getFixtureList().get(0));
			}		
		}
		if (!isActive){
			if (body.getFixtureList().size > 0){
				body.destroyFixture(body.getFixtureList().get(0));
			}
		}
	}
}
