package com.chidemgames.protectthesurvivors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class ItemGameSaved {

	private String nameGame;
	private int idGame;
	private int score;
	private Table table, container;
	private Skin skin;
	
	public ItemGameSaved(String nameGame, int idGame, int score, Skin skin, Table container){
		this.nameGame = nameGame;
		this.idGame = idGame;
		this.score = score;
		this.skin = skin;
		this.container = container;
		
		createTable();
	}
	
	public void createTable(){
		
		table = new Table(skin);
		
	    table.add(new Label("", skin)).width(25f).expandY().fillY();
	    table.add(new Label(nameGame, skin)).width(container.getWidth() - 150).expandY().fillY();
	    table.add(new Image(new Texture(Gdx.files.internal("star.png")))).size(40).expandY().fillY();
	    table.add(new Label(String.valueOf(score), skin)).expandY().fillY().padLeft(6f).padTop(4f).width(70);
	    
	    Sprite fundo = new Sprite(new Texture(Gdx.files.internal("divisor_list.png")));
	    table.setBackground(new SpriteDrawable(fundo));
	    
	    addChangeListener();
	}
	
	public int getId(){
		return idGame;
	}
	
	public void addChangeListener(){
		
	}
	
	public Table getTable(){
		if (this.table != null){
			return this.table;
		} else {
			createTable();
			return this.table;
		}
	}
	
}
