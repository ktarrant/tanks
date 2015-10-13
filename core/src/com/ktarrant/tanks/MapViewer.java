package com.ktarrant.tanks;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.ktarrant.tanks.MapStage.TiledMapActorEventListener;
import com.ktarrant.tanks.MapStage.TiledMapActorEventType;
import com.ktarrant.tanks.maps.DiamondSquareProcessor;
import com.ktarrant.tanks.maps.TerrainLayer;
import com.ktarrant.tanks.maps.TerrainTileSet;
import com.ktarrant.tanks.maps.ValueMap;
import com.ktarrant.tanks.menu.HudStage;

public class MapViewer extends ScreenAdapter {

	protected AssetManager assetManager;
	protected TiledMap map;
	protected TiledMapRenderer renderer;
	protected BitmapFont font;
	protected SpriteBatch batch;
	protected InputMultiplexer multiplexer;
	public MapStage mapStage;
	public HudStage hudStage;
	
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
	
	private void loadHudStage() {
        this.assetManager.load("uiskin.json", Skin.class);
        this.assetManager.finishLoading();
		Skin uiskin = this.assetManager.get("uiskin.json", Skin.class);
		this.hudStage = new HudStage(uiskin);
        this.hudStage.setDebugAll(true);
	}
	
	private void loadMapStage() {
		// Load a TerrainTileSet from a TextureAtlas
		assetManager.load("grass.pack", TextureAtlas.class);
		assetManager.finishLoading();
		TextureAtlas mapAtlas = assetManager.get("grass.pack", 
				TextureAtlas.class);
		Skin skin = new Skin(mapAtlas);
		
		// Build a TerrainLayer from the randomly generated ValueMap
		// and an EvenTileSet
		TerrainTileSet tileset = new TerrainTileSet(skin);
		TerrainLayer layer = new TerrainLayer(
				32, 32, 128, 128, tileset);
		layer.setValueMap(generateRandomMap());
		layer.update();
		
		// Add the primary terrain values to the clickable HUD window
		Integer baseId = tileset.labelToBaseIdMap.get("Grass");
		this.hudStage.addTileButton(tileset, (baseId << 16) + 13);
		baseId = tileset.labelToBaseIdMap.get("Dirt");
		this.hudStage.addTileButton(tileset, (baseId << 16) + 4);
		
		// Create a tiled map and add the layer to it
		this.map = new TiledMap();
		this.map.getLayers().add(layer);
		
		this.mapStage = new MapStage(
				new ExtendViewport(
						Gdx.graphics.getWidth(), 
						Gdx.graphics.getHeight()),
				map);
	}
	
	public MapViewer(AssetManager assetManager){
		// Initialize objects
		this.assetManager = assetManager;
		this.font = new BitmapFont();
		this.batch = new SpriteBatch();
		
		// Load the menu Textures
		this.loadHudStage();
		
		// Load the map view
		this.loadMapStage();
		
		// Link the map to the hud
		this.mapStage.registerTiledMapActorEventListener(
				new TiledMapActorEventListener() {

					@Override
					public void handleActorEvent(
							TiledMapActorEventType eventType,
							TerrainLayerActor actor) {
						hudStage.updateSelection(actor);
					}
			
		});
		
		//Create the input multiplexer
		multiplexer = new InputMultiplexer(hudStage, mapStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		// Create a renderer for the map
		OrthographicCamera camera = (OrthographicCamera) mapStage.getCamera();
		this.renderer = new OrthogonalTiledMapRenderer(this.map);
		this.renderer.setView(camera);
	}
	
	@Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the map camera
        OrthographicCamera camera = (OrthographicCamera) mapStage.getCamera();
        camera.update();
     
        // Update all the stages together
        mapStage.act(delta);
        hudStage.updateParam("FPS", 
        		String.valueOf(Gdx.graphics.getFramesPerSecond()));
        hudStage.act(delta);
        
        // Draw the map
        this.renderer.setView(camera);
        renderer.render();
        
        // Draw the HUD
        hudStage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		mapStage.getViewport().update(width, height);
		hudStage.getViewport().update(width, height);
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
