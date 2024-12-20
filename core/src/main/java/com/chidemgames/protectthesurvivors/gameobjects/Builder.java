package com.chidemgames.protectthesurvivors.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.chidemgames.protectthesurvivors.Constants;
import com.chidemgames.protectthesurvivors.factories.PhysicsFactory;
import com.chidemgames.protectthesurvivors.managers.NodesManager;

public class Builder {

	private Body body;
	private int level = 5;
	private float life, tempLife, lifeFull;            
	private State currentState;
	private boolean isUseGravity = true;
	private World world;
	private OrthographicCamera camera;
	private Vector2 pos;
	private float price, scoreunit;
	public int direction;
	private int idNodeA, idNodeB, idCurrentNode = -1, currentLayer = -1;
	
	private float vX, vY, y = 0;
	private Bridge bridge;
	private Vector2 destiny;
	private boolean isBuilding = false; 
	private float timePerState = 0f;
	private boolean isDied = false;
	
	private Sprite sprite, lifeSprite;
	
	public enum State {
		STOPPED,
		SELECTING_DESTINY,
		BUILDING,
		ARRIVE_DESTINY,
		MOVING_LEFT,
		MOVING_RIGTH,
		MOVING_TO_NODE,
		SELECTING_FIRST_DESTINY,
		DIED
	}
	
	public Builder(World world, OrthographicCamera camera, int level, Vector2 spawnPoint) {
		this.level = level;
		this.world = world;
		this.camera = camera;
		this.pos = spawnPoint;
		
		setState(State.SELECTING_FIRST_DESTINY);
		
		sprite = new Sprite(new Texture("destroyer_icone.png"));
		sprite.setBounds(0, 0, 2f, 2f);
		
		lifeSprite = new Sprite(new Texture("life_enemys.png"));
		lifeSprite.setBounds(0, 0, 2f, 0.1f);
		
		defineLife();
		createPhysics(world);
	}
	
	public void setState(State currentState){
		this.currentState = currentState;
		timePerState = 0f;
	}
	
	public void createPhysics(World world){
		
		Shape shape = PhysicsFactory.createBoxShape(0.8f, 0.8f,	new Vector2(0, 0), 0);
		FixtureDef fixtureDef = PhysicsFactory.createFixture(shape, 0.3f, 0.5f, 0.5f, false);
		
		fixtureDef.filter.categoryBits = Constants.CATEGORY_BUILDER;
		fixtureDef.filter.maskBits = Constants.MASK_BUILDER;
		fixtureDef.filter.groupIndex = Constants.GROUP_BUILDER;
		
		body = PhysicsFactory.createBody(world, BodyType.DynamicBody, fixtureDef, pos);
		body.setFixedRotation(true);
		
		this.body.setUserData(this);
		
	}
	
	public Sprite getSprite(){
		return this.sprite;
	}
	
	public boolean isDied(){
		return isDied;
	}
	
	public void renderSprite(SpriteBatch batch){
		if (life > 0){
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
			sprite.setRotation((float) Math.toDegrees(body.getAngle()));
			sprite.draw(batch);
			
			lifeSprite.setPosition(body.getPosition().x - lifeSprite.getWidth() / 2, (body.getPosition().y + 1.3f) - lifeSprite.getHeight() / 2);
			lifeSprite.setRotation((float) Math.toDegrees(body.getAngle()));
			lifeSprite.draw(batch);
		}
	}
	
	public void defineLife(){
		if (this.level >= 1 && this.level <= 5){
			this.life = 120;
			lifeFull = 120;
			price = life * 0.05f;
			scoreunit = life / 10f;
			this.tempLife = life;
		}
	}
	
	public float getPrice(){
		return price;
	}
	
	public float getScoreUnit(){
		return scoreunit;
	}
	
	public void decreaseLife(float dano){
		if (this.life - dano < 0 ){
			this.life = 0;
		} else {
			this.life -= dano;
		}
	}
	
	public void invertDirection(){
		this.direction *= -1;
		if (direction == -1){
			setState(State.MOVING_LEFT);
		} else {
			setState(State.MOVING_RIGTH);
		}
	}
	
