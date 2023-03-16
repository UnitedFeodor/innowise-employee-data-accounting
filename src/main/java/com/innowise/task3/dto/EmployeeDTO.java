package com.innowise.task3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    int id;
    String name;
    String surname;
    String position;
    LocalDate birthDate;
    String email;
    CompanyDTO company;
    RoleDTO role;
}
