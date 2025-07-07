package com.run.game.utils.net;

import com.badlogic.gdx.Gdx;
import com.run.game.utils.exception.FailedConnectionException;
import com.run.game.utils.exception.ServerException;
import com.run.game.utils.exception.UnexpectedBehaviorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.stream.Collectors;

public class NetManager {

    private final static String ROOT_URL = "http://localhost:8080"; // FIXME: 30.06.2025 обязательно путь к серверу передавай через json файл сюда, этот json в git - не клади!!! (или потом, как-то, удали)

    private static HttpURLConnection connection = null;

    private static boolean isWaitResponse = false;
    private static String result = null;

    private static Language language = Language.ENGLISH;

    public static void checkConnectionWithServer(){
        connecting(ROOT_URL + "/test", Method.GET, null);
    }

    public static void giveRequestNpc(String args) throws ParseException {
        if (args.contains("/")) throw new ParseException("Symbols '/' - is forbidden use.", 128);

        connecting(ROOT_URL + "/response", Method.POST, args);
    }

    public static void connecting(String url, Method method, String request){
        if (isWaitResponse) {
            Gdx.app.log("NetManager", "Response not been get - please wait for next request.");
            return;
        }

        new Thread(() -> {
            result = readAndGetResponseFromServer(url, method, request);
        }).start();
    }

    private static String readAndGetResponseFromServer(String url, Method method, String request){
        isWaitResponse = true;

        String result;
        try {
            createDefaultConnection(url, method);

            if (request != null){
                Gdx.app.log("writer", "Send request...");
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
                    writer.write(request);
                    writer.flush();

                    Gdx.app.log("writer", "success");
                }
            }

            Gdx.app.log("Connection", "connecting to server...");
            connection.connect();

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    result = in.lines().collect(Collectors.joining());
                }
            } else {
                isWaitResponse = false;
                throw new ServerException(status);
            }

        } catch (IOException e) {
            isWaitResponse = false;
            throw new FailedConnectionException(e);

        } finally { // TODO: 06.07.2025 поищи альтернативу finally - он ест производительность
            if (connection != null) disposeConnection();
            isWaitResponse = false;
        }

        Gdx.app.log("Response", result);

        return result;
    }

    private static void createDefaultConnection(String url, Method method) throws IOException{
        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        connection.setRequestMethod(method.getMethod());
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(3000000);
        connection.setDoOutput(true);
    }

    public static String getResult() {
        if (result == null) throw new UnexpectedBehaviorException("Result from sever is null!");
        return result;
    }

    public static boolean isIsWaitResponse() {
        return isWaitResponse;
    }

    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language language) {
        NetManager.language = language;
    }

    private static void disposeConnection(){
        Gdx.app.log("Disconnect", "disconnecting...");

        connection.disconnect();
        connection = null;
    }

}
