package com.ktarrant.tanks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.ktarrant.tanks.MapViewer;

public class TanksGame extends Game {
	private AssetManager assetManager;
	private MapViewer mapViewer;
	
	@Override
	public void create () {
		// First create the asset manager
		this.assetManager = new AssetManager();
		
		// Load the MapViewer, give it the AssetManager for loading.
		this.mapViewer = new MapViewer(assetManager);
		
		Gdx.input.setInputProcessor(this.mapViewer.mapStage);
		this.setScreen(this.mapViewer);
	}

	@Override
	public void render () {
		super.render();
	}
}
