package com.capg.restapi;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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

	@Test
	public void givenEmployee_WhenAddedToJsonFile_ShouldReturnAddedCount() {
		Employee employee = new Employee(4, "M", "Koushik", 600000);
		employeePayrollService.addEmployee(employee);
		addEmployeeToJsonServer(employee);
		int count = employeePayrollService.getCount();
		Assert.assertEquals(4,count);
	}

	private Response addEmployeeToJsonServer(Employee employee) {
		String employeeJson = new Gson().toJson(employee);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		requestSpecification.body(employeeJson);
		return requestSpecification.post("/employees");
	}

	private List<Employee> getEmployeeList() {
		Response response = RestAssured.get("/employees");
		System.out.println(response.getContentType());
		Employee[] employees = new Gson().fromJson(response.asString(), Employee[].class);
		return Arrays.asList(employees);
	}
}
