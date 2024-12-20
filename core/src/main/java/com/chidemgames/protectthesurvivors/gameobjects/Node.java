package com.chidemgames.protectthesurvivors.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Node  {

	private int ID;
	
	private Vector2 position;
	
	private World world;
	
	private Body body;
	
	private boolean isLastNode = false;
	
	private int posInLayer;
	
	public int camada;
	
	public Node(World world, Vector2 position){
		this.world = world;
		this.position = position;
		this.camada = 1;
		createPhysics();
	}
	
	public void createPhysics(){
		
		BodyDef def = new BodyDef();
		def.position.set(this.position);
		def.fixedRotation = true;
		def.type = BodyType.StaticBody;
		
		this.body = world.createBody(def);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(0.2f);
		
		FixtureDef fix = new FixtureDef();
		fix.shape = circle;
		fix.density = 0.2f;
		fix.restitution = 0.0f;
		fix.friction = 0.1f;
		fix.isSensor = true;
		
		this.body.createFixture(fix);
		
		circle.dispose();
		
	}
	
	public int getId(){
		return this.ID;
	}
	
	public void setId(int id){
		this.ID = id;
	}
	
	public boolean isLastNode() {
		return isLastNode;
	}

	public void setLastNode(boolean isLastNode) {
		this.isLastNode = isLastNode;
	}

	public int getPosInLayer() {
		return posInLayer;
	}

	public void setPosInLayer(int posInLayer) {
		this.posInLayer = posInLayer;
	}

	public Vector2 getPosition(){
		return this.position;
	}
	
	public Body getBody(){
		return this.body;
	}
	
	public int getLayer(){
		return this.camada;
	}
	
	public String toString(){
		return "[X: " + position.x + ", Y: " + position.y + ", C: " + camada + ", pos in l: " + posInLayer + "]";
	}

	
}
