package com.chidemgames.protectthesurvivors.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.chidemgames.protectthesurvivors.PTSGame;
import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.managers.GameManager;
import com.chidemgames.protectthesurvivors.ui.Microphone;

public class SplashScene extends AbstractScene {
	
	SpriteBatch batch;
	Texture img;
	boolean changeColor = true;
	private int width;
	private int height;
	private float timer = 0f;
	private PTSGame game;
	private Image image;
	private boolean fadeInComplete = false;
	public boolean serverResponded = false;
	private List<Jogo> jogos;
	
	public SplashScene(final PTSGame game) {
		this.game = game;
		setType(TypeScene.SPLASH);

		batch = new SpriteBatch();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		img = new Texture("ps-icone-m.png");
		image = new Image(img);
		image.setBounds(width/2f - 280f, height/2f - 200f, 560f, 400f);
		image.addAction(Actions.sequence(Actions.alpha(0f), Actions.delay(0.3f), Actions.fadeIn(0.9f), Actions.delay(0.8f), Actions.run(new Runnable() {
			@Override
			public void run() {
				fadeInComplete = true;
			}
		})));
		
		game.getGraphicsLoader().loadAssetGameSaves();
		GameManager gameManager = GameManager.getInstance();
		jogos = (List<Jogo>) game.getRequestAction().buscarJogos(gameManager.getNome());
	}
	
	public void changeColor (boolean col){
		this.changeColor = col;
	}
	
	public boolean getChangeColor(){
		return this.changeColor;
	}
	
	@Override
	public void render(float delta) {
		timer += delta;
		
		Gdx.gl.glClearColor(236/255f, 240/255f, 241/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		System.out.println(game.getGraphicsLoader().manager.getProgress());
		
		if (fadeInComplete && game.getGraphicsLoader().manager.getProgress() == 1f){
			image.addAction(Actions.sequence(Actions.fadeOut(0.6f), Actions.delay(0.6f), Actions.run(new Runnable() {
					@Override
					public void run() {
						game.setScreen(new GamesSavesScene(game, jogos));
					}
				})));
		}
		
		batch.begin();
		image.act(delta);
		image.draw(batch, 1f);
		batch.end();
	}

	@Override
	public void changeMicrophone(Microphone status) {
		
	}

}
