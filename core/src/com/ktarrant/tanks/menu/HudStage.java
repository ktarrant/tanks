package com.ktarrant.tanks.menu;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class HudStage extends Stage {
	private Skin skin;
	private WidgetGroup group;
	private VerticalGroup mainVertGroup;
	private VerticalGroup hudWindow;
	private HashMap<String, Actor> paramActors;
	
	public HudStage(Skin skin) {
		this.skin = skin;
		this.paramActors = new HashMap<String, Actor>();
		group = new WidgetGroup();
	    group.setFillParent(true);
	    this.addActor(group);

	    group.setDebug(true); // This is optional, but enables debug lines for tables.

	    // Add widgets to the table here.
	    hudWindow = new VerticalGroup();
	    hudWindow.setColor(1, 1, 1, 1);
	    mainVertGroup = new VerticalGroup();
	    mainVertGroup.setFillParent(true);
	    mainVertGroup.addActor(hudWindow);
	    group.addActor(mainVertGroup);
	    
	    this.updateParam("Selection", "None");
	}
	
	public void updateParam(String param, String value) {
		String msg = param + ": " + value;
		Actor actor = paramActors.get(param);
		if (actor == null) {
			actor = new Label(msg, this.skin);
			hudWindow.addActor(actor);
			paramActors.put(param, actor);
		} else {
			((Label) actor).setText(msg);
		}
	}
}
