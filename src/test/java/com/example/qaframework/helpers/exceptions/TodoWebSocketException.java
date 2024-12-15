package com.example.qaframework.helpers.exceptions;

/**
 * Исключения для websocket
 */
public class TodoWebSocketException extends RuntimeException {
    public TodoWebSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
