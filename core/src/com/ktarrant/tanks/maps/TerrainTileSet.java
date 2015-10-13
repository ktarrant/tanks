package com.ktarrant.tanks.maps;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Set;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.ktarrant.tanks.MapViewer;

/* TODO: Can we use a label that isn't a String to be more efficient? */

public class TerrainTileSet extends TiledMapTileSet {

	public final HashMap<String, Integer> labelToBaseIdMap;
	public final HashMap<Integer, String> tileIdToLabelMap;
	public final Skin skin;
	private int curId_ = 0;
	
	/**
	 * Create an EvenTileSet from a TextureAtlas, where the name of each texture
	 * will be the key used to identify the texture later.
	 * @param atlas The atlas containing all the tiles
	 */
	public TerrainTileSet(Skin skin) {
		super();
		
		labelToBaseIdMap = new HashMap<String, Integer>();
		tileIdToLabelMap = new HashMap<Integer, String>();
		this.skin = skin;
		
		// Add each region as an TerrainTile with the label as the region name.
		for (AtlasRegion region : skin.getAtlas().getRegions()) {
			//Split by underscore:
			// First element is the label for the base ID.
			// Second element is the modifier ID for the tile
			String[] nameFields = region.name.split("_");
			assert nameFields.length == 2;
			
			//Check if the label already has a baseId
			Integer baseId = labelToBaseIdMap.get(nameFields[0]);
			if (baseId == null) {
				//This is a new label, give it a fresh baseId
				baseId = curId_++;
				labelToBaseIdMap.put(nameFields[0], baseId);
			}
			//Create a tileId for the new tile using the baseId and modId
			int modId = Integer.valueOf(nameFields[1]);
			int tileId = (baseId << 16) | modId;
			tileIdToLabelMap.put(tileId, region.name);
			
			//Create a static tile for the region
			StaticTiledMapTile tile = new StaticTiledMapTile(region);
			tile.setId(tileId);
			this.putTile(tileId, tile);
		}
	}
	
	public <T> T getTileFromSkin(int tileId, Class<T> clazz) {
		String label = tileIdToLabelMap.get(tileId);
		if (label == null) {
			return null;
		} else {
			return skin.get(label, clazz);
		}
	}
}
