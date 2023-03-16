package com.innowise.task3.controller;

import com.innowise.task3.controller.implementation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CommandProvider {

    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.ADD_EMPLOYEE, new AddEmployeeExecutor());
        commands.put(CommandName.DELETE_EMPLOYEE_WITH_ID, new DeleteEmployeeWithIdExecutor());
        commands.put(CommandName.EDIT_EMPLOYEE_WITH_ID, new EditEmployeeWithIdExecutor());
        commands.put(CommandName.GET_EMPLOYEES, new GetEmployeesExecutor());
        commands.put(CommandName.GET_EMPLOYEE_WITH_ID, new GetEmployeeWithIdExecutor());
        commands.put(CommandName.INVALID_REQUEST, new InvalidRequestExecutor());
    }


    public Command getCommand(String name) {
        Command command;
        try {
            CommandName commandName = CommandName.valueOf(name.toUpperCase());
            command = commands.get(commandName);
            return command;
        } catch (IllegalArgumentException e) { // no such command
            command = commands.get(CommandName.INVALID_REQUEST);
            return command;
        }
    }
}
