package com.ktarrant.tanks.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class TestLauncher {
	// Likely want 1f/60 for 60 fps
	public static float DEFAULT_RENDER_INTERVAL = 1.0f / 60.0f;
	public static float DEFAULT_RUN_TIME		= 1.0f;
	
	private float runTime;
	private HeadlessApplicationConfiguration config;
	private HeadlessApplication currentApp;
	
	public TestLauncher(HeadlessApplicationConfiguration config, float runTime) {
		this.config = config;
		this.currentApp = null;
		this.runTime = runTime;
	}
	public TestLauncher() {
		this(new HeadlessApplicationConfiguration(), DEFAULT_RUN_TIME);
		this.config.renderInterval = DEFAULT_RENDER_INTERVAL;
	}
	
	public void start(ApplicationListener app) {
		this.currentApp = new HeadlessApplication(app, config);
	}
	
	/**
	 * @return the config
	 */
	public HeadlessApplicationConfiguration getConfig() {
		return config;
	}
	/**
	 * @param config the config to set
	 */
	public void setConfig(HeadlessApplicationConfiguration config) {
		this.config = config;
	}
	/**
	 * @return the currentApp
	 */
	public HeadlessApplication getCurrentApp() {
		return currentApp;
	}
	/**
	 * @param currentApp the currentApp to set
	 */
	public void setCurrentApp(HeadlessApplication currentApp) {
		this.currentApp = currentApp;
	}
}
