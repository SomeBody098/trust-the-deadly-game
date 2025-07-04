package com.run.game.utils.exception;

// when the app cannot connect to server (AI)
public class FailedConnectionException extends RuntimeException {

    public FailedConnectionException(String url) {
        super("App cannot connect to: " + url);
    }

    public FailedConnectionException(Throwable t) {
        super(t);
    }

    public FailedConnectionException() {
        super("App cannot connect to server");
    }
}
