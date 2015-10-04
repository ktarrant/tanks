package com.ktarrant.tanks.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.assets.AssetManager;
import com.ktarrant.tanks.menu.HudScreen;

public class HudScreenTest {

	public static HudScreen createDemoHudScreen(AssetManager assetManager) {
		HudScreen hudScreen = new HudScreen(assetManager);
		
		return hudScreen;
	}

}
