package guru.springframework.dependency_injection.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.dependency_injection.services.ConstructorGreetingService;

class PropertyInjectedControllerTest
{
	PropertyInjectedController controller;
	
	@BeforeEach
	void setUp()
	{
		controller = new PropertyInjectedController();
		
		controller.greetingService = new ConstructorGreetingService();
	}
	
	@Test
	void getGreeting()
	{
		System.out.println(controller.getGreeting());
	}
}