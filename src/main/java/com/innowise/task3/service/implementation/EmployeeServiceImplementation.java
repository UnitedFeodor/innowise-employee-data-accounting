package com.innowise.task3.service.implementation;

import com.innowise.task3.dao.DAOException;
import com.innowise.task3.dao.DAOProvider;
import com.innowise.task3.dao.EmployeeDAO;
import com.innowise.task3.dto.*;
import com.innowise.task3.entity.Employee;
import com.innowise.task3.mapper.EmployeeMapper;
import com.innowise.task3.service.EmployeeService;
import com.innowise.task3.service.ServiceException;

import java.util.List;

public class EmployeeServiceImplementation implements EmployeeService {
    // TODO add validation

    EmployeeDAO employeeDAO = DAOProvider.getInstance().getEmployeeDAO();

    EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;

    @Override
    public List<EmployeeDTO> getAllEmployees() throws ServiceException {
        try {
            return employeeMapper.employeeListToEmployeeDTOList(employeeDAO.getAllEmployees());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeWithId(int id) throws ServiceException {
        try {
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
