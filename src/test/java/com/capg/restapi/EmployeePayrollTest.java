package com.capg.restapi;

import org.junit.Before;

import io.restassured.RestAssured;


public class EmployeePayrollTest 
{

	@Before
	   public void setUp() {
	       RestAssured.baseURI = "http://localhost";
	       RestAssured.port = 3000;
	   }
}
