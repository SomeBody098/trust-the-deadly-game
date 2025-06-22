package com.run.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.Main;
import com.run.game.ui.UiGraphic;

public class LoadingScreen implements Screen {

    private final Main main;
    private final Screen currentScreen;

    private final SpriteBatch batch;

    private final OrthographicCamera uiCamera;
    private final ScreenViewport uiViewport;

    private BitmapFont loadProgress;
    private ShapeRenderer progressRender;

    public LoadingScreen(Main main, Screen currentScreen, SpriteBatch batch, OrthographicCamera uiCamera, ScreenViewport uiViewport) {
        this.main = main;
        this.currentScreen = currentScreen;
        this.batch = batch;
        this.uiCamera = uiCamera;
        this.uiViewport = uiViewport;
    }

    @Override
    public void show() {
        loadProgress = new BitmapFont();
        progressRender = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        float screenWidth = uiViewport.getScreenWidth();
        float progress = UiGraphic.getProgress() * 100;

        // Width of progress bar on screen relevant to Screen width
        float progressBarWidth = (screenWidth / 100) * progress;

        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        loadProgress.draw(batch, "Loading " + progress + " / " + 100, 10, 40);
        batch.end();

        progressRender.setProjectionMatrix(uiCamera.combined);
        progressRender.begin(ShapeRenderer.ShapeType.Filled);
        progressRender.setColor(Color.RED);
        progressRender.rect(0, 10, progressBarWidth, 10);
        progressRender.end();

        if (UiGraphic.loading()) moveToCurrentScreen();
    }

    private void moveToCurrentScreen() {
        main.setScreen(currentScreen);

        dispose();
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
        loadProgress.dispose();
        progressRender.dispose();
    }
}
