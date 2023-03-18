package com.innowise.task3.controller.filter;

import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.CommandProvider;
import com.innowise.task3.controller.implementation.LoginExecutor;
import com.innowise.task3.controller.security.PermissionEvaluator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private final CommandProvider provider = new CommandProvider();
    private final PermissionEvaluator permissionEvaluator = new PermissionEvaluator();

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
        int roleId = (int) session.getAttribute(LoginExecutor.ACCESS_TOKEN);
        if (commandName != CommandName.LOGIN
                && !permissionEvaluator.isAccessLevelEnoughForCommand(roleId,commandName)) {
            //((HttpServletResponse) response).sendRedirect(CommandName.URI.ERROR);
            request.setAttribute(LoginExecutor.ERROR_MESSAGE,"unable to authenticate");
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
