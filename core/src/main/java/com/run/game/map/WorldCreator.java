package com.run.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Map;

public class WorldCreator{

    private static HashMap<WorldName, HashMap<RoomName, String[]>> PATHS_TO_ASSETS;
    private static AssetManager manager;

    public static void init(){
        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        PATHS_TO_ASSETS = new HashMap<>();

        initPathsToAssetNecrophobia();
    }

    public static boolean isDone(){
        if (manager.update()){
            manager.finishLoading();
            return true;
        }

        return false;
    }

    public static float getProgress(){
        return manager.getProgress();
    }

    private static void initPathsToAssetNecrophobia(){ // FIXME: 24.06.2025 перенеси пути в json и из файла и бери эти пути
        HashMap<RoomName, String[]> basement = new HashMap<>();
        basement.put(
            RoomName.BASEMENT,
            new String[] {
                "tiledmaps/necrophobia/basementleft.tmx",
                "tiledmaps/necrophobia/basementright.tmx",
                "tiledmaps/necrophobia/basementfront.tmx"
            }
        );

        PATHS_TO_ASSETS.put(WorldName.NECROPHOBIA, basement);
    }

    public static HashMap<RoomName, MapRotator> createWorld(WorldName name){
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

    public static boolean isLoadTextureWorld(WorldName worldName, RoomName roomName){
        return manager.isLoaded(PATHS_TO_ASSETS.get(worldName).get(roomName)[0]);
    }

    public static boolean isLoadTextureNecrophobiaWorld(){
        return isLoadTextureWorld(WorldName.NECROPHOBIA, RoomName.BASEMENT);
    }

    private static boolean checkingTexturesLoading(WorldName name){
        switch (name){
            case NECROPHOBIA:
                return isLoadTextureNecrophobiaWorld(); // if true - success, Textures been loaded
        }

        return false; // fail - Textures was not loading
    }

    public static void loadTextureWorld(WorldName name){
        if (checkingTexturesLoading(name)) return;

        HashMap<RoomName, String[]> rooms = PATHS_TO_ASSETS.get(name);

        for (Map.Entry<RoomName, String[]> paths : rooms.entrySet()) {
            for (String path : paths.getValue()) {
                manager.load(path, TiledMap.class);
            }
        }

    }

    public static void dispose(){
        manager.dispose();
    }
}
