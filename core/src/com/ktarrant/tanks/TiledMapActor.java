package com.ktarrant.tanks;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TiledMapActor extends Actor {
    public TiledMap tiledMap;

    public TiledMapTileLayer tiledLayer;

	public TiledMapTileLayer.Cell cell;

	public TiledMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell) {
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
