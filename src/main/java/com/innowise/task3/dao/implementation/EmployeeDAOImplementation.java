package com.innowise.task3.dao.implementation;

import com.innowise.task3.dao.DAOException;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeDAOImplementation implements EmployeeDAO {

    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1/employee-data-accounting?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "BebraBebra";
    public static final String E_ID = "e_id";
    public static final String E_NAME = "e_name";
    public static final String E_SURNAME = "e_surname";
    public static final String E_BIRTH_DATE = "e_birth_date";
    public static final String E_POSITION = "e_position";
    public static final String E_EMAIL = "e_email";
    public static final String E_ROLE_ID = "e_role_id";
    public static final String E_COMPANY_ID = "e_company_id";
    public static final String E_ROLE_NAME = "e_role_name";
    public static final String E_COMPANY_NAME = "e_company_name";

    public static final String E_PASSWORD = "e_password";

    @Override
    public List<Employee> getAllEmployees() throws DAOException {
        final String SELECT_ALL_FROM_EMPLOYEE =
                "SELECT employee.*,role.r_name AS e_role_name ,company.c_name AS e_company_name FROM employee " +
                        "JOIN company ON e_company_id = c_id  " +
                        "JOIN role ON e_role_id = r_id; ";

        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_ALL_FROM_EMPLOYEE);
             ResultSet resultSet = selectStatement.executeQuery()) {

            if (!resultSet.isBeforeFirst()) {
                throw new DAOException();
            }

            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                employees.add(employeeResultSetToEmployee(resultSet));
            }
            return employees;

        } catch (SQLException e) {

            throw new DAOException(e);
        }
    }

    @Override
    public Employee getEmployeeWithId(int id) throws DAOException {
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
                    throw new DAOException();
                }

                resultSet.next();
                Employee employee = employeeResultSetToEmployee(resultSet);
                return employee;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Employee editEmployee(Employee employee) throws DAOException {

        final String UPDATE_EMPLOYEE = "UPDATE employee " +
                "SET e_name = CASE WHEN ? IS NULL THEN e_name ELSE ? END," +
                "e_surname = CASE WHEN ? IS NULL THEN e_surname ELSE ? END," +
                "e_position = CASE WHEN ? IS NULL THEN e_position ELSE ? END," +
                "e_birth_date = CASE WHEN ? IS NULL THEN e_birth_date ELSE ? END," +
                "e_role_id = CASE WHEN ? IS NULL THEN e_role_id ELSE ? END," +
                "e_company_id = CASE WHEN ? IS NULL THEN e_company_id ELSE ? END," +
                "e_email = CASE WHEN ? IS NULL THEN e_email ELSE ? END," +
                "e_password = CASE WHEN ? IS NULL THEN e_password ELSE ? END " +
                "WHERE e_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            connection.setAutoCommit(false);
            try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE_EMPLOYEE)) {

                int statementPlaceholderCount = (int) UPDATE_EMPLOYEE.chars().filter(ch -> ch == '?').count();
                fillUpdateStatement(employee, statementPlaceholderCount, updateStatement);

                int affectedRows = updateStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new DAOException("Updating user failed, no rows affected.");
                }

                return employee;
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }



    @Override
    public Employee addEmployee(Employee employee) throws DAOException  {
        final String INSERT_EMPLOYEE =
                "INSERT INTO employee (e_name,e_surname,e_position,e_birth_date,e_role_id,e_company_id,e_email,e_password) " +
                        "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            connection.setAutoCommit(false);
            try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)) {

                insertStatement.setString(1, employee.getName());
                insertStatement.setString(2, employee.getSurname());
                insertStatement.setString(3, employee.getPosition());
                insertStatement.setDate(4, Date.valueOf(employee.getBirthDate()));
                insertStatement.setInt(5, employee.getRole().getId());
                insertStatement.setInt(6, employee.getCompany().getId());
                insertStatement.setString(7, employee.getEmail());

                String password = employee.getPassword();
                String salt = BCrypt.gensalt();
                String hashedPassword = BCrypt.hashpw(password, salt);
                insertStatement.setString(8, hashedPassword);

                int affectedRows = insertStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new DAOException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getInt(1));
                    } else {
                        throw new DAOException("Creating user failed, no ID obtained.");
                    }
                }

                return employee;
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException(e);
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void deleteEmployee(int id) throws DAOException  {
        final String DELETE_EMPLOYEE_WITH_ID = "DELETE FROM employee WHERE e_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            connection.setAutoCommit(false);
            try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_EMPLOYEE_WITH_ID)) {

                deleteStatement.setInt(1, id);

                int affectedRows = deleteStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new DAOException("Deleting user failed, no rows affected.");
                }

            } catch (SQLException e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }


    }

    @Override
    public Employee login(String email, String password) throws DAOException {
        final String Q_GET_USER_BY_EMAIL = "SELECT * FROM employee WHERE e_email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
             PreparedStatement getUserStatement = connection.prepareStatement(Q_GET_USER_BY_EMAIL)) {

            getUserStatement.setString(1, email);

            try(ResultSet rs = getUserStatement.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    throw new DAOException("no such email in db"); // no such login in db
                }
                rs.next();
                String hashedPassword = rs.getString(E_PASSWORD);
                if (!BCrypt.checkpw(password,hashedPassword)) {
                    return null;
                }
                Employee employee = employeeResultSetToEmployee(rs);
                return employee;


            }


        } catch (SQLException | IllegalArgumentException e) {
            throw new DAOException(e);
        }
    }

    private Employee employeeResultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();

        employee.setId(resultSet.getInt(E_ID));

        employee.setName(resultSet.getString(E_NAME));
        employee.setSurname(resultSet.getString(E_SURNAME));
        employee.setBirthDate(resultSet.getDate(E_BIRTH_DATE).toLocalDate());

        employee.setPosition(resultSet.getString(E_POSITION));

        employee.setEmail(resultSet.getString(E_EMAIL));

        Role role = new Role(resultSet.getInt(E_ROLE_ID), null);
        employee.setRole(role);
        Company company = new Company(resultSet.getInt(E_COMPANY_ID), null);
        employee.setCompany(company);
        return employee;
    }

    private void fillUpdateStatement(Employee employee, int statementPlaceholderCount, PreparedStatement updateStatement) throws SQLException {
        String password = employee.getPassword();
        if (password != null) {
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(password,salt);
            employee.setPassword(hashedPassword);
        }

        List<Object> employeeFields = Arrays.asList(
                employee.getName(),
                employee.getSurname(),
                employee.getPosition(),
                employee.getBirthDate(),
                employee.getRole() != null && employee.getRole().getId() > 0
                        ? employee.getRole().getId()
                        : null,
                employee.getCompany() != null && employee.getCompany().getId() > 0
                        ? employee.getCompany().getId()
                        : null,
                employee.getEmail(),
                employee.getPassword()
        );

        // 1 2  3 4  5 6  7 8

        int statementIndex = 1;
        while(statementIndex < statementPlaceholderCount) {
            Object employeeField = employeeFields.get(statementIndex / 2);
            for (int i = 0; i < 2; i++) {

                if(employeeField instanceof String) {
                    updateStatement.setString(statementIndex++, (String) employeeField);
                } else if (employeeField instanceof Integer) {
                    updateStatement.setInt(statementIndex++, (Integer) employeeField);
                } else if(employeeField instanceof LocalDate) {
                    updateStatement.setDate(statementIndex++, Date.valueOf((LocalDate) employeeField));
                } else {
                    updateStatement.setObject(statementIndex++,employeeField);
                }
            }
        }

        updateStatement.setInt(statementPlaceholderCount, employee.getId());
    }
}
