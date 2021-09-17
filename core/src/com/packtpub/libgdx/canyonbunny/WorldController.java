package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class WorldController extends InputAdapter{
	private static final String TAG =
	        WorldController.class.getName();
	public Sprite[] testSprites;
	public int selectedSprite;
	public Sprite ball;
	
    public WorldController() {
    	init();
    }
    private void init() {
		initTestObjects();
		initAnimals();
		Gdx.input.setInputProcessor(this);
	}
    private void initAnimals() {
    	Texture ballTexture = new Texture("balll.jpg");
    	ball = new Sprite (ballTexture);
    	ball.setSize(2, 2);
    	ball.setPosition(-2, -2);
    }
	
	private void initTestObjects() {
		testSprites = new Sprite[5];
		int width = 32;
		int height = 32;
		
		Pixmap pixmap = createProceduralPixmap(width, height);
		Texture texture = new Texture(pixmap);
		
		for (int i = 0; i < testSprites.length; i++) {
			Sprite spr = new Sprite(texture);
			spr.setSize(1, 1);
	        // Set origin to sprite's center
	        spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
	        // Calculate random position for sprite
	        float randomX = MathUtils.random(-2.0f, 2.0f);
	        float randomY = MathUtils.random(-2.0f, 2.0f);
	        spr.setPosition(randomX, randomY);
	        // Put new sprite into array
	        testSprites[i] = spr;
		}
		selectedSprite = 0;
	}
	
	private Pixmap createProceduralPixmap(int width, int height) {
	    Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
	    // Fill square with red color at 50% opacity
	    pixmap.setColor(1, 0, 0, 0.5f);
	    pixmap.fill();
	    // Draw a yellow-colored X shape on square
	    pixmap.setColor(1, 1, 0, 1);
	    pixmap.drawLine(0, 0, width, height);
	    pixmap.drawLine(width, 0, 0, height);
	    // Draw a cyan-colored border around square
	    pixmap.setColor(0, 1, 1, 1);
	    pixmap.drawRectangle(0, 0, width, height);
	    return pixmap;
	}
	
	public void update(float deltaTime) {
		updateTestObjects(deltaTime);
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
