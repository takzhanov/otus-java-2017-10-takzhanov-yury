package io.github.takzhanov.umbrella.hw13;

import io.github.takzhanov.umbrella.hw12.AuthFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*")
public class SpringAuthFilter extends AuthFilter {
}
