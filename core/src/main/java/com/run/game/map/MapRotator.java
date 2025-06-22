package com.run.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.run.game.utils.exception.UnexpectedBehaviorException;

import java.util.Arrays;
import java.util.LinkedList;

// turns the world as a circle
public class MapRotator implements Disposable {

    private LinkedList<MapContainer> tiledMaps; // circle

    private int currentIndex;

    public MapRotator() {
        currentIndex = 0;
    }

    public void addTiledMaps(MapContainer container) {
        tiledMaps.add(container);
    }

    public void rotate(byte once){
        if (tiledMaps.size() < once) throw new UnexpectedBehaviorException("Length of the map is less than the number of turns!");
        currentIndex += once;

        if (currentIndex < 0) currentIndex = tiledMaps.size() - 1; // circle is end!
        if (currentIndex >= tiledMaps.size()) currentIndex = 0; // circle is end!
    }

    public TiledMap getCurrentMap(){
        if (currentIndex >= tiledMaps.size() || currentIndex < 0) throw new UnexpectedBehaviorException("Current index went beyond the borders!");
        return tiledMaps.get(currentIndex).getMap();
    }

    public void dispose(){
        tiledMaps.forEach(MapContainer::dispose);
    }
}
