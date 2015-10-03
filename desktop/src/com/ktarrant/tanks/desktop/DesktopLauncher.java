package com.ktarrant.tanks.desktop;

import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.ktarrant.tanks.EvenTileSet;
import com.ktarrant.tanks.TanksGame;
import com.ktarrant.tanks.maps.DiamondSquareProcessor;
import com.ktarrant.tanks.maps.TerrainLayer;
import com.ktarrant.tanks.maps.ValueMap;
import com.ktarrant.tanks.test.MapViewer;

public class DesktopLauncher {
	public static void main (String[] arg) {
//		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new TanksGame(), config);
		
		test_evenTileSet();
	}
	
	public static void test_evenTileSet() {
		// Create a MapViewer application
		MapViewer mapView = new MapViewer() {
			
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

			@Override
			public void load() {
				// Load an EvenTileSet from a TextureAtlas
				assetManager.load("grass.pack", TextureAtlas.class);
				assetManager.finishLoading();
				
				
				
				// Build a TerrainLayer from the randomly generated ValueMap
				// and an EvenTileSet
				TextureAtlas mapAtlas = assetManager.get("grass.pack", 
						TextureAtlas.class);
				EvenTileSet tileset = new EvenTileSet(mapAtlas);
				TerrainLayer layer = new TerrainLayer(
						32, 32, 128, 128, tileset);
				layer.setValueMap(generateRandomMap());
				layer.update();
				
				// Create a tiled map and add the layer to it
				this.map = new TiledMap();
				this.map.getLayers().add(layer);
			}

			@Override
			public void doAction(int actionId) {
				if (actionId == 1) {
					TerrainLayer layer = (TerrainLayer) this.map.getLayers().get(0);
					layer.setValueMap(generateRandomMap());
					layer.update();
				}
			}
			
		};
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(mapView, config);
	}
}
