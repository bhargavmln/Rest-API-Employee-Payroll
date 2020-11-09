package com.capg.restapi;

import java.time.LocalDate;

public class Employee {

	private int id;
	private String gender;
	private String name;
	private double salary;
	private LocalDate date;

	public Employee(int id, String gender, String name, double salary) {
		this.name = name;
		this.gender = gender;
		this.id = id;
		this.salary = salary;
		this.date = LocalDate.now();
	}
}
