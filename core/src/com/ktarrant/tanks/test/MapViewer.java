package com.ktarrant.tanks.test;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.ktarrant.tanks.EvenTileSet;

public abstract class MapViewer extends ApplicationAdapter implements InputProcessor {
	public static final float DEFAULT_ZOOM = 0.6f;
	public static final float DEFAULT_CAMERA_TRANSLATE_STEP = 32.0f;
	public static final float DEFAULT_CAMERA_ZOOM_STEP = 1.0f;
	
	protected AssetManager assetManager;
	protected TiledMap map;
	protected TiledMapRenderer renderer;
	protected OrthographicCamera camera;
	protected BitmapFont font;
	protected SpriteBatch batch;
	
	private float cameraTranslateStep;
	private float cameraZoomStep;
	
	public abstract void load();
	
	@Override
	public void create () {
		// Initialize objects
		this.assetManager = new AssetManager();
		this.camera = new OrthographicCamera();
		this.font = new BitmapFont();
		this.batch = new SpriteBatch();
		this.cameraTranslateStep = DEFAULT_CAMERA_TRANSLATE_STEP;
		this.cameraZoomStep = DEFAULT_CAMERA_ZOOM_STEP;
		
		load();
		
		Gdx.input.setInputProcessor(this);
		
		// Create a renderer for the map
		this.renderer = new OrthogonalTiledMapRenderer(this.map);
	}
	
	@Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.update();
	}
	
	/**
	 * Tells the application to exit in a given number of seconds.
	 * @param seconds Number of seconds until exit.
	 */
	public void quitIn(float seconds) {
		Timer timer = new Timer();
		timer.scheduleTask(new Task() {

			@Override
			public void run() {
				System.exit(0);
			}
			
		}, seconds);
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
	

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            camera.translate(-this.cameraTranslateStep * this.camera.zoom, 0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(this.cameraTranslateStep * this.camera.zoom, 0);
        if(keycode == Input.Keys.UP)
            camera.translate(0, -this.cameraTranslateStep * this.camera.zoom);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0, this.cameraTranslateStep * this.camera.zoom);
        if(keycode == Input.Keys.EQUALS)
        	camera.zoom -= this.cameraZoomStep;
        if(keycode == Input.Keys.MINUS)
        	camera.zoom += this.cameraZoomStep;
        if(keycode == Input.Keys.NUM_1) {
        	TiledMapTileLayer tileLayer = 
        			(TiledMapTileLayer) map.getLayers().get(0);
//            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
        }
        if(keycode == Input.Keys.NUM_2)
        	;
//            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
//        Vector3 position = camera.unproject(clickCoordinates);
//        sprite.setPosition(position.x, position.y);
//        return true;
    	return false;
    }

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