	public void onUpdateState(float delta) {
		
		if (life < tempLife){
			
			tempLife = life;
			float width = this.life * 2f / lifeFull;
			lifeSprite.setSize(width, lifeSprite.getHeight());
			
		}
		
		if (timePerState > 720f){
			life = 0;
		}
		
		if ((this.direction == 1 && body.getWorldCenter().x >= (camera.viewportWidth/2 - (0.1f + 0.25f))) || 
				(this.direction == -1 && body.getWorldCenter().x <= (-camera.viewportWidth/2 + (0.1f + 0.25f)))){
			invertDirection();
		}
		
		if (!this.isUseGravity){
			body.applyForce(-world.getGravity().x*body.getMass(),
					-world.getGravity().y*body.getMass(), body.getWorldCenter().x, body.getWorldCenter().y, true);
		} 
		
		if (life == 0 && !isDied){
			setState(State.DIED);
		}
		
		switch(this.currentState){
			case SELECTING_FIRST_DESTINY:
				
				chooseNodeByLayer(1);
	
				if (this.destiny != null){
					setState(State.MOVING_TO_NODE);
				}
				
				break;
			case SELECTING_DESTINY:
				
				idCurrentNode = idNodeA;
				currentLayer = NodesManager.getInstance().getNode(idCurrentNode).getPosInLayer();
				
				chooseNode();
				
				destiny = NodesManager.getInstance().getNode(idNodeB).getPosition();
			
				float t = 4f;
				
				this.vX = Math.abs((destiny.x - body.getWorldCenter().x) / t);
				this.vY = Math.abs((destiny.y - body.getWorldCenter().y) / t);
													
				break;
			case BUILDING:
				timePerState ++;
				
				if (!moveToDestiny()){
					isBuilding = true;
					bridge.resize(this.body.getPosition());
					
					NodesManager.getInstance().setStatusNode(NodesManager.getInstance().getNode(idNodeA), NodesManager.getInstance().getNode(idNodeB), NodesManager.CONNECTING);
					NodesManager.getInstance().setStatusNode(NodesManager.getInstance().getNode(idNodeB), NodesManager.getInstance().getNode(idNodeA), NodesManager.CONNECTING);
					
				} else {
					isBuilding = false;
					NodesManager.getInstance().setStatusNode(NodesManager.getInstance().getNode(idNodeA), NodesManager.getInstance().getNode(idNodeB), NodesManager.CONNECTED);
					NodesManager.getInstance().setStatusNode(NodesManager.getInstance().getNode(idNodeB), NodesManager.getInstance().getNode(idNodeA), NodesManager.CONNECTED);
					
					bridge.resize(destiny);
					
					idNodeA = idNodeB;
					idNodeB = -1;
					
					this.body.setLinearVelocity(0, 0);
					
					setState(State.SELECTING_DESTINY);
				}
			
				break;
			case STOPPED:
				
				this.body.setLinearVelocity(0, 0);
				
				break;
			case MOVING_LEFT:
				
				this.direction = -1;
				this.body.setLinearVelocity(new Vector2(-2f, body.getLinearVelocity().y));
				
				break;
			case MOVING_RIGTH:

				this.direction = 1;
				this.body.setLinearVelocity(new Vector2(2f, body.getLinearVelocity().y));
				
				break;
			case MOVING_TO_NODE:
				
				float tempXBody = ((float)((int)(this.body.getPosition().x * 10)) / 10);
				float tempXNode = ((float)((int)(destiny.x * 10)) / 10);
				
				if (tempXBody < tempXNode){
					this.direction = 1;
					this.body.setLinearVelocity(new Vector2(2f, body.getLinearVelocity().y));

				} else if (tempXBody > tempXNode){
					this.body.setLinearVelocity(new Vector2(-2f, body.getLinearVelocity().y));
				} else {
					setState(State.SELECTING_DESTINY);
				}
				
				break;
			case ARRIVE_DESTINY:
				timePerState ++;
				
				if (moveToDestiny()){
					if (NodesManager.getInstance().inLastLayer(idNodeB)){
						System.out.println("in last layer :)");
						life = 0;
						explode(40);
					} else {
						idNodeA = idNodeB;
						idNodeB = -1;
						setState(State.SELECTING_DESTINY);
					}
					timePerState = 0f;
				}
				
				break;
			case DIED:
				this.body.setLinearVelocity(0, 0);
				for (Fixture fix: body.getFixtureList()){
					this.body.destroyFixture(fix);
				}
				this.world.destroyBody(body);
				isDied = true;
				setState(State.STOPPED);
				break;
			default:
				break;
		}
		
	}
	
	public void explode(int numRays){
		
		Vector2 center = body.getWorldCenter();
		float radius = 6f;
		
		for (int i = 0; i < numRays; i++){
			
			float angle = (i / (float) numRays) * (float) Math.toRadians(360);
			Vector2 directionRay = new Vector2((float) Math.sin((double) angle), (float) Math.cos((double)angle)); 
			Vector2 endRay = new Vector2(center.x + (directionRay.x * radius), center.y + (directionRay.y * radius));
			
			RayCastCallBack callback = new RayCastCallBack();
			
			world.rayCast(callback, center, endRay);
			if (callback.body != null){
				blastImpulse(callback.body, center, callback.pointIntersect, 60 / (float) numRays);
			}
		}
		
	}
	
