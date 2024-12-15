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
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QaFrameworkTests
public class PostTodoTests {

    @Autowired
    private TodoApiHelper todoApiHelper;

    @BeforeEach
    public void beforeEach() {
        todoApiHelper.clearAllTodos();
    }

    @Test
    void shouldCreateTodoSuccessfully() {
        TodoDto newTodo = new TodoDto(1, "Test TODO", false);
        todoApiHelper.createTodo(newTodo);
        List<TodoDto> todoDtoList = todoApiHelper.getTodos(null, null);
        assertEquals(1, todoDtoList.size(), "Список должен содержать одну задачу");
        checkTodoData(newTodo, todoDtoList.get(0));
    }

    @Test
    void shouldNotAllowDuplicateIds() {
        todoApiHelper.createTodo(new TodoDto(1, "Task 1", false));

        Response response = todoApiHelper.sendPostTodo(new TodoDto(1, "Task 2", false));
        TestUtils.checkStatusCode(HTTP_BAD_REQUEST, response.statusCode());

        List<TodoDto> todoDtoList = todoApiHelper.getTodos(null, null);
        assertEquals(1, todoDtoList.size(), "Список должен содержать одну задачу");
    }

    @Test
    void shouldRequireTextField() {
        TodoDto todo = new TodoDto(2, null, false);
        Response response = todoApiHelper.sendPostTodo(todo);
        assertEquals(HTTP_BAD_REQUEST, response.statusCode());
        assertTrue(todoApiHelper.getTodos(null, null).isEmpty(), "Задача не должна быть создана");
    }
}
