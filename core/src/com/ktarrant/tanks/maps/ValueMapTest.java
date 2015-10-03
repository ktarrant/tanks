package com.ktarrant.tanks.maps;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueMapTest extends ValueMap<Integer> {
	public static int MAX_ATTEMPTS = 1;
	public static int MAX_DIV_VALUE = 8;
	public static int MAX_MOD_VALUE = 8;
	
	public ValueMapTest() {
		super(16, 16);
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
				for (int x = 0; x < this.width; x++) {
					for (int y = 0; y < this.height; y++) {
						System.out.print(String.valueOf(this.get(x,y)) + ", ");
					}
					System.out.println();
				}
				System.out.println();
			}
		}
	}
}
