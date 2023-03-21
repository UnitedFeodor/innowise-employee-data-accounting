import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.CommandProvider;
import com.innowise.task3.controller.DispatcherServlet;
import com.innowise.task3.controller.implementation.LoginExecutor;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControllerTest {
    private final CommandProvider commandProvider = CommandProvider.getInstance();
    @Test
    void commandProviderReturns() {

        String employeesUri = "/employees";

        assertEquals(CommandName.GET_EMPLOYEES, commandProvider.getCommandName(employeesUri, CommandName.HttpMethod.GET));
        assertEquals(CommandName.ADD_EMPLOYEE, commandProvider.getCommandName(employeesUri, CommandName.HttpMethod.POST));

        String employeeIdUri = "/employee/12";
        assertEquals(CommandName.GET_EMPLOYEE_WITH_ID, commandProvider.getCommandName(employeeIdUri, CommandName.HttpMethod.GET));
        assertEquals(CommandName.DELETE_EMPLOYEE_WITH_ID, commandProvider.getCommandName(employeeIdUri, CommandName.HttpMethod.DELETE));
        assertEquals(CommandName.EDIT_EMPLOYEE_WITH_ID,  commandProvider.getCommandName(employeeIdUri, CommandName.HttpMethod.PUT));

        String errorUri = "/error";
        assertEquals(CommandName.INVALID_REQUEST, commandProvider.getCommandName(errorUri, CommandName.HttpMethod.GET));

        String invalidUri = "/asdasdasdasdas";
        assertEquals(CommandName.INVALID_REQUEST, commandProvider.getCommandName(invalidUri, CommandName.HttpMethod.GET));

        String sessionLoginLogoutUri = "/session";
        assertEquals(CommandName.LOGIN, commandProvider.getCommandName(sessionLoginLogoutUri, CommandName.HttpMethod.POST));
        assertEquals(CommandName.LOGOUT, commandProvider.getCommandName(sessionLoginLogoutUri, CommandName.HttpMethod.DELETE));

    }

}
