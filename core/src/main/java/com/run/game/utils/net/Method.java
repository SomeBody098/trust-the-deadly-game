package com.run.game.utils.net;

public enum Method {
    GET("GET"),
    POST("POST")
    ;

    private final String method;

    Method(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
