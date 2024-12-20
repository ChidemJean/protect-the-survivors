package com.chidemgames.protectthesurvivors.gameworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.chidemgames.protectthesurvivors.GestureInput;
import com.chidemgames.protectthesurvivors.collisionsystem.Contacts;
import com.chidemgames.protectthesurvivors.factories.PhysicsFactory;
import com.chidemgames.protectthesurvivors.gameobjects.Builder;
import com.chidemgames.protectthesurvivors.gameobjects.Platform;
import com.chidemgames.protectthesurvivors.gameobjects.Survivor;
import com.chidemgames.protectthesurvivors.gameobjects.Tower;
import com.chidemgames.protectthesurvivors.gameobjects.WallBox;
import com.chidemgames.protectthesurvivors.gameobjects.WallBox.WallBoxType;
import com.chidemgames.protectthesurvivors.managers.NodesManager;
import com.chidemgames.protectthesurvivors.screens.GameScene;
import com.chidemgames.protectthesurvivors.ui.Tile;

public class GameController {

	private World world;
	Skin skin;
	SpriteBatch batch;
	float tempo = 0;
	private float posInitialCam, zoomInitialCam;
	private Contacts contacts;
	private ArrayList<Body> bodysToDestroy = new ArrayList<Body>();
	private Box2DDebugRenderer debugRenderer;
	
	private ArrayList<Survivor> survivors = new ArrayList<Survivor>();
	private ArrayList<WallBox> walls = new ArrayList<WallBox>();
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	public ArrayList<Tower> towers = new ArrayList<Tower>();
	
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public Sprite towerToPosition;
	
	public boolean positionGridEnable = false;
	private OrthographicCamera camera;
	private GameScene scene;
	
	private WaveController waveController;
	
	private boolean gameOver = false;
	
	public GameScene getScene(){
		return this.scene;
	}
	
