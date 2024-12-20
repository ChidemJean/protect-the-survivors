package com.chidemgames.protectthesurvivors.gameobjects;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.chidemgames.protectthesurvivors.Constants;
import com.chidemgames.protectthesurvivors.factories.PhysicsFactory;

public class Tower {

	private World world;
	private OrthographicCamera camera;
	private Vector2 position;
	private Body body;
	private Body weapon;
	private Vector2 localPointWeapon;
	private Vector2 positionTargetBuilder;
	private RevoluteJoint jointWeapon;
	public float atack;

	private float delayShot = 0;

	float targetAngle = 0;
	boolean startShot = false;

	private LinkedList<Builder> builders = new LinkedList<Builder>();
	public ArrayList<Bullet> bulletsActived = new ArrayList<Bullet>();
	public ArrayList<Bullet> bulletsInWeapon = new ArrayList<Bullet>();

	private boolean isShoting = false;
	private Bullet bullet = null;
	private boolean hasTarget = true;

	public Tower(World world, float atack, OrthographicCamera camera, Vector2 position){

		this.atack = atack;
		this.world = world;
		this.camera = camera;
		this.position = position;

		createPhysics();

	}

	public void createPhysics() {


		Shape shape = PhysicsFactory.createBoxShape(0.7f, 0.7f, new Vector2(0, 0), 0);
		FixtureDef fix = PhysicsFactory.createFixture(shape, 0.3f, 0.5f, 0.5f, false);

		fix.filter.categoryBits = Constants.CATEGORY_TOWER;
		fix.filter.maskBits = Constants.MASK_TOWER;
		fix.filter.groupIndex = Constants.GROUP_TOWER;

		CircleShape range = PhysicsFactory.createCircleShape(8f);
		FixtureDef fixRange = PhysicsFactory.createFixture(range, 0.1f, 0.2f, 0.4f, true);

		fixRange.filter.categoryBits = Constants.CATEGORY_TOWER;
		fixRange.filter.maskBits = Constants.MASK_TOWER;
		fixRange.filter.groupIndex = Constants.GROUP_TOWER;

		body = PhysicsFactory.createBody(world, BodyType.DynamicBody, fix, position);
		body.createFixture(fixRange);
		body.setFixedRotation(true);


		Shape shapeWeapon = PhysicsFactory.createBoxShape(0.7f, 0.1f, new Vector2(0, 0), 0);

		FixtureDef fixWeapon = PhysicsFactory.createFixture(shapeWeapon, 0.1f, 0.2f, 0.4f, false);
		fixWeapon.filter.categoryBits = Constants.CATEGORY_WEAPON;
		fixWeapon.filter.maskBits = Constants.MASK_WEAPON;
		fixWeapon.filter.groupIndex = Constants.GROUP_TOWER;

		weapon = PhysicsFactory.createBody(world, BodyType.DynamicBody, fixWeapon, new Vector2(body.getWorldCenter().x + 0.9f, body.getWorldCenter().y));

		localPointWeapon = new Vector2(body.getWorldCenter().x + 0.2f, body.getWorldCenter().y);

		RevoluteJointDef joint = new RevoluteJointDef();
		joint.collideConnected = false;
		joint.initialize(body, weapon, localPointWeapon);
		joint.maxMotorTorque = 500;
		joint.enableMotor = false;

		jointWeapon = (RevoluteJoint) world.createJoint(joint);

		body.setUserData(this);
	}

	public void addBuilder(Builder b){
		if (!builders.contains(b))
			builders.offer(b);

	}

	public void removeBuilder(Builder b){
		if (builders.contains(b))
			builders.remove(b);

	}

	public void pollBuilder(){
		builders.poll();
	}

	public void reloadWeapon(){
		for (int i = 0; i < 5; i++){
			bulletsInWeapon.add(new Bullet(world, this, weapon.getWorldCenter(), weapon.getAngle()));
		}
	}

