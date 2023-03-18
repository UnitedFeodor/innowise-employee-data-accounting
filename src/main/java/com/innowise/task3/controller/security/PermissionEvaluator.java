package com.innowise.task3.controller.security;

import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.implementation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionEvaluator {

    private final int USER_ACCESS = 1;
    private final int ADMIN_ACCESS = 2;

    private final int USER_ROLE_ID = 1;
    private final int ADMIN_ROLE_ID = 2;

    private final Map<Integer,Integer> roleIdAccessLevelMap = new HashMap<>();
    private final Map<CommandName, Integer> commandAccessLevelMap = new HashMap<>();

    public PermissionEvaluator() {
        commandAccessLevelMap.put(CommandName.GET_EMPLOYEES,USER_ACCESS);
        commandAccessLevelMap.put(CommandName.GET_EMPLOYEE_WITH_ID,USER_ACCESS);
        commandAccessLevelMap.put(CommandName.LOGOUT,USER_ACCESS);

        commandAccessLevelMap.put(CommandName.ADD_EMPLOYEE,ADMIN_ACCESS);
        commandAccessLevelMap.put(CommandName.DELETE_EMPLOYEE_WITH_ID,ADMIN_ACCESS);
        commandAccessLevelMap.put(CommandName.EDIT_EMPLOYEE_WITH_ID,ADMIN_ACCESS);

        roleIdAccessLevelMap.put(USER_ROLE_ID, USER_ACCESS);
        roleIdAccessLevelMap.put(ADMIN_ROLE_ID, ADMIN_ACCESS);
    }

    public boolean isAccessLevelEnoughForCommand(int roleId, CommandName commandName) {
        return commandAccessLevelMap.get(commandName) <= roleIdAccessLevelMap.get(roleId);
    }

}
