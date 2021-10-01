package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AssetGoldCoin {
	public final AtlasRegion goldCoin;
    public AssetGoldCoin (TextureAtlas atlas) {
        goldCoin = atlas.findRegion("item_gold_coin");
}
}
