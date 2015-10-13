package com.ktarrant.tanks.menu;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.ktarrant.tanks.TerrainLayerActor;
import com.ktarrant.tanks.maps.TerrainTileSet;

public class HudStage extends Stage {
	public static final int BUTTON_WIDTH = 32;
	public static final int PREVIEW_WIDTH = 64;
	
	//Skin for using in menu textures
	private Skin skin;
	//The root table of the Stage
	private Table root;
	//The table for the HUD
	private Table hudTable;
	//The table for the params
	private Table paramTable;
	//The group for the collection of clickable tiles
	private HorizontalGroup buttonGroup;
	private HashMap<String, Label> paramActors;
	private Image selectedTile;
	private HashMap<Integer, Button> availableTiles;
	private TerrainLayerActor tileActor;
	
	public HudStage(Skin skin) {
		this.skin = skin;
		
		this.paramActors = new HashMap<String, Label>();
		this.availableTiles = new HashMap<Integer, Button>();
		
		//Create the root table
		root = new Table();
	    root.setFillParent(true);
	    root.left().top();
	    this.addActor(root);
	    root.setDebug(true); // This is optional, but enables debug lines for tables.
	    
	    //Create the hudTable
	    hudTable = new Table();
	    hudTable.left().top();
	    hudTable.setTouchable(Touchable.enabled);
	    hudTable.setBackground(skin.get("default-pane", Drawable.class));
	    root.add(hudTable).left().expandX();
	    
	    //Create selection image
	    selectedTile = new Image();
	    hudTable.add(selectedTile)
	    	.width(PREVIEW_WIDTH)
	    	.height(PREVIEW_WIDTH)
	    	.pad(8.0f);
	    
	    //Create button menu
	    buttonGroup = new HorizontalGroup();
	    hudTable.add(buttonGroup);
	    
	    //Create params group
	    paramTable = new Table();
	    hudTable.add(paramTable).left().top().expandX();
	   
	    //Initialize selection logic
	    updateSelection(null);
	}
	
	public void addTileButton(TerrainTileSet tileset, int tileId) {
		Button button = new Button(this.skin);
		Image image = new Image(tileset.getTileFromSkin(tileId, Drawable.class));
		button.add(image).width(BUTTON_WIDTH).height(BUTTON_WIDTH);
		availableTiles.put(tileId, button);
		buttonGroup.addActor(button);
		buttonGroup.layout();
	}
	
	public void updateSelection(Actor actor) {
		if (actor == null) {
			this.updateParam("Selection", "None");
		} else if (actor instanceof TerrainLayerActor) {
			this.tileActor = (TerrainLayerActor) actor;
			this.updateParam("Selection",
				String.format("TiledMapTile: (%d, %d)", 
						tileActor.getTileX(), tileActor.getTileY()));
			TerrainTileSet tileset = (TerrainTileSet) tileActor.tiledLayer.getTileset();
			Drawable drawable = tileset.getTileFromSkin(
					this.tileActor.cell.getTile().getId(), 
					Drawable.class);
			this.selectedTile.setDrawable(drawable);
		}
	}
	
	public void updateParam(String param, String value) {
		String msg = param + ": " + value;
		Label actor = paramActors.get(param);
		if (actor == null) {
			actor = new Label(msg, this.skin);
			paramTable.add(actor).expandX();
			paramTable.row();
			paramActors.put(param, actor);
		} else {
			((Label) actor).setText(msg);
		}
	}
}