	public GameController(GameScene scene) {
		
		this.scene = scene;
		batch = new SpriteBatch();

		world = new World(new Vector2(0, -9.81f), true);

		debugRenderer = new Box2DDebugRenderer();
		createContactListener();
		this.camera = new OrthographicCamera(30, (30 * (Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth())));
		
		System.out.println("camera w: " + camera.viewportWidth + " camera h: " + camera.viewportHeight);
		
		NodesManager.getInstance().createNodes(world, new Vector2[] {
				new Vector2(camera.viewportWidth/6, -camera.viewportHeight /2f + 0.2f),
				new Vector2(0, -camera.viewportHeight /2f + 0.2f),
				new Vector2(-camera.viewportWidth/6, -camera.viewportHeight /2f + 0.2f),
				new Vector2(camera.viewportWidth/7.4f, -camera.viewportHeight/4f),
				new Vector2(-camera.viewportWidth/7.4f, -camera.viewportHeight/4f),
				new Vector2(camera.viewportWidth/6, camera.viewportHeight/16f),
				new Vector2(0, camera.viewportHeight/16f),
				new Vector2(-camera.viewportWidth/6, camera.viewportHeight/16f),
				new Vector2(camera.viewportWidth/7.4f, camera.viewportHeight/2.7f),
				new Vector2(-camera.viewportWidth/7.4f, camera.viewportHeight/2.7f),
				new Vector2(camera.viewportWidth/6, camera.viewportHeight/1.62f),
				new Vector2(0, camera.viewportHeight/1.62f),
				new Vector2(-camera.viewportWidth/6, camera.viewportHeight/1.62f),
				new Vector2(camera.viewportWidth/7.4f, camera.viewportHeight/1.1f),
				new Vector2(-camera.viewportWidth/7.4f, camera.viewportHeight/1.1f)
		});
		
		posInitialCam = camera.position.y;
		zoomInitialCam = camera.zoom;

		Body constructArea = null; 
		
		survivors.add(new Survivor(world, 100, new Vector2(0, camera.viewportHeight + 2.70f), 0.6f));
		survivors.add(new Survivor(world, 100, new Vector2(0.15f, camera.viewportHeight + 2.70f), 0.4f));
		survivors.add(new Survivor(world, 100, new Vector2(-0.15f, camera.viewportHeight + 2.70f), 1.0f));
		survivors.add(new Survivor(world, 100, new Vector2(0, camera.viewportHeight + 2.70f), 0.6f));
		survivors.add(new Survivor(world, 100, new Vector2(-0.35f, camera.viewportHeight + 2.70f), 0.5f));
		survivors.add(new Survivor(world, 100, new Vector2(0.35f, camera.viewportHeight + 2.70f), 0.5f));
		survivors.add(new Survivor(world, 100, new Vector2(0.7f, camera.viewportHeight + 2.70f), 0.7f));
		survivors.add(new Survivor(world, 100, new Vector2(-0.7f, camera.viewportHeight + 2.70f), 0.6f));
		survivors.add(new Survivor(world, 100, new Vector2(0.40f, camera.viewportHeight + 2.70f), 0.6f));
		survivors.add(new Survivor(world, 100, new Vector2(-0.40f, camera.viewportHeight + 2.70f), 0.7f));
		survivors.add(new Survivor(world, 100, new Vector2(0.15f, camera.viewportHeight + 2.70f), 0.8f));
		survivors.add(new Survivor(world, 100, new Vector2(-1f, camera.viewportHeight + 2.70f), 0.1f));
		survivors.add(new Survivor(world, 100, new Vector2(1f, camera.viewportHeight + 2.70f), 0.4f));
		survivors.add(new Survivor(world, 100, new Vector2(1.15f, camera.viewportHeight + 2.70f), 1f));
		survivors.add(new Survivor(world, 100, new Vector2(0.15f, camera.viewportHeight + 2.70f), 1f));
		survivors.add(new Survivor(world, 100, new Vector2(1.00f, camera.viewportHeight + 2.70f), 0.5f));
		survivors.add(new Survivor(world, 100, new Vector2(0.72f, camera.viewportHeight + 2.70f), 0.3f));
		survivors.add(new Survivor(world, 100, new Vector2(-0.35f, camera.viewportHeight + 2.70f), 0.7f));
		survivors.add(new Survivor(world, 100, new Vector2(0f, camera.viewportHeight + 2.70f), 0.2f));
		
		walls.add(new WallBox(world, 100, new Vector2(0, camera.viewportHeight), 1.3f, 0.6f, WallBoxType.STATIC, true));
		walls.add(new WallBox(world, 100, new Vector2(-2.75f, camera.viewportHeight), 1.3f, 0.6f, WallBoxType.STATIC, true));
		walls.add(new WallBox(world, 100, new Vector2(2.75f, camera.viewportHeight), 1.3f, 0.6f, WallBoxType.STATIC, true));
		walls.add(new WallBox(world, 100, new Vector2(-5.5f, camera.viewportHeight), 1.3f, 0.6f, WallBoxType.STATIC, true));
		walls.add(new WallBox(world, 100, new Vector2(5.5f, camera.viewportHeight), 1.3f, 0.6f, WallBoxType.STATIC, true));
		
		walls.add(new WallBox(world, 100, new Vector2(7.55f, camera.viewportHeight + 0.65f), 0.6f, 1.3f, WallBoxType.STATIC, true));
		walls.add(new WallBox(world, 100, new Vector2(-7.55f, camera.viewportHeight + 0.65f), 0.6f, 1.3f, WallBoxType.STATIC, true));
		
		walls.add(new WallBox(world, 100, new Vector2(7.55f, camera.viewportHeight + 2.70f), 0.6f, 0.6f, WallBoxType.STATIC, true));
		walls.add(new WallBox(world, 100, new Vector2(-7.55f, camera.viewportHeight + 2.70f), 0.6f, 0.6f, WallBoxType.STATIC, true));
		
		PhysicsFactory.createWalls(world, camera.viewportWidth,
				camera.viewportHeight, 0.1f);
		
		platforms.add(new Platform(world, new Vector2((-camera.viewportWidth / 2f) + (0.1f + 4f) , 0), 4f, 0.1f));
		platforms.add(new Platform(world, new Vector2((camera.viewportWidth / 2f) - (0.1f + 4f) , 0), 4f, 0.1f));		
		
		platforms.add(new Platform(world, new Vector2((-camera.viewportWidth / 2f) + (0.1f + 4f) , camera.viewportHeight / 4f), 4f, 0.1f));
		platforms.add(new Platform(world, new Vector2((camera.viewportWidth / 2f) - (0.1f + 4f) , camera.viewportHeight / 4f), 4f, 0.1f));	
		
		platforms.add(new Platform(world, new Vector2((-camera.viewportWidth / 2f) + (0.1f + 4f) , camera.viewportHeight / 2f), 4f, 0.1f));
		platforms.add(new Platform(world, new Vector2((camera.viewportWidth / 2f) - (0.1f + 4f) , camera.viewportHeight / 2f), 4f, 0.1f));	
		
		platforms.add(new Platform(world, new Vector2((-camera.viewportWidth / 2f) + (0.1f + 4f) , camera.viewportHeight / 1.25f), 4f, 0.1f));
		platforms.add(new Platform(world, new Vector2((camera.viewportWidth / 2f) - (0.1f + 4f) , camera.viewportHeight / 1.25f), 4f, 0.1f));	
		
		if (platforms.size() > 0){
			int charac = 65;
			for (Platform platform : platforms){
				
				float widthPlatform = platform.getWidth();
				float numtiles = widthPlatform / 1f;
				float x = platform.getBody().getPosition().x - (widthPlatform);
				float y = platform.getBody().getPosition().y + 0.06f;
				
				for (int i = 0; i < numtiles; i++){
					tiles.add(new Tile(x, y, ((char) charac + "" + i), 2f, 2f, new Texture("Tile.png")));
					x += 2f;
				}
				if (charac < 90){
					charac++;	
				} else {
					charac = 65;
				}
			}
		}
		
		waveController = new WaveController(camera, world);
		
	}

