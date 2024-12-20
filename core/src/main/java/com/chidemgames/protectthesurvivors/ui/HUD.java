package com.chidemgames.protectthesurvivors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.chidemgames.protectthesurvivors.MathUtils;
import com.chidemgames.protectthesurvivors.gameworld.GameController;
import com.chidemgames.protectthesurvivors.managers.GameManager;
import com.chidemgames.protectthesurvivors.managers.SkinGenerator;
import com.chidemgames.protectthesurvivors.screens.GameScene;

public class HUD {
	
	private Skin skin;
	private Stage stage;
	Table table, tableItens;
	private SpriteBatch batch;
	private GameScene scene;
	private Label textCoin, textSurvivors;
	private GameController controller;
	private Image imgMicrophone, openShop;
	private boolean touchDownM = false;
	private boolean isClick = false;
	private boolean shopOpened = false;
	
	private ShopMenu shop;
	private boolean microphoneStopped = false;
	
	public HUD(GameScene scene){
		this.scene = scene;
	}

	public void createHUD(){
		batch = new SpriteBatch();
		stage = new Stage();
		this.controller = scene.getControler();
		
		scene.getInputs().addProcessor(stage);

		skin = SkinGenerator.getInstance().getSkin();

		table = new Table();
		table.setBounds(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 50);
		table.align(Align.left|Align.top);
		table.padTop(70f).padLeft(50f);
		table.setZIndex(1);
		
		imgMicrophone = new Image((Texture) scene.getLoader().manager.get("m_white.png"));
		imgMicrophone.addListener(new ActorGestureListener(){
			@Override
			public void touchDown(InputEvent event, float x, float y, int count, int button) {
				super.touchDown(event, x, y, count, button);
				
				if (!isClick){
					Vector2 size = new Vector2(imgMicrophone.getWidth(), imgMicrophone.getHeight());
					
					imgMicrophone.addAction(Actions.sequence(
							Actions.sizeTo(size.x * 1.30f, size.y * 1.30f, 0.3f, Interpolation.swingIn),
							Actions.run(new Runnable() {
								@Override
								public void run() {
									if (!microphoneStopped){
										imgMicrophone.setDrawable(new SpriteDrawable(new Sprite((Texture) scene.getLoader().manager.get("m_deactived.png"))));
										microphoneStopped = true;
										scene.getMain().getChangeSRListener().stoppedSpeechRecognizer();
									} else {
										imgMicrophone.setDrawable(new SpriteDrawable(new Sprite((Texture) scene.getLoader().manager.get("m_white.png"))));
										microphoneStopped = false;
										scene.getMain().getChangeSRListener().startSpeechRecognizer();
									}
									
									Vector2 size = new Vector2(imgMicrophone.getWidth(), imgMicrophone.getHeight());
									
									imgMicrophone.addAction(Actions.sequence(
											Actions.sizeTo(size.x / 1.30f, size.y / 1.30f, 0.3f, Interpolation.swingOut),
											Actions.run(new Runnable() {
												@Override
												public void run() {
													touchDownM = false;
													isClick = false;
												}
											})));
									}
							})));
				}
				isClick = true;
			}
		});
		
		Image imgConquistas = new Image((Texture) scene.getLoader().manager.get("conquistas.png"));
		imgConquistas.addListener(new ActorGestureListener(){
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				
			}
		});
		
		table.add(imgMicrophone).size(30, 54).padRight(40f);
		imgMicrophone.setOrigin(imgMicrophone.getX() + 15f, imgMicrophone.getY() + 27f);
		
		table.add(imgConquistas).size(50).padRight(100f);
		
		final TextButton button = new TextButton("Position Grid Disabled", skin);
		table.add(button).padRight(40f);
		
		createCoinsTable();
		createSurvivorsTable();
		createShop();
		createOpenShopTable();
				
		stage.addActor(table);
		
		tableItens = new Table();
		tableItens.setBounds(0, 50f, Gdx.graphics.getWidth(), 50f);
		tableItens.align(Align.center | Align.top);
		tableItens.padTop(10f);
		tableItens.setZIndex(1);
		
		Image img = new Image((Texture) scene.getLoader().manager.get("content_item.png"));
		Image img2 = new Image((Texture) scene.getLoader().manager.get("content_item.png"));
		Image img3 = new Image((Texture) scene.getLoader().manager.get("content_item.png"));

		img3.addListener(getActorGestureListener());
		img.addListener(getActorGestureListener());
		img2.addListener(getActorGestureListener());
		
		tableItens.add(img).size(80);
		tableItens.add(img2).size(80).padLeft(5f);
		tableItens.add(img3).size(80).padLeft(5f);

