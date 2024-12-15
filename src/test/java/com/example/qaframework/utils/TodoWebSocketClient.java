package com.example.qaframework.utils;

import com.example.qaframework.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TodoWebSocketClient extends WebSocketClient {

    private final List<String> receivedMessages = new ArrayList<>();

    @Autowired
    public TodoWebSocketClient(TestConfig testConfig) {
        super(URI.create(testConfig.getBaseWsUri()));
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        log.info("Connected to WebSocket!");
    }

    @Override
    public void onMessage(String message) {
        receivedMessages.add(message);
        log.info("Received: {}", message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("WebSocket Closed: {}", reason);
    }

    @Override
    public void onError(Exception ex) {
        log.error("Unexpected WebSocket error: ", ex);
    }

    public List<String> getReceivedMessages() {
        return new ArrayList<>(receivedMessages);
    }

    public void clearReceivedMessages() {
        receivedMessages.clear();
    }
}
