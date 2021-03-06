package io.github.takzhanov.umbrella.hw12;

import io.github.takzhanov.umbrella.hw09.common.DbService;
import io.github.takzhanov.umbrella.hw11.cache.CacheInfo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HomeServlet extends HttpServlet {
    private DbService dbService;
    private CacheInfo cacheInfo;

    public HomeServlet(DbService dbService, CacheInfo cacheInfo) {
        this.dbService = dbService;
        this.cacheInfo = cacheInfo;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> page = new HashMap<>();
        page.put("metadata", dbService.getMetaData().replace("\n", "<br/>"));
        page.put("hit", cacheInfo.getHitCount());
        page.put("miss", cacheInfo.getMissCount());
        response.getWriter().write(PageGenerator.INSTANCE.getPage("home.html", page));
    }

}
