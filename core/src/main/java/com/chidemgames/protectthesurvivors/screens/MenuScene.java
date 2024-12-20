package com.chidemgames.protectthesurvivors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;
import com.chidemgames.protectthesurvivors.managers.SkinGenerator;
import com.chidemgames.protectthesurvivors.ui.Microphone;

public class MenuScene extends AbstractScene {

	
	private Stage stage;
	private Table tableTop, tableBottom, tableLeft, tableRight;
	private Skin skin;

	public MenuScene() {
		
		setType(TypeScene.MENU);

		skin = SkinGenerator.getInstance().getSkin();
		stage = new Stage();
		
		createTableTop();
		
	}
		
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act();
		stage.draw();
	}
	
	public void createTableTop() {
		
		tableTop = new Table();
		tableTop.setFillParent(true);
		tableTop.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		tableTop.pad(20, 20, 20, 20);
		tableTop.align(Align.center | Align.top);
		
		Label label = new Label("N�vel", skin);
		tableTop.add(label);

		tableTop.debug();
		stage.addActor(tableTop);
		
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
	}
	
	public void createTableBottom() {
			
	}
	
	public void createTableLeft() {
		
	}
	
	public void createTableRight() {
		
	}

	@Override
	public void changeMicrophone(Microphone status) {
		
	}
	
	
}
