package io.github.takzhanov.umbrella.hw15.app.web;

import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw15.app.FrontendService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class LogWebSocketCreator implements WebSocketCreator {
    private final FrontendService frontendService;
    private final Set<LogWebSocket> users;

    public LogWebSocketCreator(FrontendService frontendService) {
        this.frontendService = frontendService;
        this.users = Collections.newSetFromMap(new ConcurrentHashMap<>());
        System.out.println("WebSocketCreator created");
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        UserDataSet userDataSet = (UserDataSet) req.getSession().getAttribute("user");
        LogWebSocket socket = new LogWebSocket(frontendService, users, userDataSet.getName());
        System.out.println("WebSocket created");
        return socket;
    }
}
