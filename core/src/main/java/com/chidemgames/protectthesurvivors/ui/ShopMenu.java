package com.chidemgames.protectthesurvivors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.managers.GameManager;
import com.chidemgames.protectthesurvivors.managers.SkinGenerator;
import com.chidemgames.protectthesurvivors.screens.GameScene;

public class ShopMenu {

	private Stage stage;
	private GameScene scene;
	private float width, height;
	private Table table, innerContainer, container, topShop;
	private ScrollPane scrollPane;
	private Skin skin;
	
	public ShopMenu(Stage stage, GameScene scene){
		this.stage = stage;
		this.scene = scene;
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		this.skin = SkinGenerator.getInstance().getSkin();
		
		init();
	}
	
	public void init(){
		
		table = new Table();
		table.setBounds(width, 0, width/2.6f, height - 15f);
		table.setBackground(new SpriteDrawable(new Sprite((Texture) scene.getLoader().manager.get("fundo_shopMenu.png"))));
		
		topShop = new Table();
		Image img3 = new Image((Texture) scene.getLoader().manager.get("content_item.png"));
		topShop.add(img3).size(60);
		
		container = new Table();
		container.setBackground(new SpriteDrawable(new Sprite((Texture) scene.getLoader().manager.get("fundo_towerList.png"))));
		
		innerContainer = new Table();
	    innerContainer.align(Align.left | Align.top);
	    
	    scrollPane = new ScrollPane(innerContainer, skin);
	    
	    container.add(scrollPane).size(width/3.4f, height - 180f);
	    
	    Table tbl = new Table();
	    for (int i = 0; i < 8; i++){
	    	if (i % 2 == 0){
		    	innerContainer.row();
		    	tbl = new Table();
		    	Image img = new Image((Texture) scene.getLoader().manager.get("content_item.png"));
	    		tbl.add(img).size(100f).padRight(20f);
	    		innerContainer.add(tbl).padBottom(20f).expand().align(Align.left | Align.top);
	    	} else {
	    		Image img = new Image((Texture) scene.getLoader().manager.get("content_item.png"));
	    		tbl.add(img).size(100f);
	    		innerContainer.add(tbl).padBottom(20f).expand().align(Align.left | Align.top);
	    		
	    	}
	    }
		
	    table.add(topShop).padTop(70f).padLeft(12f).row();
	    table.add(container).size(width/3.2f, height - 160).padLeft(12f);
		stage.addActor(table);
		
	}
	
	public void open(){
		
		table.addAction(Actions.moveTo(width - width/2.6f, 0, 0.8f, Interpolation.circle));
		
	}
	
	public void close(){
		
		table.addAction(Actions.moveTo(width, 0, 0.8f, Interpolation.circle));
		
	}
	
}
