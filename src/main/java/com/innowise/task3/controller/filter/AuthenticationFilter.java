package com.innowise.task3.controller.filter;

import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.CommandProvider;
import com.innowise.task3.controller.implementation.InvalidRequestExecutor;
import com.innowise.task3.controller.implementation.LoginExecutor;
import com.innowise.task3.controller.security.PermissionEvaluator;
import com.innowise.task3.controller.utils.ControllerUtils;
import com.innowise.task3.entity.dto.EmployeeDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;
import com.innowise.task3.service.ServiceProvider;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private static final String INVALID_REQUEST_URI = String.valueOf(CommandName.INVALID_REQUEST.getUri());
    private static final String REQUEST_NOT_PERMITTED_FOR_THIS_ACCESS_LEVEL = "Request not permitted for this access level";
    private static final String CAN_NOT_ACCESS_OTHER_COMPANYS_EMPLOYEES = "Can not access other company's employees";
    private final CommandProvider provider = CommandProvider.getInstance();
    private final PermissionEvaluator permissionEvaluator = new PermissionEvaluator();

    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();

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
        int roleId = (int) session.getAttribute(LoginExecutor.ROLE_TOKEN);
        int usersCompanyId = (int) session.getAttribute(LoginExecutor.COMPANY_ID);

        // role check
        if (commandName != CommandName.LOGIN
                && !permissionEvaluator.isAccessLevelEnoughForCommand(roleId,commandName)) {

            request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, REQUEST_NOT_PERMITTED_FOR_THIS_ACCESS_LEVEL);
            request.getRequestDispatcher(INVALID_REQUEST_URI).forward(request,response);
            return;

        }

        // company check
        if (commandName.getUri().equals(CommandName.URI.EMPLOYEE)) {

            int requestUserId = ControllerUtils.getIdFromLastQuerySegment(httpServletRequest.getServletPath());

            try {
                EmployeeDTO employeeDTO = employeeService.getEmployeeWithId(requestUserId);
                if (employeeDTO.getCompany() != usersCompanyId) {
                    throw new ServiceException();
                }
                chain.doFilter(request, response);

            } catch (ServiceException e) {
                request.setAttribute(InvalidRequestExecutor.ERROR_MESSAGE, CAN_NOT_ACCESS_OTHER_COMPANYS_EMPLOYEES);
                request.getRequestDispatcher(INVALID_REQUEST_URI).forward(request,response);
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
