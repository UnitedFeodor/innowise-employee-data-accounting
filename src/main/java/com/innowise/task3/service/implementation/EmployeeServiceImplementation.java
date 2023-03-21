package com.innowise.task3.service.implementation;

import com.innowise.task3.dao.DAOException;
import com.innowise.task3.dao.DAOProvider;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.entity.dto.AddEmployeeDTO;
import com.innowise.task3.entity.dto.EditEmployeeDTO;
import com.innowise.task3.entity.dto.EmployeeDTO;
import com.innowise.task3.entity.dto.LoginDTO;
import com.innowise.task3.entity.dto.mapper.EmployeeMapper;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServiceImplementation implements EmployeeService {

    EmployeeDAO employeeDAO = DAOProvider.getInstance().getEmployeeDAO();

    EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    public List<EmployeeDTO> getAllEmployees(int companyId) throws ServiceException {
        try {
            if (companyId < 0) {
                throw new ServiceException("companyId is < 0");
            }

            List<Employee> allEmployeesFromCompany =
                    employeeDAO.getAllEmployees()
                            .stream()
                            .filter(e -> e.getCompany().getId() == companyId)
                            .collect(Collectors.toList());

            return employeeMapper.employeeListToEmployeeDTOList(allEmployeesFromCompany);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeWithId(int id) throws ServiceException {
        try {
            if (id < 0) {
                throw new ServiceException("id is < 0");
            }

            return employeeMapper.employeeToEmployeeDTO(employeeDAO.getEmployeeWithId(id));
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmployeeDTO editEmployee(EditEmployeeDTO editEmployeeDTO) throws ServiceException{
        try {
            Employee employee = employeeDAO.editEmployee(employeeMapper.editEmployeeDTOToEmployee(editEmployeeDTO));
            return employeeMapper.employeeToEmployeeDTO(employee);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public EmployeeDTO addEmployee(AddEmployeeDTO addEmployeeDTO) throws ServiceException {
        try {
            Employee employee = employeeDAO.addEmployee(employeeMapper.addEmployeeDTOToEmployee(addEmployeeDTO));
            return employeeMapper.employeeToEmployeeDTO(employee);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteEmployee(int id) throws ServiceException {
        try {
            if (id < 0) {
                throw new ServiceException("id is < 0");
            }

            employeeDAO.deleteEmployee(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmployeeDTO login(LoginDTO loginDTO) throws ServiceException {
        try {
            Employee employee = employeeDAO.login(loginDTO.getEmail(),loginDTO.getPassword());
            return employee == null ? null : employeeMapper.employeeToEmployeeDTO(employee);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
