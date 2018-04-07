package io.github.takzhanov.umbrella.hw15.app.web;

import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw12.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class InfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> page = new HashMap<>();
        page.put("username", ((UserDataSet) request.getSession().getAttribute("user")).getName());
        response.getWriter().write(PageGenerator.INSTANCE.getPage("home.html", page));
    }
}
