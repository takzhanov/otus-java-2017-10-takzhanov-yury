package io.github.takzhanov.umbrella.hw12;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public enum PageGenerator {
    INSTANCE;

    private static final String TML_DIR = "/ftl/";
    private final Configuration cfg;

    PageGenerator() {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(this.getClass(), TML_DIR);
    }

    public String getPage(String fileName) {
        return getPage(fileName, null);
    }

    public String getPage(String fileName, Map<String, Object> pageVariables) {
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(fileName);
            template.process(pageVariables, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }
}
