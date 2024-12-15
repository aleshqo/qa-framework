package com.example.qaframework.helpers.exceptions;

/**
 * Исключение для ошибок парсинга json
 */
public class JsonParseException extends RuntimeException {
    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
