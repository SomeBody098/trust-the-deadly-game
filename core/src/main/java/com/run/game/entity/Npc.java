package com.run.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.run.game.utils.exception.FailedConnectionException;

import java.text.ParseException;

public class Npc implements Entity{

    private final static String URL = "http://localhost:8080"; // FIXME: 30.06.2025 обязательно путь к серверу передавай через json файл сюда, этот json в git - не клади!!!

    private final Net.HttpResponseListener httpResponseListener;

    private boolean isWaitResponse = false;

    public Npc() {
        httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                isWaitResponse = true;

                Gdx.app.log("request", "Npc been give request to server...");
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        String response = httpResponse.getResultAsString();
                        while (response.isEmpty()) {
                            // FIXME: 04.07.2025 ИГРА ТУТ В ЛОВУШКЕ! - ПОФИКСИ ЭТО (чтобы метод "ждал" пока сервер не даст ему ответ)
                        }

                        Gdx.app.log("response", "Npc been take response to server! The response: " + response);
                    }
                });
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("Failed connect", "Cannot connecting to server", t);
                isWaitResponse = false;
                throw new FailedConnectionException(t);
            }

            @Override
            public void cancelled() {
                Gdx.app.error("Cancelled", "Server canceled the request");
                isWaitResponse = false;
                throw new FailedConnectionException();
            }
        };
    }

    public void giveRequest(String args) throws ParseException {
        if (isWaitResponse) return;

        if (args.contains("/")) throw new ParseException("Symbols '/' - is forbidden use.", 128);

        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url(URL + "/response/" + args).build();

        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);

        // FIXME: 04.07.2025 придумай - как заставить LibGDX ждать ответа от сервера
    }

}
