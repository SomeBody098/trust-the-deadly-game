package com.run.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.map.WorldCreator;
import com.run.game.screen.MainMenuScreen;
import com.run.game.ui.UiFactory;
import com.run.game.utils.exception.NotInitializedObjectException;

public class Main extends Game {

    private SpriteBatch batch;
    private OrthographicCamera gameCamera;
    private OrthographicCamera uiCamera;

    private FitViewport gameViewport;
    private ScreenViewport uiViewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        createCameras();
        createViewports();
        init();

        setScreen(new MainMenuScreen(this, batch, uiCamera, uiViewport, gameCamera, gameViewport));
    }

    private void createCameras(){
        gameCamera = new OrthographicCamera(
            Gdx.graphics.getWidth() * ((float) 1 / 16),
            Gdx.graphics.getHeight() * ((float) 1 / 16)
        );
        gameCamera.setToOrtho(false);
        gameCamera.update();

        uiCamera = new OrthographicCamera(
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );
        uiCamera.setToOrtho(false);
        uiCamera.update();
    }

    private void createViewports(){
        if (gameCamera == null || uiCamera == null){
            throw new NotInitializedObjectException("gameCamera or uiCamera not initialized!");
        }

        gameViewport = new FitViewport(
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight(),
            gameCamera
        );

        uiViewport = new ScreenViewport(uiCamera);
    }

    private void init(){
        WorldCreator.init();
        UiFactory.init(uiCamera, uiViewport, batch);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        WorldCreator.dispose();  // FIXME: 23.06.2025 ПЕРЕСМОТРИ
        batch.dispose();
    }
}
