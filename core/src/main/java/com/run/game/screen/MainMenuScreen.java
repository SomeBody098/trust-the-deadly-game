package com.run.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.ui.UiController;
import com.run.game.ui.UiFactory;
import com.run.game.ui.UiGraphic;

public class MainMenuScreen implements Screen {

    private final Main main;

    private final SpriteBatch batch;

    private final OrthographicCamera gameCamera;
    private final FitViewport gameViewport;

    private final OrthographicCamera uiCamera;
    private final ScreenViewport uiViewport;

    private UiController mainMenu;

    public MainMenuScreen(Main main, SpriteBatch batch, OrthographicCamera uiCamera, ScreenViewport uiViewport, OrthographicCamera gameCamera, FitViewport gameViewport) {
        this.main = main;
        this.batch = batch;
        this.uiCamera = uiCamera;
        this.uiViewport = uiViewport;
        this.gameCamera = gameCamera;
        this.gameViewport = gameViewport;
    }

    @Override
    public void show() {
        if (mainMenu == null) {
            if (!UiGraphic.isLoadTexturesForMainMenu()) {
                UiGraphic.downloadTexturesForMainMenu();
                main.setScreen(new LoadingScreen(main, this, batch, uiCamera, uiViewport));
                return;
            }

            mainMenu = new UiController(UiFactory.createMainMenuStage(main, new GameScreen(main, batch, gameCamera, uiCamera, gameViewport, uiViewport)));
        }
    }

    @Override
    public void render(float delta) {
        uiViewport.apply();
        batch.setProjectionMatrix(uiCamera.combined);

        mainMenu.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height);
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
        mainMenu.dispose();
    }
}
