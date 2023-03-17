package com.innowise.task3.controller.filter;

import com.innowise.task3.controller.CommandName;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpSession session = ((HttpServletRequest) request).getSession(false);

        String commandNameStr = request.getParameter(JSPConstants.COMMAND);
        if (commandNameStr == null) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"no command passed in request");
            chain.doFilter(filteredRequest,response);

        } else {
            try {
                CommandName commandName = CommandName.valueOf(commandNameStr.toUpperCase());
                String userRole = (String) session.getAttribute(UserConstants.USER_ROLE);
                if(Security.canExecuteThisRequest(userRole, commandName)) {
                    chain.doFilter(request,response);

                } else {
                    session.setAttribute(JSPConstants.ERROR_MESSAGE,"cannot do this request for this role");
                    chain.doFilter(filteredRequest,response);
                }
            } catch (IllegalArgumentException e) {
                session.setAttribute(JSPConstants.ERROR_MESSAGE,"invalid command passed in request");
                chain.doFilter(filteredRequest,response);
            }


        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
