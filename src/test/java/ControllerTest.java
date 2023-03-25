import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.CommandName;
import com.innowise.task3.controller.CommandProvider;
import com.innowise.task3.controller.DispatcherServlet;
import com.innowise.task3.controller.implementation.LoginExecutor;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.entity.dto.EmployeeDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private static final String CONTENT_TYPE = "content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String LOCALHOST_8080 = "http://localhost:8080/task3";
    private static final String ID = "\"id\":";
    private static final String NAME = "\"name\":";
    private static final String SURNAME = "\"surname\":";
    private static final String POSITION = "\"position\":";
    private static final String BIRTH_DATE = "\"birthDate\":";
    private static final String EMAIL = "\"email\":";
    private static final String COMPANY = "\"company\":";
    private static final String ROLE = "\"role\":";
    private static final String PASSWORD = "\"password\":";
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final CommandProvider commandProvider = CommandProvider.getInstance();
    private final DispatcherServlet dispatcherServlet = new DispatcherServlet();

    private Integer addedEmployeeId;

    MockHttpServletRequest getFormedMockRequest(String method, String servletPath, String jsonBody) {
        MockHttpServletRequest request = new MockHttpServletRequest();

        request.setServletPath(servletPath);

        request.setMethod(method);

        request.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        request.setContentType(APPLICATION_JSON);
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        request.setContent(jsonBody.getBytes(StandardCharsets.UTF_8));

        return request;

    }

    @Test
    void loginExecutorResponseIsEmployeeJSON() throws ServletException, IOException {

        String jsonReqBody = "{\n" +
                "    \"email\": \"john@gmail.com\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
        MockHttpServletRequest request = getFormedMockRequest(CommandName.HttpMethod.POST,
                LOCALHOST_8080+CommandName.URI.SESSION,
                jsonReqBody);

        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request,response);

        assertEquals(HttpServletResponse.SC_OK,response.getStatus());
        assertTrue(response.getContentType().contains(APPLICATION_JSON));

        String jsonResponseStr = response.getContentAsString();

        assertTrue(jsonResponseStr.contains(ID));
        assertTrue(jsonResponseStr.contains(NAME));
        assertTrue(jsonResponseStr.contains(SURNAME));
        assertTrue(jsonResponseStr.contains(POSITION));
        assertTrue(jsonResponseStr.contains(BIRTH_DATE));
        assertTrue(jsonResponseStr.contains(EMAIL));
        assertTrue(jsonResponseStr.contains(COMPANY));
        assertTrue(jsonResponseStr.contains(ROLE));
        assertFalse(jsonResponseStr.contains(PASSWORD));
    }

    @Test
    void getEmployeesExecutorResponseIsEmployeesJSON() throws ServletException, IOException {

        String jsonReqBody = "";
        MockHttpServletRequest request = getFormedMockRequest(CommandName.HttpMethod.GET,
                LOCALHOST_8080+CommandName.URI.EMPLOYEES,
                jsonReqBody);
        MockHttpSession mockHttpSession = new MockHttpSession();
        int companyId = 1;
        mockHttpSession.setAttribute(LoginExecutor.COMPANY_ID, companyId);
        request.setSession(mockHttpSession);

        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request,response);

        assertEquals(HttpServletResponse.SC_OK,response.getStatus());
        assertTrue(response.getContentType().contains(APPLICATION_JSON));

        String jsonResponseStr = response.getContentAsString();

        List<EmployeeDTO> employeeDTOList =
                objectMapper.readValue(jsonResponseStr,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeDTO.class));

        assertTrue(employeeDTOList.stream().allMatch(e -> e.getCompany() == companyId));

        assertTrue(jsonResponseStr.contains(ID));
        assertTrue(jsonResponseStr.contains(NAME));
        assertTrue(jsonResponseStr.contains(SURNAME));
        assertTrue(jsonResponseStr.contains(POSITION));
        assertTrue(jsonResponseStr.contains(BIRTH_DATE));
        assertTrue(jsonResponseStr.contains(EMAIL));
        assertTrue(jsonResponseStr.contains(COMPANY));
        assertTrue(jsonResponseStr.contains(ROLE));
        assertFalse(jsonResponseStr.contains(PASSWORD));
    }

    @Test
    void getEmployeeWithIdExecutorResponseIsEmployeeJSON() throws ServletException, IOException {

        String jsonReqBody = "";
        int employeeId = 1;
        MockHttpServletRequest request = getFormedMockRequest(CommandName.HttpMethod.GET,
                LOCALHOST_8080+CommandName.URI.EMPLOYEE+ employeeId,
                jsonReqBody);

        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request,response);

        assertEquals(HttpServletResponse.SC_OK,response.getStatus());
        assertTrue(response.getContentType().contains(APPLICATION_JSON));

        String jsonResponseStr = response.getContentAsString();

        EmployeeDTO employeeDTO = objectMapper.readValue(jsonResponseStr,EmployeeDTO.class);
        assertEquals(employeeId,employeeDTO.getId());

        assertTrue(jsonResponseStr.contains(ID));
        assertTrue(jsonResponseStr.contains(NAME));
        assertTrue(jsonResponseStr.contains(SURNAME));
        assertTrue(jsonResponseStr.contains(POSITION));
        assertTrue(jsonResponseStr.contains(BIRTH_DATE));
        assertTrue(jsonResponseStr.contains(EMAIL));
        assertTrue(jsonResponseStr.contains(COMPANY));
        assertTrue(jsonResponseStr.contains(ROLE));
        assertFalse(jsonResponseStr.contains(PASSWORD));
    }

    @Test
    void addEmployeeExecutorResponseIsEmployeesJSON() throws ServletException, IOException {

        String jsonReqBody = "{\n" +
                "    \"name\": \"jsadssade\",\n" +
                "    \"surname\": \"jsonov\",\n" +
                "    \"position\": \"jsoner\",\n" +
                "    \"birthDate\": [\n" +
                "        2000,\n" +
                "        1,\n" +
                "        22\n" +
                "    ],\n" +
                "    \"email\": \"new employeeee\",\n" +
                "    \"password\": \"e\",\n" +
                "    \"company\": 2,\n" +
                "    \"role\": 1\n" +
                "}";

        MockHttpServletRequest request = getFormedMockRequest(CommandName.HttpMethod.POST,
                LOCALHOST_8080+CommandName.URI.EMPLOYEES,
                jsonReqBody);

        MockHttpSession mockHttpSession = new MockHttpSession();
        int companyId = 2;
        mockHttpSession.setAttribute(LoginExecutor.COMPANY_ID, companyId);
        request.setSession(mockHttpSession);

        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request,response);

        assertEquals(HttpServletResponse.SC_CREATED,response.getStatus());
        assertTrue(response.getContentType().contains(APPLICATION_JSON));

        String jsonResponseStr = response.getContentAsString();

        EmployeeDTO employeeDTO = objectMapper.readValue(jsonResponseStr,EmployeeDTO.class);
        assertNotNull(employeeDTO.getId());
        assertTrue(employeeDTO.getId() > 0);
        addedEmployeeId = employeeDTO.getId();

        assertTrue(jsonResponseStr.contains(ID));
        assertTrue(jsonResponseStr.contains(NAME));
        assertTrue(jsonResponseStr.contains(SURNAME));
        assertTrue(jsonResponseStr.contains(POSITION));
        assertTrue(jsonResponseStr.contains(BIRTH_DATE));
        assertTrue(jsonResponseStr.contains(EMAIL));
        assertTrue(jsonResponseStr.contains(COMPANY));
        assertTrue(jsonResponseStr.contains(ROLE));
        assertFalse(jsonResponseStr.contains(PASSWORD));
    }

    @Test
    void deleteEmployeeExecutorResponseIsNoContent() throws ServletException, IOException {

        addEmployeeExecutorResponseIsEmployeesJSON();

        String jsonReqBody = "";
        int employeeId = addedEmployeeId;

        MockHttpServletRequest request = getFormedMockRequest(CommandName.HttpMethod.DELETE,
                LOCALHOST_8080+CommandName.URI.EMPLOYEE+employeeId,
                jsonReqBody);

        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request,response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT,response.getStatus());

    }

    @Test
    void editEmployeeExecutorResponseIsEmployeesJSON() throws ServletException, IOException {

        addEmployeeExecutorResponseIsEmployeesJSON();

        String jsonReqBody = "{\n" +
                "    \"name\": \"jsadssade\",\n" +
                "    \"surname\": \"jsonov\",\n" +
                "    \"position\": \"jsoner\",\n" +
                "    \"birthDate\": [\n" +
                "        2000,\n" +
                "        1,\n" +
                "        22\n" +
                "    ],\n" +
                "    \"email\": \"new employeeee\",\n" +
                "    \"password\": \"e\",\n" +
                "    \"company\": 2,\n" +
                "    \"role\": 1\n" +
                "}";

        int employeeId = addedEmployeeId;
        MockHttpServletRequest request = getFormedMockRequest(CommandName.HttpMethod.PUT,
                LOCALHOST_8080+CommandName.URI.EMPLOYEE+employeeId,
                jsonReqBody);

        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request,response);

        assertEquals(HttpServletResponse.SC_OK,response.getStatus());
        assertTrue(response.getContentType().contains(APPLICATION_JSON));

        String jsonResponseStr = response.getContentAsString();

        EmployeeDTO employeeDTO = objectMapper.readValue(jsonResponseStr,EmployeeDTO.class);
        assertNotNull(employeeDTO.getId());
        assertTrue(employeeDTO.getId() > 0);
        addedEmployeeId = employeeDTO.getId();

        assertTrue(jsonResponseStr.contains(ID));
        assertTrue(jsonResponseStr.contains(NAME));
        assertTrue(jsonResponseStr.contains(SURNAME));
        assertTrue(jsonResponseStr.contains(POSITION));
        assertTrue(jsonResponseStr.contains(BIRTH_DATE));
        assertTrue(jsonResponseStr.contains(EMAIL));
        assertTrue(jsonResponseStr.contains(COMPANY));
        assertTrue(jsonResponseStr.contains(ROLE));
        assertFalse(jsonResponseStr.contains(PASSWORD));
    }

    @Test
    void logoutExecutorResponseIsNoContent() throws ServletException, IOException {

        String jsonReqBody = "";
        MockHttpServletRequest request = getFormedMockRequest(CommandName.HttpMethod.DELETE,
                LOCALHOST_8080+CommandName.URI.SESSION,
                jsonReqBody);

        MockHttpSession mockHttpSession = new MockHttpSession();
        request.setSession(mockHttpSession);

        MockHttpServletResponse response = new MockHttpServletResponse();

        dispatcherServlet.service(request,response);

        assertEquals(HttpServletResponse.SC_NO_CONTENT,response.getStatus());
    }


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
