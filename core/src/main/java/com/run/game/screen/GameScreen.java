package com.run.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.map.MapController;
import com.run.game.map.WorldName;
import com.run.game.map.RoomName;
import com.run.game.map.WorldCreator;
import com.run.game.ui.UiController;
import com.run.game.ui.UiFactory;
import com.run.game.ui.UiGraphic;

public class GameScreen implements Screen {

    private final Main main;
    private final SpriteBatch batch;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;

    private final FitViewport gameViewport;
    private final ScreenViewport uiViewport;

    private MapController mapController;
    private UiController gameMenu;

    public GameScreen(Main main, SpriteBatch batch, OrthographicCamera gameCamera, OrthographicCamera uiCamera, FitViewport gameViewport, ScreenViewport uiViewport) {
        this.main = main;
        this.batch = batch;
        this.gameCamera = gameCamera;
        this.uiCamera = uiCamera;
        this.gameViewport = gameViewport;
        this.uiViewport = uiViewport;
    }

    @Override
    public void show() {
        if (gameMenu == null) {
            if (!UiGraphic.isLoadTexturesForLevelNecrophobia() || !WorldCreator.isLoadTextureWorld(WorldName.NECROPHOBIA, RoomName.BASEMENT)) {  // FIXME: 23.06.2025 ХАРДКОД
                UiGraphic.downloadTexturesForLevelNecrophobia();
                WorldCreator.loadTextureWorld(WorldName.NECROPHOBIA);  // FIXME: 23.06.2025 ХАРДКОД
                main.setScreen(new LoadingScreen(main, this, batch, uiCamera, uiViewport));
                return;
            }

            mapController = new MapController(batch, gameCamera, WorldCreator.createWorld(WorldName.NECROPHOBIA));  // FIXME: 23.06.2025 ХАРДКОД
            mapController.setCurrentNameLocation(RoomName.BASEMENT); // FIXME: 21.06.2025 в будущем через json

            gameMenu = new UiController(UiFactory.createGameUiStage(mapController.getCurrentPlace()));
        }
    }

    @Override
    public void render(float delta) {
        renderGameObj(delta);
        renderUi(delta);

        update();
    }

    private void renderGameObj(float delta){
        gameViewport.apply();
        batch.setProjectionMatrix(gameCamera.combined);
        mapController.render();
    }

    private void renderUi(float delta){
        uiViewport.apply();
        batch.setProjectionMatrix(uiCamera.combined);
        gameMenu.render(delta);
    }

    private void update(){
        updateCameras();
        updateWorld();
    }

    private void updateCameras(){
        gameCamera.update();
        uiCamera.update();
    }

    private void updateWorld(){

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapController.dispose();
        gameMenu.dispose();
    }
}
