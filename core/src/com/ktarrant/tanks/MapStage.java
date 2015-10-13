package com.ktarrant.tanks;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ktarrant.tanks.maps.TerrainLayer;

public class MapStage extends Stage {
	public static final float DEFAULT_ZOOM = 0.6f;
	public static final float DEFAULT_CAMERA_TRANSLATE_RATE = 512.0f;
	public static final float DEFAULT_CAMERA_ZOOM_RATE = 4.0f;
	public static final Vector3 VECTOR_OUT = new Vector3(0, 0, 1);
	private final TiledMap tiledMap;
	private float cameraTranslateRate;
	private float cameraZoomRate;
	private ArrayList<TiledMapActorEventListener> evtListeners;

	public enum TiledMapActorEventType {
		TOUCHDOWN;
	}
	
	public interface TiledMapActorEventListener {
		
		public void handleActorEvent(TiledMapActorEventType eventType, TerrainLayerActor actor);
		
	}
	
	public MapStage(Viewport viewport, TiledMap tiledMap) {
		super(viewport);
		evtListeners = new ArrayList<TiledMapActorEventListener>();
		
		this.cameraTranslateRate = DEFAULT_CAMERA_TRANSLATE_RATE;
		this.cameraZoomRate = DEFAULT_CAMERA_ZOOM_RATE;
		
		this.tiledMap = tiledMap;

        for (MapLayer layer : tiledMap.getLayers()) {
        	if (layer instanceof TerrainLayer) {
	            TerrainLayer tiledLayer = (TerrainLayer)layer;
	            createTerrainLayerActorsForLayer(tiledLayer);
        	}
        }
    }
	
	public void act(float delta) {
		super.act(delta);
		handleInput(delta);
	}
	
	public void registerTiledMapActorEventListener(TiledMapActorEventListener listener) {
		evtListeners.add(listener);
	}
	
	public void unregisterTiledMapActorEventListener(TiledMapActorEventListener listener) {
		evtListeners.remove(listener);
	}
	
	private boolean handleInput(float delta) {
    	OrthographicCamera camera = (OrthographicCamera) this.getViewport().getCamera();
    	
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.translate(
            	-this.cameraTranslateRate * delta * camera.zoom, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.translate(
            	this.cameraTranslateRate * delta * camera.zoom, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.translate(
            	0, this.cameraTranslateRate * delta * camera.zoom);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.translate(
            	0, -this.cameraTranslateRate * delta * camera.zoom, 0);
        if(Gdx.input.isKeyPressed(Input.Keys.EQUALS))
        	camera.zoom -= this.cameraZoomRate * delta;
        if(Gdx.input.isKeyPressed(Input.Keys.MINUS))
        	camera.zoom += this.cameraZoomRate * delta;
//        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
//        	doAction(1);
//        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2))
//        	doAction(2);
        return true;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 stageCoords = this.screenToStageCoordinates(
				new Vector2(screenX, screenY));
		TerrainLayerActor actor = (TerrainLayerActor) this.hit(
				stageCoords.x, stageCoords.y, false);
		if (actor != null) {
			System.out.println(String.format("touchdown x=%d y=%d", 
					actor.getTileX(), actor.getTileY()));
			// Notify listeners
			for (TiledMapActorEventListener listener : evtListeners) {
				listener.handleActorEvent(TiledMapActorEventType.TOUCHDOWN, 
					actor);
			}
		} else {
			System.out.println(String.format("No actor found at x=%f y=%f",
					stageCoords.x, stageCoords.y));
		}
		return true;
	}

    private void createTerrainLayerActorsForLayer(TerrainLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
            	TerrainLayer.Cell cell = tiledLayer.getCell(x, y);
            	TerrainLayerActor actor = new TerrainLayerActor(tiledMap, tiledLayer, cell);
                actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                        tiledLayer.getTileHeight());
                
                addActor(actor);
            }
        }
    }
}
