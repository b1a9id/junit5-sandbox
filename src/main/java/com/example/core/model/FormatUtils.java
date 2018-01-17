package com.example.core.model;

import java.util.Objects;

public class FormatUtils {

	public static String person(Person person) {
		if (Objects.isNull(person)) {
			return "";
		}

		String result = "Name：{name}, Age：{age}, Gender：{gender}";
		return result.replace("{name}", person.getFullName())
				.replace("{age}", String.valueOf(person.getAge()))
				.replace("{gender}", person.getGender().toString());
	}
}
