package com.ktarrant.tanks.menu;

import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

public class HudGroup extends HorizontalGroup {
	private Actor selectedActor;
	private HashMap<String, Actor> paramActors;

	public void setSelection(Actor selectedActor) {
		this.selectedActor = selectedActor;
		this.paramActors = new HashMap<String, Actor>();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
}
