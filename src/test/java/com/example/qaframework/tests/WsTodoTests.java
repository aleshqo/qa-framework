package com.example.qaframework.tests;

import com.example.qaframework.config.QaFrameworkTests;
import com.example.qaframework.config.TestConfig;
import com.example.qaframework.helpers.TodoApiHelper;
import com.example.qaframework.model.TodoDto;
import com.example.qaframework.utils.TodoWebSocketClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;

@QaFrameworkTests
public class WsTodoTests {

    @Autowired
    private TodoApiHelper todoApiHelper;
    @Autowired
    private TestConfig testConfig;
    @Autowired
    private TodoWebSocketClient webSocketClient;

    @Test
    void shouldReceiveNotificationsOnWebSocketForNewTodos() {
        webSocketClient.connect();
        try {
            todoApiHelper.createTodo(new TodoDto(1, "Task via WebSocket", false));

            await("Waiting new task")
                    .atMost(15000, MILLISECONDS)
                    .pollInterval(500, MILLISECONDS)
                    .until(() -> {
                        List<String> messages = webSocketClient.getReceivedMessages();
                        return !messages.isEmpty() && messages.get(0).contains("Task via WebSocket");
                    });
        } finally {
            webSocketClient.close();
        }
    }
}
