package com.ktarrant.tanks.maps;

import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.ktarrant.tanks.MapViewer;

/* TODO: Can we use a label that isn't a String to be more efficient? */

public class TerrainTileSet extends TiledMapTileSet {
	private int curId_ = 0;
	private HashMap<String, Integer> labelToBaseIdMap_;
	
	public int tileWidth_ = 0;
	public int tileHeight_ = 0;
	
	public TerrainTileSet() {
		super();
		
		labelToBaseIdMap_ = new HashMap<String, Integer>();
	}
	
	/**
	 * Create an EvenTileSet from a TextureAtlas, where the name of each texture
	 * will be the key used to identify the texture later.
	 * @param atlas The atlas containing all the tiles
	 */
	public TerrainTileSet(TextureAtlas atlas) {
		this();
		
		this.addTextureAtlas(atlas);
	}
	
	public void addTextureAtlas(TextureAtlas atlas) {
		// Add each region as an EvenTile with the label as the region name.
		for (AtlasRegion region : atlas.getRegions()) {
			this.addTextureRegion(region.name, region);
		}
	}
	
	public void addTextureRegion(String name, TextureRegion region) {
		String[] nameFields = name.split("_");
		assert nameFields.length == 2;
		
		Integer baseId = labelToBaseIdMap_.get(nameFields[0]);
		if (baseId == null) {
			baseId = curId_++;
			labelToBaseIdMap_.put(nameFields[0], baseId);
		}
		int modId = Integer.valueOf(nameFields[1]);
		int tileId = (baseId << 16) | modId;
		StaticTiledMapTile tile = new StaticTiledMapTile(region);
		this.putTile(tileId, tile);
	}
}
