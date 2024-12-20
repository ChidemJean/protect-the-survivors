package com.chidemgames.protectthesurvivors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chidemgames.protectthesurvivors.callbacks.IChangeSpeechRecognizerCallback;
import com.chidemgames.protectthesurvivors.callbacks.IRequestActionCallback;
import com.chidemgames.protectthesurvivors.loaders.GraphicsLoader;
import com.chidemgames.protectthesurvivors.managers.GameManager;
import com.chidemgames.protectthesurvivors.managers.SkinGenerator;
import com.chidemgames.protectthesurvivors.screens.GameScene;
import com.chidemgames.protectthesurvivors.screens.LoadingScene;
import com.chidemgames.protectthesurvivors.screens.SplashScene;

public class PTSGame extends AbstractGame {

	private IRequestActionCallback requestAction;
	private IChangeSpeechRecognizerCallback changeSR;
	private GraphicsLoader loader;
	
	public IChangeSpeechRecognizerCallback getChangeSRListener() {
		return changeSR;
	}

	public void setChangeSRListener(IChangeSpeechRecognizerCallback changeSR) {
		this.changeSR = changeSR;
	}

	public IRequestActionCallback getRequestAction() {
		return requestAction;
	}

	public void setRequestActionListener(IRequestActionCallback requestAction) {
		this.requestAction = requestAction;
	}

	public enum TypeScene {
		SPLASH,
		GAME,
		GAME_SAVES,
		MENU,
		OPTIONS,
		LOADING
	}
	
   public enum StateGame {
    	RUNNING,
    	STOPPED
   }
	
   	public GraphicsLoader getGraphicsLoader(){
   		return this.loader;
   	}
   
	@Override
	public void create() {
		
		this.loader = new GraphicsLoader();
		SkinGenerator.getInstance().loadSkin();
		this.setScreen(new SplashScene(this));
	}
	
	@Override
	public void render() {
		super.render();
		this.loader.manager.update();
	}
}
