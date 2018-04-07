package io.github.takzhanov.umbrella.hw15.app.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.takzhanov.umbrella.hw15.app.FrontendService;
import io.github.takzhanov.umbrella.hw15.app.Listener;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Set;

@WebSocket
public class LogWebSocket implements Listener {
    private final FrontendService frontendService;
    private Set<LogWebSocket> users;
    private Session session;
    private String username;

    public LogWebSocket(FrontendService frontendService, Set<LogWebSocket> users, String username) {
        this.frontendService = frontendService;
        this.users = users;
        this.username = username;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        Gson gson = new GsonBuilder().create();
        ProtocolMessage protocolMessage = gson.fromJson(data, ProtocolMessage.class);
        System.out.println(protocolMessage);

        if ("sysinfo".equals(protocolMessage.getCommand())) {
            frontendService.showSysInfo(this);
        } else if ("userinfo".equals(protocolMessage.getCommand())) {
            frontendService.showUserInfo(this, username);
        } else {
            for (LogWebSocket user : users) {
                try {
                    user.getSession().getRemote().sendString(protocolMessage.getText());
                    System.out.println("Sending message: " + protocolMessage.getText());
                } catch (Exception e) {
                    System.out.print(e.toString());
                }
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
    public void onFrontendUpdate(String msg) {
        try {
            session.getRemote().sendString("Frontend: " + msg);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
