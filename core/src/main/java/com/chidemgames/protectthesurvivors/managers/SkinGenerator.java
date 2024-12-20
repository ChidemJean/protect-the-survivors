package com.chidemgames.protectthesurvivors.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class SkinGenerator {

	private static SkinGenerator INSTANCE = new SkinGenerator();
	private Skin skin;
	
	public void loadSkin(){
		
		getInstance().skin = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		getInstance().skin.add("white", new Texture(pixmap));
		getInstance().skin.add("btPLay", new Texture("button_play.png"));
		getInstance().skin.add("btPlayOver", new Texture("button_play_hover.png"));
		
		getInstance().skin.add("btNew", new Texture("btNew.png"));
		getInstance().skin.add("btNewOver", new Texture("btNew_over.png"));
		
		getInstance().skin.add("btRemove", new Texture("btRemove_over.png"));
		getInstance().skin.add("btRemoveOver", new Texture("btRemove.png"));
		
		Pixmap pixmap2 = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.valueOf("f3e186"));
		pixmap.fill();
		
		getInstance().skin.add("yellow", new Texture(pixmap2));
		
		BitmapFont font = new BitmapFont(Gdx.files.internal("exo.fnt"));
		
		getInstance().skin.add("default", font);
		
		TextButtonStyle imgButtonStyle = new TextButtonStyle();
		imgButtonStyle.font = getInstance().skin.getFont("default");
		imgButtonStyle.fontColor = Color.WHITE;
		
		getInstance().skin.add("default", imgButtonStyle);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = getInstance().skin.getFont("default");

		getInstance().skin.add("default", labelStyle);
		
		LabelStyle labelStyleItens = new LabelStyle();
		labelStyleItens.font = getInstance().skin.getFont("default");
		labelStyleItens.fontColor = Color.valueOf("f39c00");

		getInstance().skin.add("itens", labelStyleItens);
		
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = getInstance().skin.getFont("default");
		textFieldStyle.fontColor = Color.valueOf("f39c12");
		
		getInstance().skin.add("default", textFieldStyle);
		
		ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle();
		scrollPaneStyle.vScroll = getInstance().skin.newDrawable(new SpriteDrawable(new Sprite(new Texture("scroll.png"))));
		scrollPaneStyle.vScrollKnob = getInstance().skin.newDrawable(new SpriteDrawable(new Sprite(new Texture("scrollKnob.png"))));
		
		getInstance().skin.add("default", scrollPaneStyle);
		
		ImageButtonStyle imgT = new ImageButtonStyle();
		imgT.up = getInstance().skin.newDrawable("btPLay");
		imgT.down = getInstance().skin.newDrawable("btPlayOver");
		
		getInstance().skin.add("default", imgT);
		
		ImageButtonStyle btNew = new ImageButtonStyle();
		btNew.up = getInstance().skin.newDrawable("btNew");
		btNew.down = getInstance().skin.newDrawable("btNewOver");
		
		getInstance().skin.add("btNew", btNew);
		
		ImageButtonStyle btRemove = new ImageButtonStyle();
		btRemove.up = getInstance().skin.newDrawable("btRemove");
		btRemove.down = getInstance().skin.newDrawable("btRemoveOver");
		
		getInstance().skin.add("btRemove", btRemove);
		
		
		
	}
	
	public Skin getSkin(){
		return getInstance().skin;
	}
	
	public static SkinGenerator getInstance(){
		return INSTANCE;
	}
	
}
