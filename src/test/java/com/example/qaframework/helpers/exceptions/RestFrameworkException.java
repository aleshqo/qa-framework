package com.example.qaframework.helpers.exceptions;

/**
 * Исключение для ошибок rest хелпера
 */
public class RestFrameworkException extends RuntimeException {

    public RestFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
