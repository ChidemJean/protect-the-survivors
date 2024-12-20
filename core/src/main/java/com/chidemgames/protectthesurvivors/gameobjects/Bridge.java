package com.chidemgames.protectthesurvivors.gameobjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.chidemgames.protectthesurvivors.Constants;
import com.chidemgames.protectthesurvivors.factories.PhysicsFactory;
import com.chidemgames.protectthesurvivors.managers.NodesManager;

public class Bridge {

	private int idNodeA;
	private int idNodeB;
	
	private float width;
	private float height;
	private float angle;
	
	private Body body;
	private World world;
	private OrthographicCamera camera;
	
	public Bridge(){}
	
	public Bridge(World world, OrthographicCamera camera, int idNodeA, int idNodeB){
		this.idNodeA = idNodeA;
		this.idNodeB = idNodeB;
		this.world = world;
		this.camera = camera;
		createPhysics();
	}
	
	public void createPhysics(){
		
		Vector2 posA = NodesManager.getInstance().getNode(idNodeA).getPosition();
		float radiusA = NodesManager.getInstance().getNode(idNodeA).getBody().getFixtureList().get(0).getShape().getRadius();
		
		Vector2 posB = NodesManager.getInstance().getNode(idNodeA).getPosition();
		float radiusB = NodesManager.getInstance().getNode(idNodeB).getBody().getFixtureList().get(0).getShape().getRadius();
		
		double ca = (double) posB.x - posA.x;
		double co = (double) posB.y - posA.y;
		
		double hipotenusa = Math.sqrt(Math.pow(ca, 2.0) + Math.pow(co, 2.0));
		
		angle = (float) Math.asin(co / hipotenusa);
		
		if (posB.x < posA.x){
			angle = (((float) Math.toRadians(180)) - (angle));
		}
		
		float mX = ((posA.x + posB.x) / 2);
		float mY = ((posA.y + posB.y) / 2);
				
		Shape shape = PhysicsFactory.createBoxShape((float) (hipotenusa - (hipotenusa * 0.4865f)), 0.1f, new Vector2(0, 0), angle);
		FixtureDef fix = PhysicsFactory.createFixture(shape, 0.1f, 0.2f, 0.4f, false);
		
		fix.filter.categoryBits = Constants.CATEGORY_PLATFORM;
		fix.filter.maskBits = Constants.MASK_PLATFORM;
		fix.filter.groupIndex = Constants.GROUP_PLATFORM;
		
		this.body = PhysicsFactory.createBody(world, BodyType.StaticBody, fix, new Vector2(mX, mY));
	}
	
	public Body getBody(){
		return this.body;
	}
	
	public void resize(Vector2 positionBuilder){
		
		if (body.getFixtureList().size > 0 && body.getFixtureList().get(0) != null){
			
			body.destroyFixture(body.getFixtureList().get(0));
	
			Vector2 posA = NodesManager.getInstance().getNode(idNodeA).getPosition();
			float radiusA = NodesManager.getInstance().getNode(idNodeA).getBody().getFixtureList().get(0).getShape().getRadius();
			
			Vector2 posB = positionBuilder;
			float radiusB = NodesManager.getInstance().getNode(idNodeB).getBody().getFixtureList().get(0).getShape().getRadius();
			
			double ca = (double) posB.x - posA.x;
			double co = (double) posB.y - posA.y;
			
			double hipotenusa = Math.sqrt(Math.pow(ca, 2.0) + Math.pow(co, 2.0));
			
			angle = (float) Math.asin(co / hipotenusa);
			
			if (posB.x < posA.x){
				angle = (((float) Math.toRadians(180)) - (angle));
			}
			
			float mX = ((posA.x + posB.x) / 2);
			float mY = ((posA.y + posB.y) / 2);
						
			Shape shape = PhysicsFactory.createBoxShape((float) (hipotenusa - (hipotenusa * 0.4865f)), 0.1f, new Vector2(0, 0), angle);
			FixtureDef fix = PhysicsFactory.createFixture(shape, 0.1f, 0.2f, 0.4f, false);
			
			body.setTransform(new Vector2(mX, mY), 0);
			
			fix.filter.categoryBits = Constants.CATEGORY_PLATFORM;
			fix.filter.maskBits = Constants.MASK_PLATFORM;
			fix.filter.groupIndex = Constants.GROUP_PLATFORM;

			body.createFixture(fix);

		}
	}
	
}
