package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.canyonbunny.until.Constants;


public class WorldRenderer implements Disposable{
	 private static OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    public WorldRenderer(WorldController worldController) {
    	this.worldController = worldController;
    	init();
    }
    
    private void init() {
    	batch = new SpriteBatch();
    	camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    	camera.position.set(0, 0, 0);
    	camera.update();
    }
    
    public void render() {
    	renderObjects();
    }
    
    private void renderObjects() {
    	batch.setProjectionMatrix(camera.combined);
    	batch.begin();
    	for (Sprite spr : worldController.testSprites) {
			spr.draw(batch);
		}
    	worldController.ball.draw(batch);
    	batch.end();
    }
    
    public static void resize(int width, int height) {
    	camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
    	camera.update();
    }
    
    @Override 
    public void dispose() {
    	batch.dispose();
    }
}
