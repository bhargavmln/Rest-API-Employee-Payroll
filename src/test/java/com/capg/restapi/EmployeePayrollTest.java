package com.capg.restapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EmployeePayrollTest {
	EmployeePayrollService employeePayrollService;

	@Before
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
		employeePayrollService = new EmployeePayrollService(getEmployeeList());
	}

	@Ignore
	@Test
	public void givenEmployee_WhenAddedTo_ShouldReturnAddedCount() {
		Employee employee = new Employee(4, "M", "Koushik", 600000);
		employeePayrollService.addEmployee(employee);
		addEmployeeToJsonServer(employee);
		int count = employeePayrollService.getCount();
		Assert.assertEquals(4, count);
	}

	@Ignore
	@Test
	public void givenEmployeeList_WhenAdded_ShouldReturnAddedCount() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(5,"M","Sundar",400000));
		employees.add(new Employee(6,"M","Anil",450000));
		employees.add(new Employee(7,"M","Obama",600000));
		employeePayrollService.addEmployeeToList(employees);
		addMultipleEmployeeUsingThreads(employees);
		Assert.assertEquals(7, getEmployeeListFromJsonServer().size());
	}
	
	@Test
	public void givenEmployeeShouldGetUpdatedInTheJsonServer() {
		employeePayrollService.updateEmployeesalary("Jeff Bezos", 300000);
		Employee employee = employeePayrollService.getEmployee("Jeff Bezos");
		String empJson = new Gson().toJson(employee);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		Response response = request.put("/employees/" + employee.getId());
		Assert.assertEquals(200, response.getStatusCode());
	}

	private Response addEmployeeToJsonServer(Employee employee) {
		String employeeJson = new Gson().toJson(employee);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(employeeJson);
		return requestSpecification.post("/employees");
	}

	private List<Employee> getEmployeeListFromJsonServer() {
		Response response = RestAssured.get("/employees");
		Employee[] employees = new Gson().fromJson(response.asString(), Employee[].class);
		return Arrays.asList(employees);
	}

	private void addMultipleEmployeeUsingThreads(List<Employee> employees) {
		Map<Integer, Boolean> addEmployeeStatus = new HashMap<>();
		for (Employee employee : employees) {
			addEmployeeStatus.put(employee.getId(), false);
			Runnable task = () -> {
				System.out.println("Employee Being Added : " + Thread.currentThread().getName());
				Response response = addEmployeeToJsonServer(employee);
				if (response.getStatusCode() == 201)
					System.out.println("Employee Added : " + Thread.currentThread().getName());
				addEmployeeStatus.put(employee.getId(), true);
			};
			Thread thread = new Thread(task, employee.getName());
			thread.setPriority(6);
			thread.start();
		}
		while (addEmployeeStatus.containsValue(false)) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private List<Employee> getEmployeeList() {
		Response response = RestAssured.get("/employees");
		System.out.println(response.getContentType());
		Employee[] employees = new Gson().fromJson(response.asString(), Employee[].class);
		return Arrays.asList(employees);
	}
}