	public void updateBullet(){
		for (Bullet bullet : bulletsInWeapon){
			bullet.setTransform(weapon.getWorldCenter(), weapon.getAngle());
		}
	}

	public void onUpdateState(){

		if (bulletsInWeapon.size() > 0){
			updateBullet();
			for (Bullet bullet : bulletsInWeapon){
				bullet.onUpdate();
				bullet.body.applyForce(-world.getGravity().x*bullet.body.getMass(),
						-world.getGravity().y*bullet.body.getMass(), bullet.body.getWorldCenter().x, bullet.body.getWorldCenter().y, true);
			}
		}

		if (bulletsActived.size() > 0){
			for (int i = 0; i < bulletsActived.size(); i++) {
				if (bulletsActived.get(i).body != null){
					bulletsActived.get(i).onUpdate();
				}
				if (!bulletsActived.get(i).isActive || (bulletsActived.get(i).body.getLinearVelocity().x == 0 && bulletsActived.get(i).body.getLinearVelocity().y == 0)){
					bulletsActived.remove(bulletsActived.get(i));
				}
			}
		}

		if (startShot){
			delayShot ++;
		} else {
			delayShot = 0;
		}

		if (builders.size() > 0) {

			isShoting = true;

			Builder targetBuilder = builders.peek();
			positionTargetBuilder = targetBuilder.getBody().getWorldCenter();

			if (bulletsInWeapon.size() > 0 && (delayShot == 14f || !startShot)){
				float vX = ((positionTargetBuilder.x - bulletsInWeapon.get(0).body.getWorldCenter().x) * bulletsInWeapon.get(0).body.getMass());
				float vY = ((positionTargetBuilder.y - bulletsInWeapon.get(0).body.getWorldCenter().y) * bulletsInWeapon.get(0).body.getMass());
				startShot = true;

				bulletsInWeapon.get(0).body.applyLinearImpulse(new Vector2(vX * 6, vY * 6), bulletsInWeapon.get(0).body.getWorldCenter(), true);
				bulletsActived.add(bulletsInWeapon.get(0));
				bulletsInWeapon.get(0).setInWeapon(false);
				bulletsInWeapon.remove(bulletsInWeapon.get(0));
				delayShot = 0;

			}

			double ca = (double) positionTargetBuilder.x - localPointWeapon.x;
			double co = ((double) positionTargetBuilder.y - localPointWeapon.y) + 1.4;

			double hipotenusa = Math.sqrt(Math.pow(ca, 2.0) + Math.pow(co, 2.0));

			if (localPointWeapon.x > positionTargetBuilder.x){
				targetAngle = (float) Math.toRadians(180) - (float) Math.asin(co / hipotenusa);
			} else {
				targetAngle = (float) Math.asin(co / hipotenusa);
			}
		} else {
			if (localPointWeapon.x > 0){
				targetAngle = (float) Math.toRadians(180);
			} else {
				targetAngle	= 0;
			}
			delayShot = 0;
			startShot = false;
		}

		jointWeapon.enableMotor(true);

		float diferenca = (float) Math.abs(jointWeapon.getJointAngle() - targetAngle);
		float speedMotor = diferenca / diferenca /2;

		if (jointWeapon.getJointAngle() > targetAngle){
			jointWeapon.setMotorSpeed(-speedMotor);
		} else if (jointWeapon.getJointAngle() < targetAngle){
			jointWeapon.setMotorSpeed(speedMotor);
		} else {
			jointWeapon.enableMotor(false);
		}

		if (isShoting && bulletsInWeapon.size() == 0 && bulletsActived.size() < 5){
			reloadWeapon();
		}

	}

	public void removeBullet(Body bullet){
		bulletsActived.remove(bullet);
		world.destroyBody(bullet);
	}

	public void setTargetAngle(float angle){
		this.hasTarget = true;
		this.targetAngle = angle;
	}

	public float getTargetAngle(){
		return this.targetAngle;
	}

}
