package com.run.game.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Map;

public class WorldCreator {

    private static HashMap<MapName, HashMap<RoomName, String[]>> PATHS_TO_ASSETS;
    private static AssetManager manager;

    public void init(){
        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        PATHS_TO_ASSETS = new HashMap<>();

        initPathsToAssetNecrophobia();
    }

    private static void initPathsToAssetNecrophobia(){
        HashMap<RoomName, String[]> basement = new HashMap<>();
        basement.put(
            RoomName.BASEMENT,
            new String[] {
                "tiledmaps/necrophobia/basementleft.tmx",
                "tiledmaps/necrophobia/basementright.tmx",
                "tiledmaps/necrophobia/basementfront.tmx"
            }
        );

        PATHS_TO_ASSETS.put(MapName.NECROPHOBIA, basement);
    }

    public static HashMap<RoomName, MapRotator> createWorld(MapName name){
        if (!manager.isLoaded(PATHS_TO_ASSETS.get(name).get(RoomName.BASEMENT)[0])){
            loadTextureWorld(name); // FIXME: 22.06.2025 это нужно сделать в экране загрузки, так как иначе мы - никак не добьемся асинхронной загрузки
        }

        HashMap<RoomName, MapRotator> world = new HashMap<>();
        HashMap<RoomName, String[]> rooms = PATHS_TO_ASSETS.get(name);

        if (rooms == null) throw new IllegalArgumentException("World on name " + name + " - not exist");

        for (Map.Entry<RoomName, String[]> paths : rooms.entrySet()) {
            MapRotator room = new MapRotator();

            for (String path : paths.getValue()) {
                room.addTiledMaps(new MapContainer(manager.get(path, TiledMap.class)));
            }

            world.put(paths.getKey(), room);
        }

        return world;
    }

    public static void loadTextureWorld(MapName name){
        HashMap<RoomName, String[]> rooms = PATHS_TO_ASSETS.get(name);

        for (Map.Entry<RoomName, String[]> paths : rooms.entrySet()) {
            for (String path : paths.getValue()) {
                manager.load(path, TiledMap.class);
            }
        }

    }
}
