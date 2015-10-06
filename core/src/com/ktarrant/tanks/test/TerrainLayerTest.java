package com.ktarrant.tanks.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.ktarrant.tanks.MapViewer;
import com.ktarrant.tanks.maps.DiamondSquareProcessor;
import com.ktarrant.tanks.maps.TerrainLayer;
import com.ktarrant.tanks.maps.TerrainTileSet;
import com.ktarrant.tanks.maps.ValueMap;

public class TerrainLayerTest {

	public static MapViewer createDemoMapViewer(AssetManager assetManager) {
		// Create a MapViewer application
		MapViewer mapView = new MapViewer(assetManager) {
			

			
		};
		
		return mapView;
	}
}
