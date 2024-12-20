package com.chidemgames.protectthesurvivors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.chidemgames.protectthesurvivors.gameobjects.Tower;
import com.chidemgames.protectthesurvivors.gameworld.GameController;
import com.chidemgames.protectthesurvivors.screens.AbstractScene;
import com.chidemgames.protectthesurvivors.screens.GameScene;
import com.chidemgames.protectthesurvivors.ui.Tile;

public class GestureInput implements GestureListener{

	private OrthographicCamera camera;
	private GameController controler;

	public GestureInput(OrthographicCamera camera, GameController controler){
		this.camera = camera;
		this.controler = controler;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		float point = (deltaY * (camera.viewportHeight / Gdx.graphics.getHeight()));

		if (this.camera != null) {

			float heightCam = camera.viewportHeight;

			if (camera.position.y + point <= (((heightCam / 2f) - 0.1f) * 2.5f) - ((heightCam / 2f) - 0.2f) &&
					camera.position.y + point >= (-((heightCam / 2f) - 0.1f)) + ((heightCam / 2f) - 0.2f)){
				camera.position.set(camera.position.x, camera.position.y + point, 0);
				camera.update();
			}
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {

		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

    @Override
    public void pinchStop() {

    }

    @Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		System.out.println("tap -> X: " + x + " Y: " + y);
		Vector2 pos = MathUtils.toWorld(x, y, camera);

		System.out.println("TAP: " + pos.x + " : " + pos.y);

		System.out.println(controler.tileContactPoint(pos));


		return false;
	}
}
