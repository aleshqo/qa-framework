package com.example.qaframework.tests;

import com.example.qaframework.config.QaFrameworkTests;
import com.example.qaframework.helpers.TodoApiHelper;
import com.example.qaframework.model.TodoDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.qaframework.utils.TestUtils.checkStatusCode;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QaFrameworkTests
public class GetTodoTests {

    @Autowired
    private TodoApiHelper todoApiHelper;

    @BeforeEach
    public void beforeEach() {
        todoApiHelper.clearAllTodos();
    }

    @Test
    void shouldReturnEmptyTodoListInitially() {
        List<TodoDto> todoDtoList = todoApiHelper.getTodos(0, 10);
        assertEquals(0, todoDtoList.size());
    }

    @Test
    void shouldReturnTodosWithPagination() {
        todoApiHelper.createTodo(new TodoDto(1, "Task 1", false));
        todoApiHelper.createTodo(new TodoDto(2, "Task 2", false));

        List<TodoDto> todoDtoList = todoApiHelper.getTodos(0, 1);
        assertEquals(1, todoDtoList.size());
    }

    @Test
    void shouldHandleInvalidOffsetAndLimit() {
        Response response = todoApiHelper.sendGetTodo(-1, 0);
        checkStatusCode(HTTP_BAD_REQUEST, response.statusCode());
    }
}
