import com.innowise.task3.dao.DAOException;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.dao.implementation.EmployeeDAOImplementation;
import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDAOTest {
    EmployeeDAO employeeDAO = new EmployeeDAOImplementation();

    @Test
    void getAllEmployees() throws DAOException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        assertNotNull(employees);
    }

    @Test
    void getEmployeeWithId() throws DAOException {
        int employeeId = 1;

        Employee employee = employeeDAO.getEmployeeWithId(employeeId);

        assertNotNull(employee);
        assertEquals(employeeId,employee.getId());
    }

    @Test
    void addEmployee() throws DAOException {
        Employee employee = new Employee();
        String name = "a";
        employee.setName(name);
        String surname = "b";
        employee.setSurname(surname);

        String position = "c";
        employee.setPosition(position);

        employee.setRole(new Role(1,""));
        employee.setCompany(new Company(2,""));

        LocalDate birthDate = LocalDate.of(2001, 9, 11);
        employee.setBirthDate(birthDate);

        String email = "d";
        employee.setEmail(email);
        String password = "e";
        employee.setPassword(password);

        Employee insertedEmployee = employeeDAO.addEmployee(employee);

        assertNotNull(insertedEmployee);
        assertNotNull(insertedEmployee.getId());

        assertEquals(insertedEmployee.getName(),name);
        assertEquals(insertedEmployee.getSurname(),surname);
        assertEquals(insertedEmployee.getPosition(),position);

        assertEquals(insertedEmployee.getEmail(),email);
        assertEquals(insertedEmployee.getPassword(),password);
    }

    @Test
    void editEmployeeAllFields() throws DAOException {
        Employee employee = new Employee();

        int employeeId = 3;
        employee.setId(employeeId);

        String name = "asdasdsa";
        employee.setName(name);
        String surname = "bqwewqe";
        employee.setSurname(surname);

        String position = "casdasd";
        employee.setPosition(position);

        employee.setRole(new Role(1,""));
        employee.setCompany(new Company(2,""));

        LocalDate birthDate = LocalDate.of(1991, 1, 21);
        employee.setBirthDate(birthDate);

        String email = "dasdasddas";
        employee.setEmail(email);
        String password = "password";
        employee.setPassword(password);

        Employee editedEmployee = employeeDAO.editEmployee(employee);

        assertNotNull(editedEmployee);
    }

    @Test
    void editEmployeeNotAllFields() throws DAOException {
        Employee employee = new Employee();

        int employeeId = 3;
        employee.setId(employeeId);

        String password = "password";
        employee.setPassword(password);

        Employee editedEmployee = employeeDAO.editEmployee(employee);

        assertNotNull(editedEmployee);
    }

    @Test
    void editEmployeeNoFields() throws DAOException {
        Employee employee = new Employee();

        int employeeId = 9;
        employee.setId(employeeId);

        Employee editedEmployee = employeeDAO.editEmployee(employee);

        assertNotNull(editedEmployee);
    }

    @Test
    void deleteEmployee() throws DAOException {
        // get last inserted
        int id = employeeDAO.getAllEmployees().stream().max(Comparator.comparing(Employee::getId)).map(e -> e.getId()).orElseThrow();

        assertDoesNotThrow(() -> employeeDAO.deleteEmployee(id));

    }

    @Test
    void signIn() throws DAOException {
        String password = "password";
        String email = "unabomber@gmail.com";

        assertNotNull(employeeDAO.login(email, password));
    }

}
