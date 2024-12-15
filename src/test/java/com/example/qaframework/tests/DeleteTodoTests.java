package com.example.qaframework.tests;

import com.example.qaframework.config.QaFrameworkTests;
import com.example.qaframework.helpers.TodoApiHelper;
import com.example.qaframework.model.TodoDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.qaframework.utils.TestUtils.checkStatusCode;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QaFrameworkTests
public class DeleteTodoTests {

    @Autowired
    private TodoApiHelper todoApiHelper;

    @BeforeEach
    public void beforeEach() {
        todoApiHelper.clearAllTodos();
    }


    @Test
    void shouldDeleteTodoSuccessfully() {
        todoApiHelper.createTodo(new TodoDto(1, "Task to delete", false));
        todoApiHelper.deleteTodo(1);

        assertTrue(todoApiHelper.getTodos(null, null).isEmpty(), "Задача должна быть удалена");
    }

    @Test
    void shouldRequireAuthorizationForDelete() {
        todoApiHelper.createTodo(new TodoDto(1, "Task 1", false));

        Response response = todoApiHelper.sendDeleteTodo(1, "wrong", "credentials");
        checkStatusCode(HTTP_UNAUTHORIZED, response.statusCode());
    }
}
