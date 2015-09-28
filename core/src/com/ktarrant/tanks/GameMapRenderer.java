package com.ktarrant.tanks;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class GameMapRenderer extends OrthogonalTiledMapRenderer {
	
   public GameMapRenderer(TiledMap map) {
        super(map);
    }

    public GameMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public GameMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public GameMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    @Override
    public void renderObject(MapObject object) {
        if (object instanceof MonsterObject) {
            MonsterObject monsterObject = (MonsterObject) object;
            batch.draw(
                    monsterObject.getTextureRegion(),
                    monsterObject.getX() * monsterObject.getTileWidth(),
                    monsterObject.getY() * monsterObject.getTileHeight(),
                    monsterObject.getWidth(),
                    monsterObject.getHeight()
            );
        }
    }
}
