package com.ktarrant.tanks.maps;

import java.util.Random;

public class DiamondSquareProcessor {	
	
	/** Seeds the map with randomly generated values.
	 * 	Writes values between minValue and maxValue, altering only cells whose value
	 *  lies between applyToMin and applyToMax. All bounds inclusive.
	 *  @param map The map to seed.
	 *  @param minValue The minimum value to write into the ValueMap.
	 *  @param maxValue The maximum value to write into the ValueMap.
	 *  @param applyToMin The minimum value that will be seeded.
	 *  @param applyToMax The maximum value that will be seeded. */
	public static void seedMapRandom(ValueMap<Float> map, float minValue, float maxValue, float applyToMin, float applyToMax) {
		Random rand = new Random();
		final float range = maxValue - minValue;
		for (int x = 0; x < map.width; x++) {
			for (int y = 0; y < map.height; y++) {
				final float curval = map.get(x, y);
				if (curval >= applyToMin && curval <= applyToMax) {
					map.set(x, y, rand.nextFloat() * range - minValue);
				}
			}
		}
	}
	
	/** Helper function to clamp an input value between minValue and maxValue. */
	protected static float clampValue(float value, float minValue, float maxValue) {
		return (value > maxValue) ? maxValue :
			   (value < minValue) ? minValue :
				value;
	}
	

	/** Runs the Diamond-Square algorithm on the ValueMap. This changes
	 *  a seeded ValueMap to have more natural-looking groups of values.
	 * 	Writes values between minValue and maxValue, altering only cells whose value
	 *  lies between applyToMin and applyToMax. All bounds inclusive.
	 *  @param map The ValueMap to process.
	 *  @param minValue The minimum value to write into the ValueMap.
	 *  @param maxValue The maximum value to write into the ValueMap.
	 *  @param applyToMin The minimum value that will be seeded.
	 *  @param applyToMax The maximum value that will be seeded. */
	public static void diamondSquare(ValueMap<Float> map, float minValue, float maxValue, 
			float applyToMin, float applyToMax,
			float diamondMultiplier, float squareMultiplier) {
		int div = 1;
		int len = (map.width > map.height) ? map.width : map.height;
		final float range = maxValue - minValue;
		Random rand = new Random(System.nanoTime()); 
		
		// Run the algorithm
		while (div < (len - 1)) {
			int step = (len - 1) / div;
			for (int x = 0; x < map.width; x += step) {
				for (int y = 0; y < map.height; y += step) {
					float roff1 = (rand.nextFloat() * range * 2.0f - range) * diamondMultiplier / ((float) div);
					float roff2 = (rand.nextFloat() * range * 2.0f - range) * squareMultiplier  / ((float) div);
					float roff3 = (rand.nextFloat() * range * 2.0f - range) * squareMultiplier  / ((float) div);
					float roff4 = (rand.nextFloat() * range * 2.0f - range) * squareMultiplier  / ((float) div);
					float roff5 = (rand.nextFloat() * range * 2.0f - range) * squareMultiplier  / ((float) div);

					// The diamond step: Taking a square of four points, generate
					// a random value at the square midpoint, where the two
					// diagonals meet. The midpoint value is calculated by averaging
					// the four corner values, plus a random amount. This gives you
					// diamonds when you have multiple squares arranged in a grid.
					if (x + step < map.width - 1 && y + step < map.height - 1) {
						float midValue = (map.get(x, y) + map.get(x + step, y)
								+ map.get(x, y + step) + map.get(x + step, y + step)) / 4.0f;
						map.set(x + step / 2, y + step / 2,
								clampValue(midValue + roff1, minValue, maxValue));
					}
					
					// The square step: Taking each diamond of four points, generate
					// a random value at the center of the diamond. Calculate the
					// midpoint value by averaging the corner values, plus a random
					// amount generated in the same range as used for the diamond
					// step. This gives you squares again.
					// left face diamond
					if (y + step < map.height - 1) {
						float lvalue = map.get(x, y) + map.get(x, y + step);
						int pts = 2;
						if (x > step / 2) {
							lvalue += map.get(x - step / 2, y + step / 2); pts++;
						}
						if (x < map.height - 1 - step / 2) {
							lvalue += map.get(x + step / 2, y + step / 2); pts++;
						}
						lvalue = lvalue / ((float) pts);
						map.set(x, y + step / 2,
								clampValue(lvalue + roff2, minValue, maxValue));
					}
					// top face diamond
					if (x + step < map.width - 1) {
						float tvalue = map.get(x, y) + map.get(x + step, y);
						int pts = 2;
						if (y > step / 2) {
							tvalue += map.get(x + step / 2, y - step / 2); pts++;
						}
						if (y < map.height - 1 - step / 2) {
							tvalue += map.get(x + step / 2, y + step / 2); pts++;
						}
						tvalue = tvalue / ((float) pts);
						map.set(x + step / 2, y,
								clampValue(tvalue + roff3, minValue, maxValue));
					}
				}
			}
			
			div = div * 2;
		}
	}
}
