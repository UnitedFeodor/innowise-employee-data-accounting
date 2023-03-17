package com.innowise.task3.dao.implementation;

import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImplementation implements EmployeeDAO {

    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1/employee-data-accounting?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "BebraBebra";
    // TODO add custom exceptions

    @Override
    public List<Employee> getAllEmployees() {
        final String SELECT_ALL_FROM_EMPLOYEE =
                "SELECT employee.*,role.r_name AS e_role_name ,company.c_name AS e_company_name FROM employee " +
                        "JOIN company ON e_company_id = c_id  " +
                        "JOIN role ON e_role_id = r_id; ";
//                "SELECT e_id,e_name,e_surname,e_position,e_birth_date,e_email,e_password,company.c_name,role.r_name " +
//                "FROM employee INNER JOIN company ON e_company_id = c_id JOIN role ON e_role_id = r_id; ";
        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_ALL_FROM_EMPLOYEE);
             ResultSet resultSet = selectStatement.executeQuery()) {

            if (!resultSet.isBeforeFirst()) {
                throw new SQLException();
            }

            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                employees.add(resultSetToEmployee(resultSet));
            }
            return employees;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getEmployeeWithId(int id) {
        final String SELECT_EMPLOYEE_WITH_ID =
                "SELECT employee.*,role.r_name AS e_role_name ,company.c_name AS e_company_name FROM employee " +
                        "JOIN company ON e_company_id = c_id  " +
                        "JOIN role ON e_role_id = r_id " +
                        "WHERE e_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_EMPLOYEE_WITH_ID)){

            selectStatement.setInt(1,id);

            try (ResultSet resultSet = selectStatement.executeQuery()) {

                if (!resultSet.isBeforeFirst()) {
                    throw new SQLException();
                }

                resultSet.next();
                Employee employee = resultSetToEmployee(resultSet);
                return employee;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee resultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

        employee.setId(resultSet.getInt("e_id"));

        employee.setName(resultSet.getString("e_name"));
        employee.setSurname(resultSet.getString("e_surname"));
        employee.setBirthDate(resultSet.getDate("e_birth_date").toLocalDate());

        employee.setPosition(resultSet.getString("e_position"));

        employee.setEmail(resultSet.getString("e_email"));

        Role role = new Role(resultSet.getInt("e_role_id"), resultSet.getString("e_role_name"));
        employee.setRole(role);
        Company company = new Company(resultSet.getInt("e_company_id"), resultSet.getString("e_company_name"));
        employee.setCompany(company);
        return employee;
    }

    @Override
    public Employee editEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        //INSERT INTO employee (e_name,e_surname,e_position,e_birth_date,e_role_id,e_company_id,e_email,e_password) VALUES ('Jack','Jackson','Manager',2002-05-20,1,2,'jack@yahoo.com','test')
        final String INSERT_EMPLOYEE =
                "INSERT INTO employee (e_name,e_surname,e_position,e_birth_date,e_role_id,e_company_id,e_email,e_password) " +
                        "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_EMPLOYEE,Statement.RETURN_GENERATED_KEYS)){

            insertStatement.setString(1,employee.getName());
            insertStatement.setString(2,employee.getSurname());
            insertStatement.setString(3,employee.getPosition());
            insertStatement.setDate(4,Date.valueOf(employee.getBirthDate()));
            insertStatement.setInt(5,employee.getRole().getId());
            insertStatement.setInt(6,employee.getCompany().getId());
            insertStatement.setString(7,employee.getEmail());

            String password = employee.getPassword();
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(password,salt);
            insertStatement.setString(8,hashedPassword);

            int affectedRows = insertStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return employee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteEmployee(int id) {

    }

    @Override
    public boolean login(String login, String password) {
        return false;
    }

    @Override
    public boolean register(Employee employee) {
        return false;
    }
}