	public void blastImpulse(Body body, Vector2 blastCenter, Vector2 applyPoint, float blastPower){
		
		Vector2 direction = new Vector2(applyPoint.x - blastCenter.x, applyPoint.y - blastCenter.y);
		float distance = Vector2.len(direction.x, direction.y);
		
		if (distance > 0 && body != null){
			
			float invDistance = 1 / distance;
			float impulse = blastPower * invDistance * invDistance;
			body.applyLinearImpulse(new Vector2(impulse * direction.x, impulse * direction.y), applyPoint, true);
			
			if (body.getUserData() instanceof Survivor){
				Survivor s = (Survivor) body.getUserData();
				s.decreaseLife(impulse * 10);
			}
			
			if (body.getUserData() instanceof WallBox){
				WallBox w = (WallBox) body.getUserData();
				w.decreaseLife(impulse * 100);
			}
		}
		
	}
	
	public void chooseNode(){
		
		if (currentLayer != -1){
			
			ArrayList<Integer> ids = NodesManager.getInstance().getNodesByStatus(idCurrentNode, NodesManager.CONNECTABLE);
			ids.addAll(NodesManager.getInstance().getNodesByStatus(idCurrentNode, NodesManager.CONNECTED));
			int indexRandom = (int) (Math.random() * (ids.size()));
			
			if (ids.size() > 0){
				Integer nodeId = ids.get(indexRandom);
				this.idNodeB = nodeId;
				int layerB = NodesManager.getInstance().getNode(idNodeB).getLayer();
				
				if (layerB > 1){
					if (NodesManager.getInstance().isLinked(NodesManager.getInstance().getNode(idNodeA), NodesManager.getInstance().getNode(idNodeB))){ 
						setState(State.ARRIVE_DESTINY);
					} else {
						setState(State.BUILDING);
						bridge = new Bridge(world, camera, idNodeA, idNodeB);
					}
				} else {
					setState(State.SELECTING_DESTINY);
				}
			}
		}
		
	}
	
	public void chooseNodeByLayer(int layer){
		
		ArrayList<Integer> ids = NodesManager.getInstance().getNodesByLayer(layer);
		int indexRandom = (int) (Math.random() * (ids.size()));
		Integer nodeId = ids.get(indexRandom);
		
		this.idNodeA = nodeId;
		
		Node node = NodesManager.getInstance().getNode(nodeId);
		NodesManager.getInstance().getNodesByStatus(nodeId, NodesManager.CONNECTED);
		
		this.destiny = node.getPosition();
		System.out.println("Id node: " + nodeId);
	}
	
	public boolean moveToDestiny(){
		
		this.isUseGravity = false;
		
		boolean isArrive = true;
		
		float tempXBody = ((float)((int)(this.body.getPosition().x * 10)) / 10);
		float tempYBody = ((float)((int)(this.body.getPosition().y * 10)) / 10);
		
		float tempXNode = ((float)((int)(destiny.x * 10)) / 10);
		float tempYNode = ((float)((int)(destiny.y * 10)) / 10);
		
		if (tempYBody != tempYNode){
			
			isArrive = false;
			if (this.body.getWorldCenter().y < destiny.y){
				this.body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, this.vY));
			} 			
			if (this.body.getWorldCenter().y > destiny.y){
				this.body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, -this.vY));
			}
		}

		if (tempXBody != tempXNode) {
			
			isArrive = false;
			
			if (this.body.getWorldCenter().x < destiny.x){
				this.body.setLinearVelocity(new Vector2(this.vX, body.getLinearVelocity().y));
			}
			
			if (this.body.getWorldCenter().x > destiny.x){
				this.body.setLinearVelocity(new Vector2(-this.vX, body.getLinearVelocity().y));
			}
		}
		
		return isArrive;
	}
	
	public float getLife(){
		return life;
	}
	
	public Body getBody(){
		return this.body;
	}
}

class RayCastCallBack implements RayCastCallback{
	
	public Body body;
	public Vector2 pointIntersect;
	
	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		body = fixture.getBody();
		if (body.getUserData() instanceof Survivor || body.getUserData() instanceof WallBox){
			pointIntersect = point;
		} else {
			body = null;
		}
		return 0;
	}
	
}