package com.run.game.utils.net;

import com.badlogic.gdx.Gdx;
import com.run.game.utils.exception.FailedConnectionException;
import com.run.game.utils.exception.ServerException;
import com.run.game.utils.exception.UnexpectedBehaviorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

public class NetManager {

    private final static String ROOT_URL = "http://localhost:8080"; // FIXME: 30.06.2025 обязательно путь к серверу передавай через json файл сюда, этот json в git - не клади!!! (или потом, как-то, удали)

    private static boolean isWaitResponse = false;

    private static HttpURLConnection connection = null;

    private static String result = null;

    public static void checkConnectionWithServer(){
        connecting(ROOT_URL + "/test", Method.GET);
    }

    public static void giveRequestNpc(String args) throws ParseException {
        if (args.contains("/")) throw new ParseException("Symbols '/' - is forbidden use.", 128);

        connecting(ROOT_URL + "/response/" + args, Method.POST);
    }

    public static void connecting(String url, Method method){
        if (isWaitResponse) {
            Gdx.app.log("NetManager", "Response not been get - please wait for next request.");
            return;
        }

        new Thread(() -> {
            result = readAndGetResponseFromServer(url, method);
        }).start();
    }

    private static String readAndGetResponseFromServer(String url, Method method){
        isWaitResponse = true;

        String result;
        try {
            createDefaultConnection(url, method);

            Gdx.app.log("Connection", "connecting to server...");
            connection.connect();

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
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

    private static void disposeConnection(){
        Gdx.app.log("Disconnect", "disconnecting...");

        connection.disconnect();
        connection = null;
    }

}
