package io.github.takzhanov.umbrella.hw15.main;

import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw12.AuthFilter;
import io.github.takzhanov.umbrella.hw12.LoginServlet;
import io.github.takzhanov.umbrella.hw15.app.DbService;
import io.github.takzhanov.umbrella.hw15.app.FrontendService;
import io.github.takzhanov.umbrella.hw15.app.MessageSystemContext;
import io.github.takzhanov.umbrella.hw15.app.web.InfoServlet;
import io.github.takzhanov.umbrella.hw15.app.web.LogWebSocketServlet;
import io.github.takzhanov.umbrella.hw15.db.DbServiceImpl;
import io.github.takzhanov.umbrella.hw15.front.FrontendServiceImpl;
import io.github.takzhanov.umbrella.hw15.ms.Address;
import io.github.takzhanov.umbrella.hw15.ms.MessageSystem;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main {
    private static final String PUBLIC_HTML = "/static";

    public static void main(String[] args) throws Exception {
        MessageSystem messageSystem = new MessageSystem();

        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setDbAddress(dbAddress);

        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        DbService dbService = new DbServiceImpl(context, dbAddress);
        dbService.init();
        prepareTestData(dbService);

        messageSystem.start();

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(PUBLIC_HTML);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler webContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webContext.setContextPath("/AdminWebapp");
        //фильтр и логин работают по-старому
        webContext.addFilter(new FilterHolder(new AuthFilter()), "/*", EnumSet.allOf(DispatcherType.class));
        webContext.addServlet(new ServletHolder(new LoginServlet(dbService)), "/login");
        webContext.addServlet(new ServletHolder(new InfoServlet()), "/home");
        //все остальное общается с системой через Frontend
        webContext.addServlet(new ServletHolder(new LogWebSocketServlet(frontendService)), "/log");

        Server server = new Server(8080);
        server.setHandler(new HandlerList(resourceHandler, webContext));
        server.start();
        server.join();

        Thread.sleep(1000);
        messageSystem.dispose();
    }

    private static void prepareTestData(DbService dbService) {
        dbService.dropTables();
        dbService.prepareTables();
        dbService.saveUser(new UserDataSet("root", 20));
        dbService.saveUser(new UserDataSet("jorj", 21));
        dbService.saveUser(new UserDataSet("alex", 22));
    }

}
