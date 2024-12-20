package com.chidemgames.protectthesurvivors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class MathUtils {
	
	public static Vector2 toWorld(float x, float y, OrthographicCamera camera){
		
		float centerY = (Gdx.graphics.getHeight()/2 * (camera.viewportHeight / Gdx.graphics.getHeight())) + camera.position.y;
		float centerX = (Gdx.graphics.getWidth()/2 * (camera.viewportWidth / Gdx.graphics.getWidth()));
		
		y = centerY - (y * (camera.viewportHeight / Gdx.graphics.getHeight()));
		x = (x * (camera.viewportWidth / Gdx.graphics.getWidth())) - centerX;
		
		return new Vector2(x, y);
	}
	
	public boolean recContainsPoint (float width, float height, float xR, float yR, float x, float y) {
		return xR <= x && xR + width >= x && yR <= y && yR + height >= y;
	}

}
