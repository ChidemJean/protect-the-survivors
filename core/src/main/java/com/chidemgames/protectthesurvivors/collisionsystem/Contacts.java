package com.chidemgames.protectthesurvivors.collisionsystem;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.chidemgames.protectthesurvivors.gameobjects.Builder;
import com.chidemgames.protectthesurvivors.gameobjects.Bullet;
import com.chidemgames.protectthesurvivors.gameobjects.Survivor;
import com.chidemgames.protectthesurvivors.gameobjects.Tower;
import com.chidemgames.protectthesurvivors.gameobjects.WallBox;
import com.chidemgames.protectthesurvivors.managers.GameManager;

public class Contacts implements ContactListener {

	private World world;
	public ArrayList<Body> bodysToDestroy = new ArrayList<Body>();
	public ArrayList<Fixture> fixturesToDestroy = new ArrayList<Fixture>();
	
	public Contacts(World world){
		this.world = world;
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		
		boolean sensorA = fixtureA.isSensor();
		boolean sensorB = fixtureB.isSensor();
		
		if (bodyA.getUserData() instanceof Builder){
			if (sensorB && bodyB.getUserData() instanceof Tower){
				Tower tower = (Tower) bodyB.getUserData();
				tower.addBuilder((Builder) bodyA.getUserData());
			} else {
				if (sensorB && bodyB.getUserData() instanceof Bullet){
					Builder b = (Builder) bodyA.getUserData();
					final Bullet bu = (Bullet) bodyB.getUserData();
					b.decreaseLife(bu.tower.atack);
					destroyBody(bu.body);
					bu.isActive = false;
					if (b.getLife() == 0){
						GameManager.getInstance().increment(b.getPrice(), GameManager.MOEDAS);
						GameManager.getInstance().increment(b.getScoreUnit(), GameManager.SCORE);
						GameManager.getInstance().increment(b.getScoreUnit(), GameManager.XP);
					}
				}
			}
		}
		
		if (bodyB.getUserData() instanceof Builder){
			if (sensorA && bodyA.getUserData() instanceof Tower){
				Tower tower = (Tower) bodyA.getUserData();
				tower.addBuilder((Builder) bodyB.getUserData());
			} else {
				if (sensorA && bodyA.getUserData() instanceof Bullet){
					Builder b = (Builder) bodyB.getUserData();
					final Bullet bu = (Bullet) bodyA.getUserData();
					b.decreaseLife(bu.tower.atack);
					destroyBody(bu.body);
					bu.isActive = false;
					if (b.getLife() == 0){
						GameManager.getInstance().increment(b.getPrice(), GameManager.MOEDAS);
						GameManager.getInstance().increment(b.getScoreUnit(), GameManager.SCORE);
						GameManager.getInstance().increment(b.getScoreUnit(), GameManager.XP);
					}
				}
			}
		}
		
		if (bodyA.getUserData() != null && bodyA.getUserData().equals("wall")){
			if (sensorB && bodyB.getUserData() instanceof Bullet){
				destroyBody(bodyB);
			}
		}
		if (bodyB.getUserData() != null && bodyB.getUserData().equals("wall")){
			if (sensorA && bodyA.getUserData() instanceof Bullet){
				destroyBody(bodyA);
			}
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();	
		
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		
		boolean sensorA = fixtureA.isSensor();
		boolean sensorB = fixtureB.isSensor();
		
		if (bodyA.getUserData() instanceof Builder){
			if (sensorB && bodyB.getUserData() instanceof Tower){
				Tower tower = (Tower) bodyB.getUserData();
				tower.removeBuilder((Builder) bodyA.getUserData());
			}
		}
		
		if (bodyB.getUserData() instanceof Builder){
			if (sensorA && bodyA.getUserData() instanceof Tower){
				Tower tower = (Tower) bodyA.getUserData();
				tower.removeBuilder((Builder) bodyB.getUserData());
			}
		}
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		
		if (bodyA.getUserData() instanceof Survivor && !(bodyB.getUserData() instanceof WallBox) && !(bodyB.getUserData() instanceof Survivor)){
			Survivor s = (Survivor) bodyA.getUserData();
			float dano = 0;
			for (float im : impulse.getNormalImpulses()){
				dano += im;
			}
			s.decreaseLife(dano * 100);
			if (s.life == 0){
				destroyBody(s.body);
			}
		}
		
		if (bodyB.getUserData() instanceof Survivor && !(bodyA.getUserData() instanceof WallBox) && !(bodyA.getUserData() instanceof Survivor)){
			Survivor s = (Survivor) bodyB.getUserData();
			float dano = 0;
			for (float im : impulse.getNormalImpulses()){
				dano += im;
			}
			s.decreaseLife(dano * 100);
			if (s.life == 0){
				destroyBody(s.body);
			}
		}
	}
	
	public void destroyBody(final Body body){
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				if (body != null && body.getFixtureList().size > 0){
					world.destroyBody(body);
				}
			}
		});
	}

}
