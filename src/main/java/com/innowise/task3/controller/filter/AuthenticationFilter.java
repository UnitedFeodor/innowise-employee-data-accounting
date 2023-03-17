package com.innowise.task3.controller.filter;

import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.implementation.LoginExecutor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session == null || session.getAttribute(LoginExecutor.ID_TOKEN) == null) {
            ((HttpServletResponse) response).sendRedirect(CommandName.URI.URI_LOGIN);
        }

        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
