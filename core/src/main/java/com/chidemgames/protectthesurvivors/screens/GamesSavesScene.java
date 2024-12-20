package com.chidemgames.protectthesurvivors.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.chidemgames.protectthesurvivors.PTSGame;
import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;
import com.chidemgames.protectthesurvivors.callbacks.IRequestActionCallback;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.loaders.GraphicsLoader;
import com.chidemgames.protectthesurvivors.managers.SkinGenerator;
import com.chidemgames.protectthesurvivors.managers.GameManager;
import com.chidemgames.protectthesurvivors.ui.ItemGameSaved;
import com.chidemgames.protectthesurvivors.ui.Microphone;

public class GamesSavesScene extends AbstractScene {

	
	private Stage stage;
	private Table container, tableTop, innerContainer;
	private Array<ItemGameSaved> savesList = new Array<ItemGameSaved>(); 
	private Skin skin;
	private int width, height;
	private PTSGame game;
	private ScrollPane scrollPane;
	private ItemGameSaved itemActive;
	private Image imgMicrophone;
	private GraphicsLoader loader;
	private List<Jogo> jogos;

	public GamesSavesScene(PTSGame game, List<Jogo> jogos) {
		
		this.game = game;
		this.jogos = jogos;
		setType(TypeScene.GAME_SAVES);

		skin = SkinGenerator.getInstance().getSkin();
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);

		IRequestActionCallback com = game.getRequestAction();
		if (com != null){
			GameManager.getInstance().setComands(com.currentCommands());
		}
		
		loader = game.getGraphicsLoader();
		
