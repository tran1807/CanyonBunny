package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.until.CameraHelper;

public class WorldController extends InputAdapter {
	
	public Sprite[] testSprites;
	public int selectedSprite;
	
	public CameraHelper cameraHelper;
	public Object sprite;
	public Object dragon;
	
	private static final String TAG = WorldController.class.getName();
	
	public WorldController() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init() {
		initTestObjects();
		cameraHelper = new CameraHelper();
		Gdx.input.setInputProcessor(this);
	}
	
	private void initTestObjects() {
		testSprites = new Sprite[5];
		// Create a list of texture regions
       Array<TextureRegion> regions = new Array<TextureRegion>();
       regions.add(Assets.instance.bunny.head);
       regions.add(Assets.instance.feather.feather);
       regions.add(Assets.instance.goldCoin.goldCoin);
       for (int i = 0; i < testSprites.length; i++) {
           Sprite spr = new Sprite(regions.random());
           // Define sprite size to be 1m x 1m in game world
           spr.setSize(1, 1);
           // Set origin to sprite's center
           spr.setOrigin(spr.getWidth() / 2.0f,
             spr.getHeight() / 2.0f);
           // Calculate random position for sprite
           float randomX = MathUtils.random(-2.0f, 2.0f);
           float randomY = MathUtils.random(-2.0f, 2.0f);
           spr.setPosition(randomX, randomY);
           // Put new sprite into array
           testSprites[i] = spr;
      }
      // Set first sprite as selected one
      selectedSprite = 0;
		
	}
	
	public void update(float deltaTime) {
		updateTestObjects(deltaTime);
		cameraHelper.update(deltaTime);
	}
	
	private void updateTestObjects(float deltaTime) {
		// TODO Auto-generated method stub
		float rotation = testSprites[selectedSprite].getRotation();
		rotation += 90 * deltaTime;
		rotation %= 360;
		testSprites[selectedSprite].setRotation(rotation);
	}
	
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		switch (keycode) {
		case Keys.SPACE:
			Gdx.app.log("Key Press", "Phim Space");
			selectedSprite += 1;
			if(selectedSprite > 4 ) selectedSprite = 0;
			break;
		default:
			break;
		}
		return false;
	}
}
