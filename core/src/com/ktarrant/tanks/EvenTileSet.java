package com.ktarrant.tanks;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/* TODO: Can we use a label that isn't a String to be more efficient? */

public class EvenTileSet extends TiledMapTileSet {
	private int curId_ = 0;
	private HashMap<String, Integer> labelToIdMap_;
	
	public int tileWidth_ = 0;
	public int tileHeight_ = 0;
	
	public EvenTileSet() {
		super();
		
		labelToIdMap_ = new HashMap<String, Integer>();
	}
	
	/**
	 * Create an EvenTileSet from a TextureAtlas, where the name of each texture
	 * will be the key used to identify the texture later.
	 * @param atlas The atlas containing all the tiles
	 * @return A tileset using the tiles in the atlas.
	 */
	public static EvenTileSet fromTextureAtlas(TextureAtlas atlas) {
		EvenTileSet tileset = new EvenTileSet();
		
		// Add each region as an EvenTile with the label as the region name.
		for (AtlasRegion region : atlas.getRegions()) {
			tileset.addEvenTile(region.name, region);
		}
		
		return tileset;
	}
	
	/**
	 * Adds a tile to the tileset with the given label. This label can be used
	 * to retrieve the tile later using getEvenTile(label).
	 * @param label The label used for the region
	 * @param region The region to use as a tile.
	 */
	public void addEvenTile(String label, TextureRegion region) {
		// Make sure the Id number we are going to use is not
		// already taken in the tileset.
		while (this.getTile(curId_) != null) {
			curId_++;
		}
		
		// Create a tile from the region
		StaticTiledMapTile tile = new StaticTiledMapTile(region);
		tile.setId(curId_);
		
		// Add the tile with the current available ID
		this.putTile(curId_, tile);
		
		// Add an entry in the hashmap to map the label to the id
		labelToIdMap_.put(label, curId_);
		
		// Increment the current available Id
		curId_++;
		
		// Update the tile width/height
		if (tileWidth_ == 0 || tileHeight_ == 0) {
			tileWidth_ = region.getRegionWidth();
			tileHeight_ = region.getRegionHeight();
		}
	}
	
	/**
	 * Gets the tile ID associated with the given label.
	 * @param label The even tile label to search for.
	 * @return The ID for the even tile found, or null if no even tile 
	 * 		was found.
	 */
	public int getEvenTileId(String label) {
		return labelToIdMap_.get(label);
	}
}
