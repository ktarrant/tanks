package com.ktarrant.tanks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ktarrant.tanks.maps.TerrainLayer;

public class MapStage extends Stage {
	public static final float DEFAULT_ZOOM = 0.6f;
	public static final float DEFAULT_CAMERA_TRANSLATE_STEP = 32.0f;
	public static final float DEFAULT_CAMERA_ZOOM_STEP = 1.0f;
	private float cameraTranslateStep;
	private float cameraZoomStep;
	private TiledMap tiledMap;
	
	public class TiledMapClickListener extends ClickListener {

	    private TiledMapActor actor;

	    public TiledMapClickListener(TiledMapActor actor) {
	        this.actor = actor;
	    }

	    @Override
	    public void clicked(InputEvent event, float x, float y) {
	        System.out.println(String.format("%d,%d has been clicked.",
	        		actor.getTileX(), actor.getTileY()));
	    }
	}
	
	public MapStage(Viewport viewport, TiledMap tiledMap) {
		super(viewport);
		
		this.cameraTranslateStep = DEFAULT_CAMERA_TRANSLATE_STEP;
		this.cameraZoomStep = DEFAULT_CAMERA_ZOOM_STEP;
		
		this.tiledMap = tiledMap;

        for (MapLayer layer : tiledMap.getLayers()) {
        	if (layer instanceof TerrainLayer) {
	            TerrainLayer tiledLayer = (TerrainLayer)layer;
	            createActorsForLayer(tiledLayer);
        	}
        }
    }

    private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
            	TerrainLayer.Cell cell = new TerrainLayer.Cell();
            	TiledMapActor actor = new TiledMapActor(tiledMap, tiledLayer, cell);
                actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                        tiledLayer.getTileHeight());
                
                addActor(actor);
                EventListener eventListener = new TiledMapClickListener(actor);
                actor.addListener(eventListener);
            }
        }
    }
	
    @Override
    public boolean keyUp(int keycode) {
    	OrthographicCamera camera = (OrthographicCamera) this.getViewport().getCamera();
    	
        if(keycode == Input.Keys.LEFT)
            camera.translate(-this.cameraTranslateStep * camera.zoom, 0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(cameraTranslateStep * camera.zoom, 0);
        if(keycode == Input.Keys.UP)
            camera.translate(0, cameraTranslateStep * camera.zoom);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0, -cameraTranslateStep * camera.zoom);
        if(keycode == Input.Keys.EQUALS)
        	camera.zoom -= this.cameraZoomStep;
        if(keycode == Input.Keys.MINUS)
        	camera.zoom += this.cameraZoomStep;
//        if(keycode == Input.Keys.NUM_1)
//        	doAction(1);
//        if(keycode == Input.Keys.NUM_2)
//        	doAction(2);
        
        return false;
    }
}
