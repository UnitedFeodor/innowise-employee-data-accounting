import com.innowise.task3.entity.dto.AddEmployeeDTO;
import com.innowise.task3.entity.dto.EditEmployeeDTO;
import com.innowise.task3.entity.dto.EmployeeDTO;
import com.innowise.task3.entity.dto.LoginDTO;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;
import com.innowise.task3.service.ServiceProvider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {

    EmployeeService employeeService = ServiceProvider.getInstance().getEmployeeService();
    @Test
    public void getAllEmployees() throws ServiceException {
        int companyId = 1;
        List<EmployeeDTO> allEmployeesFromCompany = employeeService.getAllEmployees(companyId);

        assertNotNull(allEmployeesFromCompany);
        assertTrue(allEmployeesFromCompany.stream().allMatch(e -> e.getCompany() == companyId));

    }

    @Test
    public void getEmployeeWithId() throws ServiceException {
        int id = 1;
        EmployeeDTO employeeDTO = employeeService.getEmployeeWithId(id);

        assertNotNull(employeeDTO);
        assertTrue(Stream.of(employeeDTO.getId(),
                        employeeDTO.getName(),
                        employeeDTO.getSurname(),
                        employeeDTO.getPosition(),
                        employeeDTO.getRole(),
                        employeeDTO.getCompany(),
                        employeeDTO.getEmail(),
                        employeeDTO.getBirthDate())
                .allMatch(Objects::nonNull));

    }

    @Test
    public void editEmployee() throws ServiceException {
        EditEmployeeDTO editEmployeeDTO = new EditEmployeeDTO();

        int employeeId = 3;
        editEmployeeDTO.setId(employeeId);

        String name = "asdasdsa";
        editEmployeeDTO.setName(name);
        String surname = "bqwewqe";
        editEmployeeDTO.setSurname(surname);

        String position = "casdasd";
        editEmployeeDTO.setPosition(position);

        editEmployeeDTO.setRole(1);
        editEmployeeDTO.setCompany(1);

        LocalDate birthDate = LocalDate.of(1991, 1, 21);
        editEmployeeDTO.setBirthDate(birthDate);

        String email = "dasdasddas";
        editEmployeeDTO.setEmail(email);
        String password = "password";
        editEmployeeDTO.setPassword(password);

        EmployeeDTO editedEmployeeDTO = employeeService.editEmployee(editEmployeeDTO);

        EmployeeDTO fullEditedEmployeeInfo = employeeService.getEmployeeWithId(employeeId);
        assertNotNull(editedEmployeeDTO);
        assertTrue(Stream.of(fullEditedEmployeeInfo.getId(),
                        fullEditedEmployeeInfo.getName(),
                        fullEditedEmployeeInfo.getSurname(),
                        fullEditedEmployeeInfo.getPosition(),
                        fullEditedEmployeeInfo.getRole(),
                        fullEditedEmployeeInfo.getCompany(),
                        fullEditedEmployeeInfo.getEmail(),
                        fullEditedEmployeeInfo.getBirthDate())
                .allMatch(Objects::nonNull));
    }



    @Test
    public void addEmployee() throws ServiceException {
        AddEmployeeDTO employee = new AddEmployeeDTO();
        String name = "a";
        employee.setName(name);
        String surname = "b";
        employee.setSurname(surname);

        String position = "c";
        employee.setPosition(position);

        employee.setRole(1);
        employee.setCompany(2);

        LocalDate birthDate = LocalDate.of(2001, 9, 11);
        employee.setBirthDate(birthDate);

        String email = "d";
        employee.setEmail(email);
        String password = "e";
        employee.setPassword(password);

        EmployeeDTO insertedEmployee = employeeService.addEmployee(employee);

        assertNotNull(insertedEmployee);
        assertNotNull(insertedEmployee.getId());

        assertEquals(insertedEmployee.getName(),name);
        assertEquals(insertedEmployee.getSurname(),surname);
        assertEquals(insertedEmployee.getPosition(),position);

        assertEquals(insertedEmployee.getEmail(),email);
    }

    @Test
    public void deleteEmployee() throws ServiceException {

        int id = employeeService.getAllEmployees(2).stream().max(Comparator.comparing(EmployeeDTO::getId)).map(e -> e.getId()).orElseThrow();
        assertDoesNotThrow(() -> employeeService.deleteEmployee(id));

    }

    @Test
    public void login() throws ServiceException {
        LoginDTO loginDTO = new LoginDTO();

        String password = "password";
        loginDTO.setPassword(password);

        String email = "unabomber@gmail.com";
        loginDTO.setEmail(email);

        EmployeeDTO employeeDTO = employeeService.login(loginDTO);

        assertNotNull(employeeDTO);
        assertEquals(email,employeeDTO.getEmail());
    }
}
