package com.run.game.utils.exception;

// when server give error code (Example: 500 "server error")
public class ServerException extends RuntimeException {
    public ServerException(int code) {
        super("Server error with code: " + code);
    }
}
