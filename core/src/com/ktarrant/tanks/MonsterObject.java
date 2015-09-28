package com.ktarrant.tanks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

public class MonsterObject extends MapObject {
	private TextureRegion region;
	private int x;
	private int y;
	private float tileWidth;
	private float tileHeight;
	private float width;
	private float height;
	
	public MonsterObject(float tileWidth, float tileHeight, float width, float height) {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.width = width;
		this.height = height;
	}
	
	public MonsterObject(float tileWidth, float tileHeight) {
		this(tileWidth, tileHeight, tileWidth, tileHeight);
	}
	
	public float getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(float tileWidth) {
		this.tileWidth = tileWidth;
	}

	public float getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(float tileHeight) {
		this.tileHeight = tileHeight;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public TextureRegion getTextureRegion() {
		return region;
	}

	public void setTextureRegion(TextureRegion region) {
		this.region = region;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