		createTableTop();
		createList();
		initButtons();
		
	}
		
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(206f/255f, 164f/255f, 89f/255f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
			exit();
		}
		
		stage.act();
		stage.draw();
	}
	
	public void exit(){
		System.exit(0);
	}
	
	public void createList(){
		
		container = new Table();
		container.setWidth(width/1.6f);
		container.setHeight(height/1.5f);
		container.setPosition(55, 40);
		Sprite fundoList = new Sprite((Texture) loader.manager.get("fundo_gs.png"));
		fundoList.setSize(width, height);
		container.setBackground(new SpriteDrawable(fundoList));
		
	    innerContainer = new Table();
	    innerContainer.align(Align.left | Align.top);
		GameManager gameManager = GameManager.getInstance();

	    if (jogos != null){
		    for (Jogo jogo : jogos){
		    	Table table = addGameItem(gameManager.getNome(), jogo.getIdJogo(), jogo.getTipoDeJogo(), jogo.getIdJogo());
		    	innerContainer.add(table).padLeft(5f).expand().align(Align.left | Align.top);
		  	    innerContainer.row();
		    }
	    }

	    scrollPane = new ScrollPane(innerContainer, skin);
	    
	    container.add(scrollPane).top();
	    stage.addActor(container);
		
	}
	
	public Table addGameItem(String jogador, int id, String tipoJogo, int score){
		
		final ItemGameSaved gameSaved = new ItemGameSaved(jogador + " - " + tipoJogo, id, score, skin, container);
    	final Table table = gameSaved.getTable();
    	
    	table.addListener(new FocusListener() {
	    	@Override
	    	public boolean handle(Event event) {
	    		
	    		String statusEvent = event.toString();
	    				    			
    			if (statusEvent.equals("touchDown")){
    				if (table.getUserObject() == null){
		    			resetAllActives();
		    			Sprite fundo = new Sprite((Texture) loader.manager.get("divisor_list_hover.png"));
		    			
		    			table.setBackground(new SpriteDrawable(fundo));
		    			table.setUserObject("active");
		    			itemActive = gameSaved;
    				} else {
    					Sprite fundo = new Sprite((Texture) loader.manager.get("divisor_list.png"));
		    			table.setBackground(new SpriteDrawable(fundo));
		    			table.setUserObject(null);
	    				itemActive = null;
    				}
    			}
	    		
	    		return super.handle(event);
	    	}
		});
  	    savesList.add(gameSaved);

    	return table;
	}
	
	public void resetAllActives(){
		
		for (ItemGameSaved item : savesList) {
			Sprite fundo = new Sprite((Texture) loader.manager.get("divisor_list.png"));
			item.getTable().setBackground(new SpriteDrawable(fundo));
			item.getTable().setUserObject(null);
		}
		
	}
	
	public void initButtons(){
		
		Table buttons = new Table();
		buttons.setBounds(0, 0, width, height);
		buttons.align(Align.left | Align.top);
		buttons.padTop(150);
		buttons.padLeft(55 + container.getWidth());
		
		ImageButton buttonNew = new ImageButton(skin.get("btNew", ImageButtonStyle.class));
		buttonNew.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				
				GameManager manager = GameManager.getInstance();
				int id = game.getRequestAction().salvarJogo(manager.getToken(), manager.getNome(), 0, 0, 1, "normal");
				
				Table table = addGameItem(manager.getNome(), id, "normal", id);
		    	innerContainer.add(table).padLeft(5f).expand().align(Align.left | Align.top);
		  	    innerContainer.row();
		  	    scrollPane.setScrollY(scrollPane.getMaxY());
				
			}
		});
		buttons.add(buttonNew).padLeft(35).row();
		
		ImageButton buttonRemove = new ImageButton(skin.get("btRemove", ImageButtonStyle.class));
		buttonRemove.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {

				if (itemActive != null){
					GameManager manager = GameManager.getInstance();

					int id = game.getRequestAction().removerJogo(manager.getToken(), itemActive.getId());
					if (id == -101){
						innerContainer.removeActor(itemActive.getTable());
						innerContainer.top().left();
						itemActive = null;
					}
				}
				
			}
		});
		buttons.add(buttonRemove).padLeft(35).row();
		
		ImageButton buttonPlay = new ImageButton(skin);
		buttonPlay.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (itemActive != null){
					stage.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.run(new Runnable() {
						@Override
						public void run() {
							game.setScreen(new LoadingScene(game, TypeScene.GAME));
						}
					})));
				}
			}
		});
		
		buttons.add(buttonPlay).padLeft(35).padTop(10);		
		
		stage.addActor(buttons);
		
	}
	
	public void createTableTop() {
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		tableTop = new Table();
		tableTop.setBounds(0, 0, width, height);
		tableTop.pad(50, 20, 20, 20);
		tableTop.align(Align.left | Align.top);
		
		Image imgUser = new Image((Texture) loader.manager.get("user_icon.png"));
		imgMicrophone = new Image((Texture) loader.manager.get("m_white.png"));
		
		Label labelNome = new Label(GameManager.getInstance().getNome(), skin);

		tableTop.add(imgUser).size(65, 65).padLeft(35f);
		tableTop.add(labelNome);
		tableTop.add(imgMicrophone).size(30, 54).padLeft(width - (imgMicrophone.getWidth() + labelNome.getWidth() + 55));
		
		stage.addActor(tableTop);
		
	}
	
	public void changeMicrophoneImg(Texture texture){
		imgMicrophone.setDrawable(new SpriteDrawable(new Sprite(texture)));
	}
	
	@Override
	public void resize(int width, int height){
		super.resize(width, height);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void changeMicrophone(Microphone status) {
		switch (status){
			case ERRO:
				changeMicrophoneImg((Texture) loader.manager.get("m_red.png"));
				break;
			case LISTENING:
				changeMicrophoneImg((Texture) loader.manager.get("m_white.png"));
				break;
			case RECONHECIDO:
				changeMicrophoneImg((Texture) loader.manager.get("m_green.png"));
				break;
			case STOPPED:
				changeMicrophoneImg((Texture) loader.manager.get("m_white.png"));
				break;
		}		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
}
