package com.chidemgames.protectthesurvivors.gameworld;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.chidemgames.protectthesurvivors.gameobjects.Builder;

public class WaveController {

	private ArrayList<Builder> builders = new ArrayList<Builder>();
	private OrthographicCamera camera;
	private World world;
	private Vector2 spawnPointLeft, spawnPointRight;
	private float timeWave;
	private float timeToSpawn;
	
	public WaveController(OrthographicCamera camera, World world){
		
		this.camera = camera;
		this.world = world;
		this.timeToSpawn = 0;
		this.spawnPointLeft = new Vector2((-camera.viewportWidth/2) -1.5f, (-camera.viewportHeight/2) + 1.5f);
		this.spawnPointRight = new Vector2((camera.viewportWidth/2) + 1.5f, (-camera.viewportHeight/2) + 1.5f);
		createNewWave();
	}
	
	public void onUpdate(float delta){
		
		decreaseTimers();

		if (this.timeWave > 0){
			if (this.timeToSpawn == 0 && builders.size() < 5){
				int randomPoint = 1 + (int) (Math.random() * 2);
				builders.add(new Builder(world, camera, 1, (randomPoint == 1)? spawnPointLeft : spawnPointRight));
				this.timeToSpawn = 20f * (1 + (int) (Math.random() * 6));
			}
		} else if (builders.size() == 0) {
			createNewWave();
		}
	}
	
	public void createNewWave(){
		
		this.timeWave = 1200f;
		
	}
	
	public void decreaseTimers(){
		this.timeWave -= (this.timeWave > 0)? 1 : 0;
		this.timeToSpawn -=  ((this.timeToSpawn > 0)? 1 : 0);
	}
	
	public List<Builder> getWaveEnemys(){
		return builders;
	}
	
}
