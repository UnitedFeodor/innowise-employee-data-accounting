package com.innowise.task3.controller.filter;

import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.CommandProvider;
import com.innowise.task3.controller.implementation.LoginExecutor;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    private static final String REQUEST_NOT_AUTHORIZED = "Request not authorized";
    private final CommandProvider provider = new CommandProvider();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String uri = httpServletRequest.getServletPath();
        String httpMethod = httpServletRequest.getMethod();
        CommandName commandName = provider.getCommandName(uri, httpMethod);

        HttpSession session = httpServletRequest.getSession(false);
        if (commandName == CommandName.LOGIN) {
            request.getRequestDispatcher(httpServletRequest.getServletPath()).forward(request, response);

        } else if (session == null || session.getAttribute(LoginExecutor.ID_TOKEN) == null) {
            request.setAttribute(LoginExecutor.ERROR_MESSAGE, REQUEST_NOT_AUTHORIZED);
            request.getRequestDispatcher(String.valueOf(CommandName.INVALID_REQUEST.getUri())).forward(request,response);

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
