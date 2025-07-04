package com.run.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.run.game.map.WorldCreator;
import com.run.game.screen.MainMenuScreen;
import com.run.game.ui.UiFactory;
import com.run.game.utils.exception.FailedConnectionException;
import com.run.game.utils.exception.NotInitializedObjectException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main extends Game {

    private SpriteBatch batch;
    private OrthographicCamera gameCamera;
    private OrthographicCamera uiCamera;

    private FitViewport gameViewport;
    private ScreenViewport uiViewport;

    @Override
    public void create() {
        checkConnectionWithServer();

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

    private void checkConnectionWithServer(){
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder(); // FIXME: 30.06.2025 начало: вот эта часть должна блокировать (выдавать FailedConnectionException), если к серверу с AI нельзя подключиться
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://localhost:8080/test").build(); // FIXME: 30.06.2025 обязательно путь к серверу передавай через json файл сюда, этот json в git - не клади!!!

        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("response", httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("Failed connect", "Cannot connecting to server", t);
                throw new FailedConnectionException(t);
            }

            @Override
            public void cancelled() {
                Gdx.app.error("Cancelled", "Server canceled the request");
                throw new FailedConnectionException();
            }
        };

        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener); // FIXME: 30.06.2025 конец
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
