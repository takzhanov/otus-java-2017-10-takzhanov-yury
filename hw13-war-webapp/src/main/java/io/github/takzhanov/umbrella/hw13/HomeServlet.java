package io.github.takzhanov.umbrella.hw13;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw11.cache.CacheInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    private DbService dbService;
    private CacheInfo cacheInfo;

    @Autowired
    public void setDbService(DbService dbService) {
        this.dbService = dbService;
    }

    @Autowired
    @Qualifier("cacheEngineForSpringImpl")
    public void setCacheInfo(CacheInfo cacheInfo) {
        this.cacheInfo = cacheInfo;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> page = new HashMap<>();
        page.put("metadata", dbService.getMetaData().replace("\n", "<br/>"));
        request.setAttribute("page", page);
        request.setAttribute("hit", cacheInfo.getHitCount());
        request.setAttribute("miss", cacheInfo.getMissCount());
        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }

}
