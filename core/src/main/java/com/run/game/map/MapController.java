package com.run.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

public class MapController implements Disposable {

    private final HashMap<RoomName, MapRotator> levelMaps;
    private final OrthogonalTiledMapRenderer renderer;

    private RoomName currentNameLocation = RoomName.NONE;

    public MapController(Batch batch, OrthographicCamera camera, HashMap<RoomName, MapRotator> levelMaps) {
        this.levelMaps = levelMaps;
        renderer = new OrthogonalTiledMapRenderer(null, batch);
        renderer.setView(camera);
    }

    public void render(){
        update();
        renderer.render();
    }

    public void update(){
        renderer.setMap(getMap(currentNameLocation));
    }

    private TiledMap getMap(RoomName nameLocation) {
        return levelMaps.get(nameLocation).getCurrentMap();
    }

    public MapRotator getCurrentPlace(){
        return levelMaps.get(currentNameLocation);
    }

    public RoomName getCurrentNameLocation() {
        return currentNameLocation;
    }

    public void setCurrentNameLocation(RoomName currentNameLocation) {
        this.currentNameLocation = currentNameLocation;
    }

    @Override
    public void dispose() {
        levelMaps.forEach((key, value) -> value.dispose());
    }
}