		stage.addActor(tableItens);
	}
	
	public void changeMicrophoneImg(Texture texture){
		if (!microphoneStopped){
			imgMicrophone.setDrawable(new SpriteDrawable(new Sprite(texture)));
		}
	}
	
	public void createCoinsTable(){
		
		Table coinsTable = new Table();
		coinsTable.setBackground(new SpriteDrawable(new Sprite((Texture) scene.getLoader().manager.get("fundo_item_hud.png"))));
		coinsTable.addAction(Actions.alpha(0.9f));

		Image imgCoin = new Image((Texture) scene.getLoader().manager.get("coin.png"));
		
		textCoin = new Label(String.valueOf(GameManager.getInstance().getMoedas()), skin.get("itens", LabelStyle.class));
		
		coinsTable.add(textCoin).padRight(6f).width(50f);
		coinsTable.add(imgCoin).size(40);
		
		table.add(coinsTable);
	}
	
	public void createSurvivorsTable() {
		
		Table survivorsTable = new Table();
		survivorsTable.setBackground(new SpriteDrawable(new Sprite((Texture) scene.getLoader().manager.get("fundo_item_hud.png"))));
		survivorsTable.addAction(Actions.alpha(0.9f));
		
		Image imgSurvivors = new Image((Texture) scene.getLoader().manager.get("sur_hud.png"));
		
		textSurvivors = new Label(String.valueOf(GameManager.getInstance().getMoedas()), skin.get("itens", LabelStyle.class));
		
		survivorsTable.add(textSurvivors).width(40f);
		survivorsTable.add(imgSurvivors).size(50, 30);
		
		table.add(survivorsTable).padLeft(10f);
		
	}
	
	public void createOpenShopTable(){
		
		Table openShopTable = new Table();
		
		openShop = new Image((Texture) scene.getLoader().manager.get("open_shop.png"));

		openShop.addListener(new ActorGestureListener(){
			@Override
			public void tap(InputEvent event, float x, float y, int pointer, int button) {
				super.tap(event, x, y, pointer, button);
				if (!shopOpened){
					openShop();
				} else {
					closeShop();
				}
			}
		});
		
		openShopTable.add(openShop).size(35, 35);
		openShop.setOrigin(openShop.getX() + 17.25f, openShop.getY() + 17.25f);

		table.add(openShopTable).size(46f, 40f).padLeft(50f);
		
	}
	
	public void openShop(){
		openShop.addAction(Actions.parallel(
				Actions.rotateBy(405f, 0.8f, Interpolation.circle), 
				Actions.color(Color.ORANGE, 0.8f, Interpolation.circle)));
		
		shop.open();
		shopOpened = true;
	}
	
	public void closeShop(){
		openShop.addAction(Actions.parallel(
				Actions.rotateBy(-405f, 0.8f, Interpolation.circle),
				Actions.color(Color.WHITE, 0.8f, Interpolation.circle)));
		
		shop.close();
		shopOpened = false;
	}

	public void createShop(){
		shop = new ShopMenu(stage, scene);
	}
	
	public void resize(int width, int height){
		stage.getViewport().update(width, height, true);
	}
	
	public ActorGestureListener getActorGestureListener(){
		return new ActorGestureListener(){
			
			Image im;
			Tile targetTile;
			
			@Override
			public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
				super.pan(event, x, y, deltaX, deltaY);
				
				controller.positionGridEnable = true;
				
				float X = event.getStageX();
				float Y = event.getStageY();
				
				if (im == null){
					im = new Image((Texture) scene.getLoader().manager.get("destroyer_icone.png"));
					im.setBounds(X, Y, 70f, 70f);
					stage.addActor(im);
				}
				im.setPosition(X - im.getWidth()/2, Y - im.getHeight()/2);

				Vector2 posScre = stage.stageToScreenCoordinates(new Vector2(X, Y));
				Vector2 pos = MathUtils.toWorld(posScre.x, posScre.y, controller.getCamera());
								
				if (controller.tileContactPoint(pos) != null){
					targetTile = controller.getTileByAddress(controller.tileContactPoint(pos));
					if (targetTile.isOccupied()){
						im.setColor(239f/255f, 32f/255f, 57f/255f, 1f);
					} else {
						im.setColor(113f/255f, 193f/255f, 106f/255f, 1f);						
					}
				} else {
					im.setColor(1f, 1f, 1f, 1f);
				}
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				stage.getActors().removeValue(im, true);
				controller.positionGridEnable = false;
				
				float priceTower = 20f;
				
				if (!((GameManager.getInstance().getMoedas() - priceTower) < 0) && GameManager.getInstance().getMoedas() > 0){
					if (targetTile != null && !targetTile.isOccupied()){
						controller.addTower(targetTile);
						GameManager.getInstance().decrement(priceTower, GameManager.MOEDAS);
					}
				}
				im = null;
			}
		};
	}
	
	public void onUpdate(){
		textCoin.setText(String.valueOf(GameManager.getInstance().getMoedas()));
		textSurvivors.setText(String.valueOf(controller.getSurvivors().size()));
		if (controller.getSurvivors().size() < 5){
			textSurvivors.setColor(Color.RED);
		}
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
	
}
