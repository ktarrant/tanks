package com.ktarrant.tanks.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.ktarrant.tanks.EvenTileSet;

public class DemoMaps {
	
	public static TiledMap makeDemoMap(EvenTileSet tileset, int width, int height) {
		TiledMap map = new TiledMap();
		
		MapLayers layers = map.getLayers();
		TiledMapTileLayer layer = new TiledMapTileLayer(width, height, 
				tileset.tileWidth_, tileset.tileHeight_);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
				String label = null;
				if ((x < width / 3) || (x > 2 * width / 3)) {
					label = "A";
				} else if ((x >= (width / 3)) && (x <= 2 * width / 3)) {
					label = "B";
				}
				cell.setTile(tileset.getTile(tileset.getEvenTileId(label)));
				layer.setCell(x, y, cell);
			}
		}
		layers.add(layer);
		return map;
	}
}
