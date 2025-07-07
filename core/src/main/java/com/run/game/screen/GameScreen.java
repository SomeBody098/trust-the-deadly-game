package com.run.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.entity.npc.Npc;
import com.run.game.entity.npc.NpcCreator;
import com.run.game.map.MapController;
import com.run.game.map.WorldName;
import com.run.game.map.RoomName;
import com.run.game.map.WorldCreator;
import com.run.game.ui.UiController;
import com.run.game.ui.UiFactory;
import com.run.game.utils.net.Language;
import com.run.game.utils.net.NetManager;

public class GameScreen implements Screen {

    private final Main main;
    private final SpriteBatch batch;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;

    private final FitViewport gameViewport;
    private final ScreenViewport uiViewport;

    private MapController mapController;
    private UiController gameMenu;

    private Npc npc = NpcCreator.createNpc("tutorial", Language.RUSSIAN); // FIXME: 07.07.2025 ВРЕМЕННО

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
        if (gameMenu == null || mapController == null) {
            if (!WorldCreator.isLoadTextureNecrophobiaWorld()) {
                WorldCreator.loadTextureWorld(WorldName.NECROPHOBIA);  // FIXME: 23.06.2025 ХАРДКОД - в будущем через json (определение генерации мира)
                main.setScreen(new LoadingScreen(main, this, batch, uiCamera, uiViewport));
                return;
            }

            mapController = new MapController(batch, gameCamera, WorldCreator.createWorld(WorldName.NECROPHOBIA));  // FIXME: 23.06.2025 ХАРДКОД
            mapController.setCurrentNameLocation(RoomName.BASEMENT); // FIXME: 21.06.2025 ХАРДКОД - в будущем через json (определение текущей локации)

            gameMenu = new UiController(UiFactory.createGameUiStage(mapController.getCurrentPlace(), npc));
        }
    }

    @Override
    public void render(float delta) {
        renderGameObj(delta);
        renderUi(delta);

        update();

        if (!NetManager.isIsWaitResponse()){
            Gdx.app.log("result", NetManager.getResult());
        }
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
