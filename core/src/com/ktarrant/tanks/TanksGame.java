package com.ktarrant.tanks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ObjectSet;

public class TanksGame extends ApplicationAdapter {
	private static final float DEFAULT_ZOOM = 0.2f;
	
	private AssetManager assetManager_;
	
	private EvenTileSet evenTileset_;
	private TiledMap map_;
	private TiledMapRenderer renderer_;
	private OrthographicCamera camera_;
	private float zoom_;
	
	BitmapFont font_;
	SpriteBatch batch_;
	
	@Override
	public void create () {
		zoom_ = DEFAULT_ZOOM;
		camera_ = new OrthographicCamera();
		
		font_ = new BitmapFont();
		batch_ = new SpriteBatch();
		
		// Load the map TextureAtlas using an AssetManager
		assetManager_ = new AssetManager();
		assetManager_.load("grass.pack", TextureAtlas.class);
		assetManager_.finishLoading();
		
		// Create the EvenTileSet from the TextureAtlas
		TextureAtlas atlas = assetManager_.get("grass.pack", TextureAtlas.class);
		evenTileset_ = EvenTileSet.fromTextureAtlas(atlas);
		
		// Create an example map using the EvenTileSet
		map_ = DemoMaps.makeDemoMap(evenTileset_, 50, 50);
		
		// Create a renderer for the map
		renderer_ = new OrthogonalTiledMapRenderer(map_);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera_.update();
		renderer_.setView(camera_);
		renderer_.render();
		batch_.begin();
		font_.draw(batch_, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch_.end();
	}
	
	@Override
	public void resize(int width, int height) {
		camera_.setToOrtho(false, width / zoom_, height / zoom_);
		camera_.update();
	}
}
