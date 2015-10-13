package com.ktarrant.tanks;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ktarrant.tanks.maps.TerrainLayer;
import com.ktarrant.tanks.maps.TerrainTileSet;

public class TerrainLayerActor extends Actor {
    public final TiledMap tiledMap;

    public final TerrainLayer tiledLayer;

	public final TerrainLayer.Cell cell;

	public TerrainLayerActor(TiledMap tiledMap, TerrainLayer tiledLayer, TerrainLayer.Cell cell) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
    }
	
	public int getTileX() {
		return (int) Math.floor(this.getX() / this.tiledLayer.getTileWidth());
	}
	
	public int getTileY() {
		return (int) Math.floor(this.getY() / this.tiledLayer.getTileHeight());
	}
}
