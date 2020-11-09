package com.capg.restapi;

import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {

	private List<Employee> employees;

	public EmployeePayrollService(List<Employee> employees) {
		this.employees = new ArrayList<Employee>(employees);
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	public List<Employee> getEmployeeList() {
		return employees;
	}

	public int getCount() {
		return employees.size();
	}
	
}
