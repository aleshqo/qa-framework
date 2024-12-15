package com.example.qaframework.helpers;

import com.example.qaframework.config.TestConfig;
import com.example.qaframework.helpers.exceptions.RestFrameworkException;
import com.example.qaframework.model.TodoDto;
import com.example.qaframework.utils.ObjectMapperUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

import static com.example.qaframework.utils.TestUtils.checkStatusCode;
import static java.net.HttpURLConnection.*;

/**
 * Хелпер для работы с тасками.
 */
@Slf4j
@Component
public class TodoApiHelper extends BaseApiHelper {

    private final String baseUri;
    @Value("${auth.login}")
    private String login;
    @Value("${auth.pass}")
    private String password;

    @Autowired
    public TodoApiHelper(TestConfig testConfig) {
        try {
            this.baseUri = testConfig.getBaseUrl() + "/todos";
        } catch (Exception e) {
            String msg = "Error creating base URI";
            log.error(msg, e);
            throw new RestFrameworkException(msg, e);
        }
    }

    /**
     * Получить список задач с поддержкой offset и limit.
     */
    @SneakyThrows
    public List<TodoDto> getTodos(Integer offset, Integer limit) {
        Response response = sendGetTodo(offset, limit);
        checkStatusCode(HTTP_OK, response.statusCode());
        return ObjectMapperUtil.readValue(response.getBody().asString(), new TypeReference<>() {
        });
    }

    /**
     * Отправить запрос на получение списка задач с поддержкой offset и limit.
     */
    public Response sendGetTodo(Integer offset, Integer limit) {
        URI uri = buildUri(offset, limit);
        return get(uri.toString());
    }

    /**
     * Создать задачу.
     */
    public void createTodo(TodoDto todo) {
        Response response = sendPostTodo(todo);
        checkStatusCode(HTTP_CREATED, response.statusCode());
    }

    /**
     * Отправка запроса на создание задачи.
     */
    public Response sendPostTodo(TodoDto todo) {
        log.info("Creating new Todo: {}", todo);
        return post(baseUri, todo);
    }

    /**
     * Обновить задачу по ID.
     */
    public void updateTodo(long id, TodoDto todo) {
        Response response = sendUpdateTodo(id, todo);
        checkStatusCode(HTTP_OK, response.statusCode());
    }

    /**
     * Отправка запроса на обновление задачи.
     */
    public Response sendUpdateTodo(long id, TodoDto todo) {
        String url = baseUri + "/" + id;
        log.info("Updating Todo with ID {}: {}", id, todo);
        return put(url, todo, login, password);
    }

    /**
     * Удалить задачу по ID.
     */
    public void deleteTodo(long id) {
        Response response = sendDeleteTodo(id, login, password);
        checkStatusCode(HTTP_NO_CONTENT, response.statusCode());
    }

    /**
     * Отправка запроса на удаление задачи.
     */
    public Response sendDeleteTodo(long id, String login, String password) {
        String url = baseUri + "/" + id;
        log.info("Deleting Todo with ID '{}'", id);
        return delete(url, login, password);
    }

    /**
     * Очистить все задачи.
     */
    public void clearAllTodos() {
        log.info("Clearing all Todos");
        List<TodoDto> todos = getTodos(null, null);
        todos.forEach(todo -> deleteTodo(todo.getId()));
    }

    /**
     * Построить URI с параметрами.
     */
    @SneakyThrows
    private URI buildUri(Integer offset, Integer limit) {
        URIBuilder builder = new URIBuilder(baseUri);
        if (offset != null) {
            builder.addParameter("offset", String.valueOf(offset));
        }
        if (limit != null) {
            builder.addParameter("limit", String.valueOf(limit));
        }
        return builder.build();
    }
}
