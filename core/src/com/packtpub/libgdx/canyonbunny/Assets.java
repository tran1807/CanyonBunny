package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.canyonbunny.until.Constants;

public class Assets implements Disposable, AssetErrorListener {

  public static final String TAG = Assets.class.getName();

  public static final Assets instance = new Assets();

  private AssetManager assetManager;

  private Assets() {}
  
  public AssetBunny bunny;
  public AssetRock rock;
  public AssetGoldCoin goldCoin;
  public AssetFeather feather;
  public AssetLevelDecoration levelDecoration;

  public void init(AssetManager assetManager) {
    this.assetManager = assetManager;
    // set asset manager error handler
    assetManager.setErrorListener(this);
    // load texture atlas
    assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
      TextureAtlas.class);
    // start loading assets and wait until finished
    assetManager.finishLoading();
    
    TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
    // enable texture filtering for pixel smoothing
    for (Texture t : atlas.getTextures()) {
         t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
    
    bunny = new AssetBunny(atlas);
    rock = new AssetRock(atlas);
    goldCoin = new AssetGoldCoin(atlas);
    feather = new AssetFeather(atlas);
    levelDecoration = new AssetLevelDecoration(atlas);
    
    Gdx.app.debug(TAG, "# of assets loaded: " +
      assetManager.getAssetNames().size);
    for (String a: assetManager.getAssetNames())
      Gdx.app.debug(TAG, "asset: " + a);
  }

  @Override
  public void dispose() {
    assetManager.dispose();
  }
  
  public void error(String filename, Class type,
    Throwable throwable) {
    Gdx.app.error(TAG, "Couldn't load asset '" +
      filename + "'", (Exception) throwable);
  }
  
  @Override
  public void error(AssetDescriptor asset, Throwable throwable) {
    Gdx.app.error(TAG, "Couldn't load asset '" +
      asset.fileName + "'", (Exception) throwable);
  }
  
  public class AssetLevelDecoration {
      public final AtlasRegion cloud01;
      public final AtlasRegion cloud02;
      public final AtlasRegion cloud03;
      public final AtlasRegion mountainLeft;
      public final AtlasRegion mountainRight;
      public final AtlasRegion waterOverlay;
      public AssetLevelDecoration (TextureAtlas atlas) {
          cloud01 = atlas.findRegion("cloud01");
          cloud02 = atlas.findRegion("cloud02");
          cloud03 = atlas.findRegion("cloud03");
          mountainLeft = atlas.findRegion("mountain_left");
          mountainRight = atlas.findRegion("mountain_right");
          waterOverlay = atlas.findRegion("water_overlay");
      } 
  }
}