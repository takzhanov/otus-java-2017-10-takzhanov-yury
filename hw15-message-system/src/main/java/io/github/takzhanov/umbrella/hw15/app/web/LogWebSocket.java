package io.github.takzhanov.umbrella.hw15.app.web;

import io.github.takzhanov.umbrella.hw15.app.FrontendService;
import io.github.takzhanov.umbrella.hw15.app.Listener;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.Set;

@WebSocket
public class LogWebSocket implements Listener {
    private final FrontendService frontendService;
    private Set<LogWebSocket> users;
    private Session session;

    public LogWebSocket(FrontendService frontendService, Set<LogWebSocket> users) {
        this.frontendService = frontendService;
        this.users = users;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        for (LogWebSocket user : users) {
            try {
                user.getSession().getRemote().sendString(data);
                System.out.println("Sending message: " + data);
            } catch (Exception e) {
                System.out.print(e.toString());
            }
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        frontendService.addListener(this);
        users.add(this);
        setSession(session);
        System.out.println("onOpen");
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        frontendService.removeListener(this);
        users.remove(this);
        System.out.println("onClose");
    }

    @Override
    public void onUpdate() {
        System.out.println("Listener: event from FE ...");
    }
}
