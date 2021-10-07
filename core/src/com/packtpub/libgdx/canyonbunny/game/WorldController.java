package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.until.CameraHelper;
import com.packtpub.libgdx.canyonbunny.until.Constants;

public class WorldController extends InputAdapter {
	
	public Level level;
	public int lives;
	public int score;
	
	public CameraHelper cameraHelper;
	
	private static final String TAG = WorldController.class.getName();
	
	public WorldController() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init() {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		initLevel();
	}
	
	private void initLevel () {
		score = 0;
		level = new Level(Constants.LEVEL_01);
	}
		
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
	}
		
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		switch (keycode) {
		case Keys.SPACE:
			
			break;
		default:
			break;
		}
		return false;
	}
	
	private void handleDebugInput(float deltaTime) {
	  if (Gdx.app.getType() != ApplicationType.Desktop) return;
	  // Selected Sprite Controls
	  float sprMoveSpeed = 5 * deltaTime;
	  if (Gdx.input.isKeyPressed(Keys.A)) 
		  moveSelectedSprite(-sprMoveSpeed, 0);
	  if (Gdx.input.isKeyPressed(Keys.D))
	    moveSelectedSprite(sprMoveSpeed, 0);
	  if (Gdx.input.isKeyPressed(Keys.W)) 
		  moveSelectedSprite(0,sprMoveSpeed);
	  if (Gdx.input.isKeyPressed(Keys.S)) 
		  moveSelectedSprite(0, -sprMoveSpeed);
	  // Camera Controls (move)
	  float camMoveSpeed = 5 * deltaTime;
	  float camMoveSpeedAccelerationFactor = 5;
	  if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
	    camMoveSpeedAccelerationFactor;
	  if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
	    0);
	  if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
	    0);
	  if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
	  if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
	    -camMoveSpeed);
	  if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
	    cameraHelper.setPosition(0, 0);
	  // Camera Controls (zoom)
	  float camZoomSpeed = 1 * deltaTime;
	  float camZoomSpeedAccelerationFactor = 5;
	  if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=
	    camZoomSpeedAccelerationFactor;
	  if (Gdx.input.isKeyPressed(Keys.COMMA))
	    cameraHelper.addZoom(camZoomSpeed);
	  if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(
	    -camZoomSpeed);
	  if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}
	
	private void moveCamera(float x, float y) {
	  x += cameraHelper.getPosition().x;
	  y += cameraHelper.getPosition().y;
	  cameraHelper.setPosition(x, y);
	}
	
	private void moveSelectedSprite (float x, float y) {
//		testSprites[selectedSprite].translate(x, y);
	}
}