package com.innowise.task3.dto;

import com.innowise.task3.entity.Company;
import com.innowise.task3.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    int company;
    int role;
}
