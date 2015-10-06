package com.ktarrant.tanks;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ktarrant.tanks.maps.DiamondSquareProcessor;
import com.ktarrant.tanks.maps.TerrainLayer;
import com.ktarrant.tanks.maps.TerrainTileSet;
import com.ktarrant.tanks.maps.ValueMap;

public abstract class MapViewer extends ScreenAdapter {

	protected AssetManager assetManager;
	protected TiledMap map;
	protected TiledMapRenderer renderer;
	protected BitmapFont font;
	protected SpriteBatch batch;
	public MapStage mapStage;
	
	private ValueMap<Integer> generateRandomMap() {
		// Build a randomly generated ValueMap
		ValueMap<Float> valueMap = new ValueMap<Float>(32, 32);
		valueMap.fillWith(0.0f);
		DiamondSquareProcessor.seedMapRandom(valueMap, 
				0.0f, 1.0f, 0.0f, 1.0f);
		DiamondSquareProcessor.diamondSquare(valueMap, 
				0.0f, 1.0f, 0.0f, 1.0f,
				1.0f, 1.0f);
		
		// Convert Float-based ValueMap to Integer-based ValueMap
		ValueMap<Integer> newMap =
				new ValueMap<Integer>(valueMap.width, valueMap.height);
		for (int i = 0; i < valueMap.size; i++) {
			int value = Math.round(valueMap.get(i));
			assert ((value == 0) || (value == 1));
			newMap.add(value);
		}
		return newMap;
	}

	public void load() {
		// Load an EvenTileSet from a TextureAtlas
		assetManager.load("grass.pack", TextureAtlas.class);
		assetManager.finishLoading();
		
		// Build a TerrainLayer from the randomly generated ValueMap
		// and an EvenTileSet
		TextureAtlas mapAtlas = assetManager.get("grass.pack", 
				TextureAtlas.class);
		TerrainTileSet tileset = new TerrainTileSet(mapAtlas);
		TerrainLayer layer = new TerrainLayer(
				32, 32, 128, 128, tileset);
		layer.setValueMap(generateRandomMap());
		layer.update();
		
		// Create a tiled map and add the layer to it
		this.map = new TiledMap();
		this.map.getLayers().add(layer);
	}

	public void doAction(int actionId) {
		if (actionId == 1) {
			TerrainLayer layer = (TerrainLayer) this.map.getLayers().get(0);
			layer.setValueMap(generateRandomMap());
			layer.update();
		}
	}
	
	private void loadMenu() {
		// Create a NinePatch from the provided texture
		this.assetManager.load("sample.png", Texture.class);
		this.assetManager.finishLoading();
		Texture patchTex = this.assetManager.get("sample.png", Texture.class);
		int patchWidth = patchTex.getWidth() / 3;
		int patchHeight = patchTex.getHeight() / 3;
		NinePatch patch = new NinePatch(patchTex, 
				patchWidth, 2 * patchWidth,
				patchHeight, 2 * patchHeight);
		
		
	}
	
	public MapViewer(AssetManager assetManager){
		// Initialize objects
		this.assetManager = assetManager;
		this.font = new BitmapFont();
		this.batch = new SpriteBatch();
		this.mapStage = new MapStage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		// Load the menu Textures
		this.loadMenu();
		
		load();
		
		// Create a renderer for the map
		this.renderer = new OrthogonalTiledMapRenderer(this.map);
	}
	
	@Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        OrthographicCamera camera = (OrthographicCamera) mapStage.getCamera();
        camera.update();
        mapStage.act();
        renderer.setView(camera);
        renderer.render();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		OrthographicCamera camera = (OrthographicCamera) mapStage.getCamera();
		mapStage.getViewport().update(width, height);
	}
	
	/**
	 * @return the assetManager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * @return the map
	 */
	public TiledMap getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(TiledMap map) {
		this.map = map;
	}
}
