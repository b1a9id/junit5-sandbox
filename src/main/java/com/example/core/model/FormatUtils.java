package com.example.core.model;

import java.util.Objects;

public class FormatUtils {

	public static String person(Person person) {
		if (Objects.isNull(person)) {
			return "";
		}

		String result = "名前：{name}, 年齢：{age}, 性別：{gender}";
		return result.replace("{name}", person.getFullName())
				.replace("{age}", String.valueOf(person.getAge()))
				.replace("{gender}", person.getGender().toString());
	}
}
