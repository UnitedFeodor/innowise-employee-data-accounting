import com.innowise.task3.dao.DAOProvider;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.dao.implementation.EmployeeDAOImplementation;
import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDAOTest {
    EmployeeDAO employeeDAO = new EmployeeDAOImplementation();

    @Test
    void getAllEmployees() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        System.out.println(employees);
    }

    @Test
    void getEmployeeWithId() {
        Employee employee = employeeDAO.getEmployeeWithId(1);
        System.out.println(employee);
    }

    @Test
    void addEmployee() {
        Employee employee = new Employee();
        employee.setName("a");
        employee.setSurname("b");
        employee.setPosition("c");
        employee.setRole(new Role(1,""));
        employee.setCompany(new Company(2,""));
        employee.setBirthDate(LocalDate.of(2001,9,11));
        employee.setEmail("d");
        employee.setPassword("e");
        Employee insertedEmployee = employeeDAO.addEmployee(employee);

        System.out.println(insertedEmployee);

    }

    @Test
    void editEmployeeAllFields() {
        Employee employee = new Employee();
        employee.setId(7);
        employee.setName("aasdas");
        employee.setSurname("bsadasd");
        employee.setPosition("cgsdffsd");
        employee.setRole(new Role(2,""));
        employee.setCompany(new Company(1,""));
        employee.setBirthDate(LocalDate.of(1991,2,22));
        employee.setEmail("dasdas@gmail.com");
        employee.setPassword("eeeeeeeeeb");
        Employee editedEmployee = employeeDAO.editEmployee(employee);

        System.out.println(editedEmployee);
    }

    @Test
    void editEmployeeNotAllFields() {
        Employee employee = new Employee();
        employee.setId(9);
        employee.setName("aasdas");
        employee.setPosition("cgsdffsd");
        employee.setCompany(new Company(1,""));
        employee.setBirthDate(LocalDate.of(1992,12,7));
        Employee editedEmployee = employeeDAO.editEmployee(employee);

        System.out.println(editedEmployee);
    }

    @Test
    void editEmployeeNoFields() {
        Employee employee = new Employee();
        employee.setId(9);
        Employee editedEmployee = employeeDAO.editEmployee(employee);

        System.out.println(editedEmployee);
    }

    @Test
    void deleteEmployee(){
        int id = 10;
        employeeDAO.deleteEmployee(id);
    }

}