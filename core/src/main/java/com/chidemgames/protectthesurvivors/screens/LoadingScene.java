package com.chidemgames.protectthesurvivors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;
import com.chidemgames.protectthesurvivors.PTSGame;
import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;
import com.chidemgames.protectthesurvivors.loaders.GraphicsLoader;
import com.chidemgames.protectthesurvivors.managers.SkinGenerator;
import com.chidemgames.protectthesurvivors.ui.Microphone;

public class LoadingScene extends AbstractScene {

	private PTSGame game;
	private SpriteBatch batch;
	private float width, height;
	private Image image;
	private GraphicsLoader loader;
	private Texture texture;
	private Label label;
	private float timer;
	private boolean fadeInComplete = false;

	public LoadingScene(PTSGame game, TypeScene targetScene){
		setType(TypeScene.LOADING);
		this.game = game;
		loader = game.getGraphicsLoader();
		timer = 20f;
		
		loader.clearLoader();

		if (targetScene == TypeScene.MENU){
			loader.loadAssetMenuScene();
			System.out.println("game !!!!!");
		} else {
			if (targetScene == TypeScene.GAME){
				loader.loadAssetGameScene();
				System.out.println("game !!!!!");
			}
		}
		
		batch = new SpriteBatch();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		texture = new Texture("marca_loading.png");
		image = new Image(texture);
		image.setBounds(width/2f - 267f, height/2f - 35f, 534f, 95f);
		image.addAction(Actions.sequence(Actions.alpha(0f), Actions.delay(0.2f), Actions.fadeIn(0.5f), Actions.run(new Runnable() {
			@Override
			public void run() {
				fadeInComplete = true;
			}
		})));
		
		label = new Label("Carregando", SkinGenerator.getInstance().getSkin().get("default", LabelStyle.class));
		label.setPosition(width / 12f, height / 10.75f);
		label.addAction(Actions.sequence(Actions.alpha(0f), Actions.delay(0.2f), Actions.fadeIn(0.5f)));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(69/255f, 69/255f, 69/255f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		timer--;
		
		if (label != null && timer == 0){
			String text = label.getText().toString();
			int numPonts = 0;
			for (int i = 0; i < text.length(); i++){
				if (text.charAt(i) == '.'){
					numPonts++;
				}
			}
			if (numPonts < 3){
				text += '.';
			} else {
				text = text.replace("...", "");
			}
			label.setText(text);
			timer = 20f;
		}
		
		if (fadeInComplete && loader.manager.getProgress() == 1f){
			
			label.addAction(Actions.sequence(Actions.delay(3f), Actions.fadeOut(0.6f), Actions.delay(0.3f)));
			image.addAction(Actions.sequence(Actions.delay(3f), Actions.fadeOut(0.6f), Actions.delay(0.3f), Actions.run(new Runnable() {
				@Override
				public void run() {
					game.setScreen(new GameScene(game));
				}
			})));
			
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
			exit();
		}
		
		batch.begin();
		image.act(delta);
		image.draw(batch, 1f);
		label.act(delta);
		label.draw(batch, 1f);
		batch.end();
	}
	
	public void exit(){
		
	}

	@Override
	public void changeMicrophone(Microphone status) {
		
	}
	
}
