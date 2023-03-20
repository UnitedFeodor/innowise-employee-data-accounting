package com.innowise.task3.controller;

import com.innowise.task3.controller.implementation.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private static final CommandProvider INSTANCE = new CommandProvider();

    private final Map<CommandName, Command> commands = new HashMap<>();

    private CommandProvider() {
        commands.put(CommandName.ADD_EMPLOYEE, new AddEmployeeExecutor());
        commands.put(CommandName.DELETE_EMPLOYEE_WITH_ID, new DeleteEmployeeWithIdExecutor());
        commands.put(CommandName.EDIT_EMPLOYEE_WITH_ID, new EditEmployeeWithIdExecutor());
        commands.put(CommandName.GET_EMPLOYEES, new GetEmployeesExecutor());
        commands.put(CommandName.GET_EMPLOYEE_WITH_ID, new GetEmployeeWithIdExecutor());
        commands.put(CommandName.INVALID_REQUEST, new InvalidRequestExecutor());
        commands.put(CommandName.LOGIN, new LoginExecutor());
        commands.put(CommandName.LOGOUT, new LogoutExecutor());
    }

    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    public Command getCommand(String uri, String httpMethod) {
        return commands.get(getCommandName(uri, httpMethod));
    }

    public CommandName getCommandName(String uri, String httpMethod) {
        for(CommandName currName : CommandName.values()) {
            if(uri.contains(currName.getUri()) && httpMethod.equals(currName.getHttpMethod())) {
                return currName;
            }
        }
        return CommandName.INVALID_REQUEST;
    }
}
