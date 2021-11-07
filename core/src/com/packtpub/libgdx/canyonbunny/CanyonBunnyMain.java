package com.packtpub.libgdx.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.packtpub.libgdx.canyonbunny.game.Assets;
import com.packtpub.libgdx.canyonbunny.screens.MenuScreen;
import com.packtpub.libgdx.canyonbunny.until.AudioManager;
import com.packtpub.libgdx.canyonbunny.until.GamePreferences;

public class CanyonBunnyMain extends Game {

	@Override 	
	public void create() {
		// TODO Auto-generated method stub
		// Set Libgdx log level
	    Gdx.app.setLogLevel(Application.LOG_DEBUG);
	    // Load assets
	    Assets.instance.init(new AssetManager());
	 // Load preferences for audio settings and start playing music
	     GamePreferences.instance.load();
	     AudioManager.instance.play(Assets.instance.music.song01);
	     
	    //Start Game 
		setScreen(new MenuScreen(this));	
	}
	
}