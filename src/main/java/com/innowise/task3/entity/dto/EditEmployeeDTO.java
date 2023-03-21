package com.innowise.task3.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class EditEmployeeDTO {

    Integer id;
    String name;
    String surname;
    String position;
    LocalDate birthDate;
    String email;
    String password;
    Integer company;
    Integer role;
}
