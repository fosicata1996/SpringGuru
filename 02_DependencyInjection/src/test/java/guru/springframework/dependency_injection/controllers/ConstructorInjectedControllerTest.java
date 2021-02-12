package guru.springframework.dependency_injection.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.dependency_injection.services.GreetingServiceImpl;

class ConstructorInjectedControllerTest
{
	ConstructorInjectedController controller;
	
	@BeforeEach
	void setUp()
	{
		controller = new ConstructorInjectedController(new GreetingServiceImpl());
	}
	
	@Test
	void getGreeting()
	{
		System.out.println(controller.getGreeting());
	}
}