package com.ktarrant.tanks.desktop;

import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.ktarrant.tanks.TanksGame;
import com.ktarrant.tanks.maps.DiamondSquareProcessor;
import com.ktarrant.tanks.maps.TerrainTileSet;
import com.ktarrant.tanks.maps.TerrainLayer;
import com.ktarrant.tanks.maps.ValueMap;
import com.ktarrant.tanks.test.MapViewer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new TanksGame(), config);
	}
}
