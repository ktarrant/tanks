package com.ktarrant.tanks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ObjectSet;
import com.ktarrant.tanks.maps.TerrainTileSet;
import com.ktarrant.tanks.menu.HudScreen;
import com.ktarrant.tanks.test.HudScreenTest;
import com.ktarrant.tanks.test.MapViewer;
import com.ktarrant.tanks.test.TerrainLayerTest;

public class TanksGame extends Game {
	private AssetManager assetManager;
	private MapViewer mapViewer;
	private HudScreen hudScreen;
	
	BitmapFont font_;
	SpriteBatch batch_;
	
	@Override
	public void create () {
		// First create the asset manager
		this.assetManager = new AssetManager();
		
		// Load the MapViewer, give it the AssetManager for loading.
		this.mapViewer = TerrainLayerTest.createDemoMapViewer(assetManager);
		// Load the HudScreen, give it the AssetManaget for loading.
		this.hudScreen = HudScreenTest.createDemoHudScreen(assetManager);
		
		font_ = new BitmapFont();
		batch_ = new SpriteBatch();
		
		Gdx.input.setInputProcessor(this.mapViewer);
		this.setScreen(this.mapViewer);
	}

	@Override
	public void render () {
		super.render();
	}
}