	public void onUpdate(){
		
	}
	
	public void render(float delta){
		tempo ++;

		if (positionGridEnable){
			world.step(0, 6, 2);
		} else {
			world.step(1 / 60f, 6, 2);
		}
		
		for (WallBox w : walls){
			w.onUpdate();
		}
		
		debugRenderer.render(world, camera.combined);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for (int i = 0; i < survivors.size(); i++){
			survivors.get(i).onUpdate();
			survivors.get(i).renderSprite(batch);
			
			if (survivors.get(i).life == 0){
				survivors.remove(survivors.get(i));
			}
		}
		
		if (survivors.size() == 0){
			gameOver = true;
		}
		
		for (int i = 0; i < waveController.getWaveEnemys().size(); i++){
			Builder builder = waveController.getWaveEnemys().get(i);
			builder.onUpdateState(delta);
			builder.renderSprite(batch);
			
			if (builder.isDied()){
				waveController.getWaveEnemys().remove(builder);
			}
		}
		
		for (Tower tower : towers){
			tower.onUpdateState();
		}

		if (positionGridEnable){
			for (Tile tile : tiles) {
				tile.render(delta, batch);
			}
		} else {
			if (waveController != null && !gameOver){
				waveController.onUpdate(delta);
			}
		}
		
		if (towerToPosition != null){
			towerToPosition.draw(batch);
		}
		
		batch.end();
	}
	
	public void createContactListener(){
		contacts = new Contacts(world);
		world.setContactListener(contacts);
	}
	
	public void addBodyToDestroy(Body body){
		bodysToDestroy.add(body);
	}
	
	public ArrayList<Survivor> getSurvivors(){
		return survivors;
	}
	
	public OrthographicCamera getCamera(){
		return camera;
	}
	
	public String tileContactPoint(Vector2 point){
		for (Tile tile : this.tiles){
			if (tile.getSprite().getBoundingRectangle().contains(point)){
				return tile.getAddress();
			}
		}
		return null;
	}
	
	public String tileContactPoint(Rectangle rec){
		for (Tile tile : this.tiles){
			if (tile.getSprite().getBoundingRectangle().contains(rec)){
				return tile.getAddress();
			}
		}
		return null;
	}
	
	public Tile getTileByAddress(String Address){
		for (Tile tile : this.tiles){
			if (tile.getAddress().equals(Address)){
				return tile;
			}
		}
		return null;
	}

	public boolean isPositionGridEnable() {
		return positionGridEnable;
	}

	public void setPositionGridEnable(boolean positionGridEnable) {
		this.positionGridEnable = positionGridEnable;
	}
	
	public void addTower(Tile tile){
		if (tile != null){
			Sprite sprite = tile.getSprite();
			Vector2 position = new Vector2(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2.2f);
			Tower tower = new Tower(world, 1f, camera, position);
			towers.add(tower);
			tile.setTower(tower);
		}
	}

	public void dispose(){
		debugRenderer.dispose();
	}
	
}
