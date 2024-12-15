package com.example.qaframework.tests;

import com.example.qaframework.config.QaFrameworkTests;
import com.example.qaframework.helpers.TodoApiHelper;
import com.example.qaframework.model.TodoDto;
import com.example.qaframework.utils.TestUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.qaframework.utils.TestUtils.checkTodoData;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QaFrameworkTests
public class PutTodoTests {

    @Autowired
    private TodoApiHelper todoApiHelper;

    @BeforeEach
    public void beforeEach() {
        todoApiHelper.clearAllTodos();
    }

    @Test
    void shouldUpdateTodoSuccessfully() {
        TodoDto newTodo = new TodoDto(1, "Task 1", false);
        todoApiHelper.createTodo(newTodo);

        TodoDto updatedTodo = new TodoDto(newTodo.getId(), "Updated Task", true);
        todoApiHelper.updateTodo(newTodo.getId(), updatedTodo);

        List<TodoDto> todoDtoList = todoApiHelper.getTodos(null, null);
        assertEquals(1, todoDtoList.size(), "Список должен содержать одну задачу");
        checkTodoData(updatedTodo, todoDtoList.get(0));
    }

    @Test
    void shouldNotUpdateNonExistentTodo() {
        int todoId = 999;
        Response response = todoApiHelper
                .sendUpdateTodo(todoId, new TodoDto(todoId, "Non-existent Task", false));

        TestUtils.checkStatusCode(HTTP_NOT_FOUND, response.statusCode());
        assertTrue(todoApiHelper.getTodos(null, null).isEmpty(), "Задача не должна быть создана");
    }
}
