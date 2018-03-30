package io.github.takzhanov.umbrella.hw12.main;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw11.cache.CacheEngine;
import io.github.takzhanov.umbrella.hw11.cache.CacheEngineImpl;
import io.github.takzhanov.umbrella.hw12.AuthFilter;
import io.github.takzhanov.umbrella.hw12.DbServiceWithLoadingImpl;
import io.github.takzhanov.umbrella.hw12.HomeServlet;
import io.github.takzhanov.umbrella.hw12.LoginServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception {
        CacheEngine<Long, UserDataSet> cacheInfo = new CacheEngineImpl<>(7, 10000, 0, false);
        DbService dbService = new DbServiceWithLoadingImpl(cacheInfo);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/AdminWebapp");
        context.addFilter(new FilterHolder(new AuthFilter()), "/*", EnumSet.allOf(DispatcherType.class));
        context.addServlet(new ServletHolder(new LoginServlet(dbService)), "/login");
        context.addServlet(new ServletHolder(new HomeServlet(dbService, cacheInfo)), "/home");

        Server server = new Server(8080);
        server.setHandler(new HandlerList(context, new DefaultHandler()));
        server.start();
        server.join();
    }
}
