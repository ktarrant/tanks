package com.ktarrant.tanks.maps;

import com.badlogic.gdx.utils.Array;

/** Class to maintain the underlying state of a Terrain map.
 *  @author ktarrant1 */
public class ValueMap<T> extends Array<T> {
	/** The length of each side of the map. */
	public int width;
	public int height;
	
	/** Create an instance of ValueMap. The length is 
	 *  automatically adjusted to be able to contain the 
	 *  given width, height.
	 *  @param width Minimum width of map.
	 *  @param height Minimum height of map. */
	public ValueMap(int width, int height) {
		super(width * height);

		this.width = width;
		this.height = height;
	}
	
	/** Fills the value map with a value. Useful for initializing the
	 *  map for subsequent set calls.
	 *  @param value Value to fill the map with.
	 */
	public void fillWith(T value) {
		for (int i = 0; i < this.size; i++) {
			this.set(i, value);
		}
		for (int i = this.size; i < width * height; i++) {
			this.add(value);
		}
	}
	
	/** Cleans the map. For each cell in the map, the value of the cell will
	 *  be compared the value of its neighbors, and if the current value is
	 *  invalid a valid value will be put in its place.
	 *  @param topValue The "dominant" value of the map. If an invalid value is
	 *  	detected, it will be replaced with a topValue.
	 *  @param bottomValue The "passive" value of the map. If a bottomValue is
	 *  	in a cell and the cell is invalid, it will be replaced by a topValue
	 *  @return true if the map cleaning had any change, false if the map
	 *  	cleaning had no effect. If the result is true, then it would be wise
	 *  	to call clean again until the region can not be cleaned any further.
	 */
	public boolean clean(T topValue, T bottomValue) {
		return this.clean(topValue, bottomValue, 0, 0, this.width, this.height);
	}
	
	/** Cleans the map. For each cell in the specified region, the value of the
	 *  cell will be compared the value of its neighbors, and if the current
	 *  value is invalid a valid value will be put in its place.
	 *  @param topValue The "dominant" value of the map. If an invalid value is
	 *  	detected, it will be replaced with a topValue.
	 *  @param bottomValue The "passive" value of the map. If a bottomValue is
	 *  	in a cell and the cell is invalid, it will be replaced by a topValue
	 *  @param minX The x-value of corner of the region to be cleaned
	 *  @param minY The y-value of corner of the region to be cleaned
	 *  @param width The width of the region to be cleaned
	 *  @param height The height of the region to be cleaned
	 *  @return true if the map cleaning had any change, false if the map
	 *  	cleaning had no effect. If the result is true, then it would be wise
	 *  	to call clean again until the region can not be cleaned any further.
	 */
	public boolean clean(T topValue, T bottomValue, int minX, int minY, 
			int width, int height) {
		boolean didChange = false;
		for (int x = minX; x < minX + width; x++) {
			for (int y = minY; y < minY + height; y++) {
				if ((this.get(x, y) == topValue) ||
					(this.get(x, y) != bottomValue)) {
					// A topValue will never need to be replaced. If it's not a
					// topValue and it's not a bottomValue, then it's not
					// something we want to clean right now so we skip this cell
					continue;
				}
				if ((x > 0) && (x < this.width - 1)) {
					
					if ((this.get(x - 1, y) == topValue) && 
						(this.get(x + 1, y) == topValue)) {
						
						this.set(x, y, topValue);
						didChange = true;
						continue;
					}
					
					if ((y > 0) && (y < this.height - 1))  {
						
						if ((this.get(x - 1, y - 1) == topValue) && 
							(this.get(x + 1, y + 1) == topValue)) {
							this.set(x, y, topValue);
							didChange = true;
							continue;
						}
						
						if ((this.get(x + 1, y - 1) == topValue) && 
							(this.get(x - 1, y + 1) == topValue)) {
							this.set(x, y, topValue);
							didChange = true;
							continue;
						}
					}

				}
				if ((y > 0) && (y < this.height - 1)) {
					
					if ((this.get(x, y - 1) == topValue) && 
						(this.get(x, y + 1) == topValue)) {
						
						this.set(x, y, topValue);
						didChange = true;
						continue;
					}
				}
			}
		}
		return didChange;
	}
	
	/** Sets a value in the map to a value.
	 *  If (x,y) outside of map is set, nothing
	 *  happens.
	 *  @param x The x-coordinate.
	 *  @param y The y-coordinate.
	 *  @param value The value to set. */
	public void set(int x, int y, T value) {
		super.set(y * width + x, value);
	}
	
	/** Gets a value in the map.
	 *  @param x The x-coordinate.
	 *  @param y The y-coordinate.
	 *  @return The value at (x,y). */
	public T get(int x, int y) {
		return super.get(y * width + x);
	}
	
	/** Gets a value in the map.
	 *  If (x,y) is outside of the map, defaultValue is returned.
	 *  @param x The x-coordinate.
	 *  @param y The y-coordinate.
	 *  @param defaultValue The value returned if (x,y) is outside the map.
	 *  @return The value at (x,y). */
	public T get(int x, int y, T defaultValue) {
		if ((x < 0) || (x >= this.width) ||
			(y < 0) || (y >= this.height)) {
			return defaultValue;
		}
		return super.get(y * width + x);
	}
}
