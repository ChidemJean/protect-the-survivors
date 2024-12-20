package com.chidemgames.protectthesurvivors.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.chidemgames.protectthesurvivors.managers.SkinGenerator;
import com.chidemgames.protectthesurvivors.ui.Microphone;

public class LoginScene extends AbstractScene {


	private Stage stage;
	private Skin skin;
	private Table table;
	private TextField field;
	
	public LoginScene(){
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = SkinGenerator.getInstance().getSkin();
		
		table = new Table(); 
		table.setFillParent(true);
		
		field = new TextField("", skin);
		field.setSize(250f, 50f);
		field.setMessageText("Nome");
		field.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				System.out.println("Text: " + textField.getText());
				textField.setDebug(true);
			}
		});
		
		table.add(field);
		table.debug();
		stage.addActor(field);
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void changeMicrophone(Microphone status) {
		
	}
	

}
