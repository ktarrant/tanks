package com.ktarrant.tanks.maps;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TerrainLayer extends TiledMapTileLayer {
	private ValueMap<Integer> valueMap;
	private TiledMapTileSet tileset;
	
	private static int[][] MOD_TABLE = new int[][] {
		//		    0,    1,    2,    3,    4,    5,    6,    7,    8,    9,   10,   11,   12,   13
		new int[] {-1,   -1,   -1,   -1,    1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,    0,   -1},	// 0
		new int[] { 0,    0,    0,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,   -1},	// 1
		new int[] {-1,   -1,   -1,   -1,    1,   -1,   -1,   -1,   -1,   -1,   -1,    0,   -1,   -1},	// 2
		new int[] { 0,    1,    1,    0,    1,    1,    0,    1,    1,    1,    1,    1,    1,   -1},	// 3
		new int[] { 1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,    0},	// 4
		new int[] { 1,    1,    0,    1,    1,    0,    1,    1,    0,    1,    1,    1,    1,   -1},	// 5
		new int[] {-1,   -1,   -1,   -1,    1,   -1,   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1},	// 6
		new int[] { 1,    1,    1,    1,    1,    1,    0,    0,    0,    1,    1,    1,    1,   -1},	// 7
		new int[] {-1,   -1,   -1,   -1,    1,   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1},	// 8
	};

	public TerrainLayer(int width, int height, int tileWidth, int tileHeight, 
			TiledMapTileSet tileset) {
		super(width, height, tileWidth, tileHeight);
		
		this.valueMap = new ValueMap<Integer>(width, height);
		this.valueMap.fillWith(0);
		this.tileset = tileset;
	}
	
	public void update(int minX, int minY, int width, int height) {
		// TODO: Clean up this function. It has asserts, bit masks, it's gross.
		// TODO: How to tie in information about ID's and number of terrain
		// types between tile set and tile layer?
		
		// Try to clean the ValueMap
		int cleanAttemptsRemaining = 3;
		while (valueMap.clean(0, 1, minX, minY, width, height)) {
			--cleanAttemptsRemaining;
			assert cleanAttemptsRemaining != 0;
		}
		
		int[] neighbors = new int[MOD_TABLE.length];
		for (int x = minX; x < minX + width; x++) {
			for (int y = minY; y < minY + height; y++) {
				// Read the tileId of the tile we are updating
				int baseId = valueMap.get(x, y);
				// The baseId must be 16-bits because it half of the tileId is
				// reserved for the modId
				assert (baseId & 0xFFFF0000) == 0;
				
				for (ThirteenPatchNeighbor neighbor : ThirteenPatchNeighbor.values()) {
					// Get the baseId of each neighbor. We can use this to
					// determine what our modifier will be.
					neighbors[neighbor.index] = valueMap.get(
							x + neighbor.xOffset, y + neighbor.yOffset,
							baseId);
				}
				
				// Get the modId from the neighbor baseId's
				int modId = getModifier(neighbors);
				assert (modId & 0xFFFF0000) == 0;
				
				// Build the tileId from the baseId and modId
				// TODO: Make the # of bits for baseId and modId a class
				// constant. It would be more efficient to give modId only 4
				// bits since we know there are < 16 values.
				int tileId = (baseId << 16) | modId;
				
				// Retrieve the tile from the tileset using the tileId and make
				// sure that it is a valid tile.
				TiledMapTile tile = tileset.getTile(tileId);
				assert tile != null;
				
				// Write the tile into the layer
				TiledMapTileLayer.Cell cell = this.getCell(x, y);
				if (cell == null) {
					cell = new TiledMapTileLayer.Cell();
				}
				cell.setTile(tile);
				this.setCell(x, y, cell);
			}
		}
	}
	
	public void update() {
		this.update(0, 0, this.getWidth(), this.getHeight());
	}
	
	public enum ThirteenPatchNeighbor {

		 TOP_LEFT			( 0, -1, -1),
		 TOP_MIDDLE			( 1,  0, -1),
		 TOP_RIGHT			( 2,  1, -1),
		 MIDDLE_LEFT		( 3, -1,  0),
		 MIDDLE_MIDDLE		( 4,  0,  0),
		 MIDDLE_RIGHT		( 5,  1,  0),
		 BOTTOM_LEFT		( 6, -1,  1),
		 BOTTOM_MIDDLE		( 7,  0,  1),
		 BOTTOM_RIGHT		( 8,  1,  1);

		 public final int index;
		 public final int xOffset;
		 public final int yOffset;

		 private ThirteenPatchNeighbor(int index, int xOffset, int yOffset) {
		 	this.index = index;
		 	this.xOffset = xOffset;
		 	this.yOffset = yOffset;
		 }
		 
		 
	 }
	
	/**
	 * @return the valueMap
	 */
	public ValueMap<Integer> getValueMap() {
		return valueMap;
	}

	/**
	 * @param valueMap the valueMap to set
	 */
	public void setValueMap(ValueMap<Integer> valueMap) {
		this.valueMap = valueMap;
	}

	/**
	 * @return the tileset
	 */
	public TiledMapTileSet getTileset() {
		return tileset;
	}

	/**
	 * @param tileset the tileset to set
	 */
	public void setTileset(TiledMapTileSet tileset) {
		this.tileset = tileset;
	}
	
	private int getModifier(int[] neighbors) {
		// matchMask contains a mask of all the match bits we can set
		int matchMask = (1 << MOD_TABLE[0].length) - 1;
		// matchFlags contains a bit for each modifier that is still a possible
		// match for the current neighbor configuration
		int matchFlags = matchMask;
		// For each neighbor of the current tile...
		for (int neighborIndex = 0;
				neighborIndex < neighbors.length;
				neighborIndex++) {
			// We eliminate any possible modifiers that aren't compatible
			// with this neighbor
			for (int matchIndex = 0; 
					matchIndex < MOD_TABLE[neighborIndex].length; 
					matchIndex++) {
				// The modTableValue tells us what this tileModifier expects
				// to have at the given neighbor position.
				int modTableValue = MOD_TABLE[neighborIndex][matchIndex];
				if (!(modTableValue == neighbors[neighborIndex]) &&
					!(modTableValue == -1)) {
					// Mask out this modifier - it is not compatible.
					matchFlags &= ~(1 << matchIndex);
				}
			}
			
			if (matchFlags == 0) {
				// If there are no matchFlags set, that means this neighbor
				// configuration is not compatible with any of our modifiers.
				// This is not a good place to be.
				return -1;
			}
		}
		
		// There could be multiple modifiers that are compatible - choose the
		// one with the smallest index.
		for (int matchIndex = 0; 
				matchIndex < MOD_TABLE[0].length; 
				matchIndex++) {
			if ((matchFlags & (1 << matchIndex)) != 0) {
				return matchIndex;
			}
		}
		
		// We should never get here, but if we do then we definitely didn't
		// succeed in matching a modifier.
		return -1;
	}
}
