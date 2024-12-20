package com.chidemgames.protectthesurvivors.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends Sprite {
	
	private Body body;
	
	private World world;
	
	private Vector2 position;
	
	private float width, height;
	
	public float getWidth() {
		return width;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Platform(World world, Vector2 position, float width, float height){
		this.world = world;
		this.width = width;
		this.height = height;
		this.position = position;
		createPhysics();
	}

	public void createPhysics(){
		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		def.position.set(this.position);
		def.fixedRotation = true;
		
		this.body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		FixtureDef fix = new FixtureDef();
		fix.shape = shape;
		fix.density = 1;
		fix.restitution = 0.5f;
		fix.friction = 0;
		
		body.createFixture(fix);
		shape.dispose();
	}
	
}
