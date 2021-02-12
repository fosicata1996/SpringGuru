package guru.springframework.dependency_injection.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.dependency_injection.services.ConstructorGreetingService;

class SetterInjectedControllerTest
{
	SetterInjectedController controller;
	
	@BeforeEach
	void setUp()
	{
		controller = new SetterInjectedController();
		controller.setGreetingService(new ConstructorGreetingService());
	}
	
	@Test
	void getGreeting()
	{
		System.out.println(controller.getGreeting());
	}
}