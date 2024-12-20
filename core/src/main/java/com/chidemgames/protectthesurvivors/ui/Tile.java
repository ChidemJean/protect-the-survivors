package com.chidemgames.protectthesurvivors.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chidemgames.protectthesurvivors.gameobjects.Tower;

public class Tile {
	
	private boolean isOccupied = false;
	private Tower tower;
	private String address;
	private float width, height, x, y;
	private Sprite sprite;
	private Texture texture;
	private Color color;
	
	public Tile(float x, float y, String address, float width, float height, Texture texture){
		this.width = width;
		this.height = height;
		this.address = address;
		this.y = y;
		this.x = x;
		this.texture = texture;
		
		if (this.texture != null){
			sprite = new Sprite(this.texture);
			sprite.setBounds(0, 0, width, height);
			this.color = sprite.getColor();
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
		if (isOccupied){
			sprite.setColor(239f, 32f, 57f, 0.8f);
		} else {
			sprite.setColor(color);
		}
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
		if (tower != null){
			setOccupied(true);
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void render(float deltaTime, SpriteBatch batch){
		
		sprite.setPosition(x, y);
		sprite.draw(batch);
		
	}

}
