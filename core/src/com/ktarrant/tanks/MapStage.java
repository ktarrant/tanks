package com.ktarrant.tanks;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MapStage extends Stage {
	public static final float DEFAULT_ZOOM = 0.6f;
	public static final float DEFAULT_CAMERA_TRANSLATE_STEP = 32.0f;
	public static final float DEFAULT_CAMERA_ZOOM_STEP = 1.0f;
	private float cameraTranslateStep;
	private float cameraZoomStep;
	
	public MapStage(Viewport viewport) {
		super(viewport);
		
		this.cameraTranslateStep = DEFAULT_CAMERA_TRANSLATE_STEP;
		this.cameraZoomStep = DEFAULT_CAMERA_ZOOM_STEP;
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
