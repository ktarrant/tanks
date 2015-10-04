package com.ktarrant.tanks.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ktarrant.tanks.maps.DiamondSquareProcessor;
import com.ktarrant.tanks.maps.ValueMap;

public class ValueMapTest extends ValueMap<Integer> {
	public static int MAX_ATTEMPTS = 1;
	public static int MAX_DIV_VALUE = 8;
	public static int MAX_MOD_VALUE = 8;
	
	public ValueMapTest() {
		super(16, 16);
	}
	
	public void printMap() {
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				System.out.print(String.valueOf(this.get(x,y)) + ", ");
			}
			System.out.println();
		}
		System.out.println();
	}

	@Test
	public void test_clean() {
		this.fillWith(0);
		for (int divValue = 1; divValue <= MAX_DIV_VALUE; divValue *= 2) {
			for (int modValue = 1; modValue <= MAX_MOD_VALUE; modValue *= 2) {
				for (int x = 0; x < this.width; x++) {
					for (int y = 0; y < this.height; y++) {
						this.set(x, y, ((x / divValue) % modValue > 0) ? 1 : 0);
					}
				}
				int attempts = 0;
				while (this.clean(0, 1)) {
					assert attempts < MAX_ATTEMPTS;
				}
				printMap();
			}
		}
	}
	
	@Test
	public void test_generate() {
		Integer[] possibleValues = new Integer[] { 0, 1 };
		
		// TODO: Increase amount of possibleValues supported
		assert possibleValues.length <= 2;
		
		// Create a ValueMap with float values to use for the DiamondSquare
		ValueMap<Float> valMap = new ValueMap<Float>(this.width, this.height);
		valMap.fillWith(0.0f);
		DiamondSquareProcessor.seedMapRandom(valMap, 0.0f, 1.0f, 0.0f, 1.0f);
		
		// Run the Diamond Square on the seeded map
		DiamondSquareProcessor.diamondSquare(valMap, 0.0f, 1.0f, 
				0.0f, 1.0f, 1.0f, 1.0f);
		
		// Convert the values in the value map to the items in the collection
		// TODO: Add configurable weights to this
		for (int i = 0; i < valMap.size; i++) {
			int elem = (int) (valMap.get(i) * (float) possibleValues.length);
			if (elem >= possibleValues.length) {
				elem = possibleValues.length - 1;
			}
			if (elem < 0) {
				elem = 0;
			}
			this.add(possibleValues[elem]);
		}
		
		// Clean the ValueMap - once clean, the ValueMap can be used for drawing
		// the terrain.
		int cleanAttempts = 0;
		while (this.clean(0, 1)) {
			assert ++cleanAttempts < 3;
		}
		printMap();
	}
}
