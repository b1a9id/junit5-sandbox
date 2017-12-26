package com.example.core.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

	private String firstName;

	private String lastName;

	private int age;

	private Gender gender;
}
