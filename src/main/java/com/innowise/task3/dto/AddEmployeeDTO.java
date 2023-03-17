package com.innowise.task3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AddEmployeeDTO {
    String name;
    String surname;
    String position;
    LocalDate birthDate;
    String email;
    String password;
    Integer company;
    Integer role;

}
