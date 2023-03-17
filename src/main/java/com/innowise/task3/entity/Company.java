package com.innowise.task3.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @EqualsAndHashCode.Exclude
    int id;
    String name;
}
