import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.task3.controller.json.mapper.ObjectMapperProvider;
import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.Role;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceProvider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CommandExecutorTest {
    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();
    private final EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();

    // TODO add executor tests, service tests, filter tests, security test
    // TODO debug to be removed
    @Test
    void json() {
        Employee employee = new Employee();
        employee.setName("a");
        employee.setSurname("b");
        employee.setPosition("c");
        employee.setRole(new Role(1,""));
        employee.setCompany(new Company(2,""));
        employee.setBirthDate(LocalDate.of(2001,9,11));
        employee.setEmail("d");
        employee.setPassword("e");
        try {
            String employeeJsonString = objectMapper.writeValueAsString(employee);
            System.out.println(employeeJsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
