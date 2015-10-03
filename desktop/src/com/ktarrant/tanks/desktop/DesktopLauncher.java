package com.ktarrant.tanks.desktop;

import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.ktarrant.tanks.EvenTileSet;
import com.ktarrant.tanks.TanksGame;
import com.ktarrant.tanks.test.DemoMaps;
import com.ktarrant.tanks.test.MapViewer;

public class DesktopLauncher {
	public static void main (String[] arg) {
//		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new TanksGame(), config);
		
		test_evenTileSet();
	}
	
	public static void test_make13Patch() {
		
	}
	
	public static void test_evenTileSet() {
		// Create a MapViewer application
		MapViewer mapView = new MapViewer() {

			@Override
			public void load() {
				// Load an EvenTileSet from a TextureAtlas
				assetManager.load("grass.pack", TextureAtlas.class);
				assetManager.finishLoading();
				TextureAtlas mapAtlas = assetManager.get("grass.pack", 
						TextureAtlas.class);
				EvenTileSet tileset = new EvenTileSet(mapAtlas);
				mapAtlas = assetManager.get("grass.pack", TextureAtlas.class);
				// Create a DemoMap to use the tileset
				map = DemoMaps.makeDemoMap(tileset, 32, 32);
			}
			
		};
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(mapView, config);
	}
}
