package com.chidemgames.protectthesurvivors.loaders;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GraphicsLoader {

	public final AssetManager manager;
	
	public GraphicsLoader(){
		this.manager = new AssetManager();
	}
	
	public void loadAssetGameScene(){
		manager.load("m_white.png", Texture.class);
		manager.load("m_red.png", Texture.class);
		manager.load("m_green.png", Texture.class);
		manager.load("m_deactived.png", Texture.class);
		manager.load("conquistas.png", Texture.class);
		manager.load("coin.png", Texture.class);
		manager.load("content_item.png", Texture.class);
		manager.load("destroyer_icone.png", Texture.class);
		manager.load("sur_hud.png", Texture.class);
		manager.load("fundo_item_hud.png", Texture.class);
		manager.load("fundo_open_shop.png", Texture.class);
		manager.load("open_shop.png", Texture.class);
		manager.load("fundo_shopMenu.png", Texture.class);
		manager.load("fundo_towerList.png", Texture.class);

	}
	
	public void loadAssetMenuScene(){
		
	}
	
	public void loadAssetGameSaves(){
		manager.load("fundo_gs.png", Texture.class);
		manager.load("divisor_list_hover.png", Texture.class);
		manager.load("divisor_list.png", Texture.class);
		manager.load("user_icon.png", Texture.class);
		manager.load("m_white.png", Texture.class);
		manager.load("m_red.png", Texture.class);
		manager.load("m_green.png", Texture.class);

	}
	
	public void loadAssetSplash(){
		manager.load("ps-icone-m.png", Texture.class);
	}
	
	public static void unloadAssetGameScene(){
		
	}
	
	public static void unloadAssetMenuScene(){
		
	}
	
	public static void unloadAssetGameSaves(){
		
	}
	
	public void unloadAssetSplash(){
		manager.unload("ps-icone-m.png");
	}
	
	public void clearLoader(){
		manager.clear();
	}
	
}
