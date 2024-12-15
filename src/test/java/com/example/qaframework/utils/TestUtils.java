package com.example.qaframework.utils;

import com.example.qaframework.model.TodoDto;
import lombok.experimental.UtilityClass;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Утилитный класс для работы с тестами
 */
@UtilityClass
public class TestUtils {

    public static void checkStatusCode(int expectedCode, int actualCode) {
        assertEquals(expectedCode, actualCode, "Неверный ответ на запрос");
    }

    public static void checkTodoData(TodoDto expectedTodo, TodoDto actualTodo) {
        assertAll(() -> {
            assertEquals(expectedTodo.getId(), actualTodo.getId(), "Неверный id задачи");
            assertEquals(expectedTodo.getText(), actualTodo.getText(), "Неверный text задачи");
            assertEquals(expectedTodo.isCompleted(), actualTodo.isCompleted(), "Неверный статус задачи");
        });
    }
}
