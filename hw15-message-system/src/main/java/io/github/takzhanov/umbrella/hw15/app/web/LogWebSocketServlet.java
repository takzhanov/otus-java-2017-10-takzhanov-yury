package io.github.takzhanov.umbrella.hw15.app.web;

import io.github.takzhanov.umbrella.hw15.app.FrontendService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class LogWebSocketServlet extends WebSocketServlet {
    private static final long LOGOUT_TIME = 10 * 60 * 1000;
    private final FrontendService frontendService;

    public LogWebSocketServlet(FrontendService frontendService) {
        this.frontendService = frontendService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new LogWebSocketCreator(frontendService));
    }
}
