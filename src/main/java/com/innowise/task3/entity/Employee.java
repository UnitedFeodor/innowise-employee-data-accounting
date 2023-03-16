package com.innowise.task3.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    int id;
    String name;
    String surname;
    String position;
    LocalDate birthDate;
    String email;
    String password;
    Company company;
    Role role;


}
