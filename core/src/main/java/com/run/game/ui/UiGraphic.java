package com.run.game.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Disposable;

public class UiGraphic implements Disposable {

    private static AssetManager manager;

    public static void init(){
        manager = new AssetManager();
    }

    public static boolean loading(){
        if (manager.update()){
            manager.finishLoading();
            return true;
        }

        return false;
    }

    public static float getProgress(){
        return manager.getProgress();
    }

    public static void finishLoading(){
        manager.finishLoading();
    }

    public static void downloadTexturesForMainMenu(){
        if (manager.isLoaded("ui/startButton/idel1.png")) return;

        // startButton
        manager.load("ui/startButton/idel1.png", Texture.class);
        manager.load("ui/startButton/pressed.png", Texture.class);
    }

    public static void downloadTexturesForLevelNecrophobia(){
        if (manager.isLoaded("ui/LeftButton/Purple/idel1.png")) return;

        // LeftButton
        manager.load("ui/LeftButton/Purple/idel1.png", Texture.class);
        manager.load("ui/LeftButton/Purple/idel2.png", Texture.class);
        manager.load("ui/LeftButton/Purple/pressed.png", Texture.class);
        // RightButton
        manager.load("ui/RightButton/Purple/idel1.png", Texture.class);
        manager.load("ui/RightButton/Purple/idel2.png", Texture.class);
        manager.load("ui/RightButton/Purple/pressed.png", Texture.class);
    }

    public static Animation<Texture> getIdelAnimationStartButton(float duration){
        return new Animation<>(duration,
                manager.get("ui/startButton/idel1.png", Texture.class),
                manager.get("ui/startButton/idel1.png", Texture.class)
        );
    }

    public static Texture getPressedTextureStartButton(){
        return manager.get("ui/startButton/pressed.png", Texture.class);
    }

    public static Animation<Texture> getIdelAnimationLeftButton(float duration){
        return new Animation<>(duration,
            manager.get("ui/LeftButton/Purple/idel1.png", Texture.class),
            manager.get("ui/LeftButton/Purple/idel2.png", Texture.class)
        );
    }

    public static Texture getPressedTextureLeftButton(){
        return manager.get("ui/LeftButton/Purple/pressed.png", Texture.class);
    }

    public static Animation<Texture> getIdelAnimationRightButton(float duration){
        return new Animation<>(duration,
            manager.get("ui/RightButton/Purple/idel1.png", Texture.class),
            manager.get("ui/RightButton/Purple/idel2.png", Texture.class)
        );
    }

    public static Texture getPressedTextureRightButton(){
        return manager.get("ui/RightButton/Purple/pressed.png", Texture.class);
    }

    public static boolean isLoadTexturesForMainMenu(){
        return manager.isLoaded("ui/startButton/idel1.png");
    }

    public static boolean isLoadTexturesForLevelNecrophobia(){
        return manager.isLoaded("ui/LeftButton/Purple/idel1.png");
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
