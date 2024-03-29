package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.packtpub.libgdx.canyonbunny.objects.BunnyHead;
import com.packtpub.libgdx.canyonbunny.objects.BunnyHead.JUMP_STATE;
import com.packtpub.libgdx.canyonbunny.objects.Feather;
import com.packtpub.libgdx.canyonbunny.objects.GoldCoin;
import com.packtpub.libgdx.canyonbunny.objects.Rock;
import com.packtpub.libgdx.canyonbunny.until.AudioManager;
import com.packtpub.libgdx.canyonbunny.until.CameraHelper;
import com.packtpub.libgdx.canyonbunny.until.Constants;

public class WorldController extends InputAdapter {

  public Level level;
  public int lives;
  public int score;

  public CameraHelper cameraHelper;

  private static final String TAG = WorldController.class.getName();

  //Rectangles for collision detection
  private Rectangle r1 = new Rectangle();
  private Rectangle r2 = new Rectangle();
  private Game game;
  private float timeLeftGameOverDelay;
  
  public WorldController(Game game) {
    // TODO Auto-generated constructor stub
	  this.game = game;
	  init();
  }

  private void init() {
	timeLeftGameOverDelay = 0;
    Gdx.input.setInputProcessor(this);
    cameraHelper = new CameraHelper();
    lives = Constants.LIVES_START;
    initLevel();
  }

  private void initLevel() {
	  score = 0;
	  level = new Level(Constants.LEVEL_01);
	  cameraHelper.setTarget(level.bunnyHead);
  }

  public void update(float deltaTime) {
    handleDebugInput(deltaTime);
    if (isGameOver()) {
		timeLeftGameOverDelay -= deltaTime;
		if (timeLeftGameOverDelay < 0) init();
	} else {
		handleInputGame(deltaTime);
	}
    
    level.update(deltaTime);
    testCollisions();
    cameraHelper.update(deltaTime);
    if (!isGameOver() && isPlayerInWater()) {
    	AudioManager.instance.play(Assets.instance.sounds.liveLost);
    	lives--;
    	if (isGameOver())
    		timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
    	else
    		initLevel();
    }
  }
  
  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
	  // Reset game world
	  if (keycode == Keys.R) {
	    init();
	    Gdx.app.debug(TAG, "Game world resetted");
	  }
	  // Toggle camera follow
	  else if (keycode == Keys.ENTER) {
	    cameraHelper.setTarget(cameraHelper.hasTarget() ?
	      null : level.bunnyHead);
	    Gdx.app.debug(TAG, "Camera follow enabled: " +
	      cameraHelper.hasTarget());
	  }
	  return false;
  }

  private void handleDebugInput(float deltaTime) {
    if (Gdx.app.getType() != ApplicationType.Desktop) return;
    if (!cameraHelper.hasTarget(level.bunnyHead)) {
        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
          camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.LEFT))
          moveCamera(-camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
          moveCamera(camMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.UP))
          moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.DOWN))
          moveCamera(0, -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
          cameraHelper.setPosition(0, 0);
      }
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
  
  private void handleInputGame(float deltaTime) {
	  if (cameraHelper.hasTarget(level.bunnyHead)) {
	    // Player Movement
	    if (Gdx.input.isKeyPressed(Keys.LEFT)) {
	      level.bunnyHead.velocity.x = -level.bunnyHead.terminalVelocity.x;
	    } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
	      level.bunnyHead.velocity.x =
	        level.bunnyHead.terminalVelocity.x;
	    } else {
	      // Execute auto-forward movement on non-desktop platform
	      if (Gdx.app.getType() != ApplicationType.Desktop) {
	        level.bunnyHead.velocity.x =
	          level.bunnyHead.terminalVelocity.x;
	      }
	    }
	    // Bunny Jump
	    if (Gdx.input.isTouched() ||
	      Gdx.input.isKeyPressed(Keys.SPACE)) {
	      level.bunnyHead.setJumping(true);
	    } else {
	      level.bunnyHead.setJumping(false);
	    }
	  }
	}

  private void moveCamera(float x, float y) {
    x += cameraHelper.getPosition().x;
    y += cameraHelper.getPosition().y;
    cameraHelper.setPosition(x, y);
  }

  private void moveSelectedSprite(float x, float y) {
    //		testSprites[selectedSprite].translate(x, y);
  }

  private void testCollisions() {
    r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y,
      level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
    // Test collision: Bunny Head <-> Rocks
    for (Rock rock: level.rocks) {
      r2.set(rock.position.x, rock.position.y, rock.bounds.width,
        rock.bounds.height);
      if (!r1.overlaps(r2)) continue;
      onCollisionBunnyHeadWithRock(rock);
      // IMPORTANT: must do all collisions for valid
      // edge testing on rocks.
    }
    // Test collision: Bunny Head <-> Gold Coins
    for (GoldCoin goldcoin: level.goldcoins) {
      if (goldcoin.collected) continue;
      r2.set(goldcoin.position.x, goldcoin.position.y,
        goldcoin.bounds.width, goldcoin.bounds.height);
      if (!r1.overlaps(r2)) continue;
      onCollisionBunnyWithGoldCoin(goldcoin);
      break;
    }
    // Test collision: Bunny Head <-> Feathers
    for (Feather feather: level.feathers) {
      if (feather.collected) continue;
      r2.set(feather.position.x, feather.position.y,
        feather.bounds.width, feather.bounds.height);
      if (!r1.overlaps(r2)) continue;
      onCollisionBunnyWithFeather(feather);
      break;
    }
  }

  private void onCollisionBunnyHeadWithRock(Rock rock) {
    BunnyHead bunnyHead = level.bunnyHead;
    float heightDifference = Math.abs(bunnyHead.position.y -
      (rock.position.y + rock.bounds.height));
    if (heightDifference > 0.25f) {
      boolean hitRightEdge = bunnyHead.position.x > (
        rock.position.x + rock.bounds.width / 2.0f);
      if (hitRightEdge) {
        bunnyHead.position.x = rock.position.x + rock.bounds.width;
      } else {
        bunnyHead.position.x = rock.position.x -
          bunnyHead.bounds.width;
      }
      return;
    }
    switch (bunnyHead.jumpState) {
    case GROUNDED:
      break;
    case FALLING:
    case JUMP_FALLING:
      bunnyHead.position.y = rock.position.y +
        bunnyHead.bounds.height + bunnyHead.origin.y;
      bunnyHead.jumpState = JUMP_STATE.GROUNDED;
      break;
    case JUMP_RISING:
      bunnyHead.position.y = rock.position.y +
        bunnyHead.bounds.height + bunnyHead.origin.y;
      break;
    }
  }
  private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) {
	AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
    goldcoin.collected = true;
    score += goldcoin.getScore();
    Gdx.app.log(TAG, "Gold coin collected");
  }
  private void onCollisionBunnyWithFeather(Feather feather) {
	AudioManager.instance.play(Assets.instance.sounds.pickupFeather);
    feather.collected = true;
    score += feather.getScore();
    level.bunnyHead.setFeatherPowerup(true);
    Gdx.app.log(TAG, "Feather collected");
  }
  
  public boolean isGameOver () {
	  return lives < 0;
  }
  
  public boolean isPlayerInWater () {
	  return level.bunnyHead.position.y < -5;
  }
}