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

	public List<Employee> getEmployeeFromList() {
		return employees;
	}
	
	public void addEmployeeToList(List<Employee> employees) {
		employees.forEach(employee -> this.employees.add(employee));
	}

	public int getCount() {
		return employees.size();
	}

	public void updateEmployeesalary(String name, double salary) {
		Employee employee = this.employees.stream().filter(emp -> emp.getName().equals(name))
										  .findAny()
										  .orElse(null);
		employee.setSalary(salary);
	}

	public Employee getEmployee(String name) {
		return this.employees.stream().filter(emp -> emp.getName().equals(name))
								      .findAny()
								      .orElse(null);
	}
}
