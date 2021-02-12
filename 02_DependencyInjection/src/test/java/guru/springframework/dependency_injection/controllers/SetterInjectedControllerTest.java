package guru.springframework.dependency_injection.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.dependency_injection.services.GreetingServiceImpl;

class SetterInjectedControllerTest
{
	SetterInjectedController controller;
	
	@BeforeEach
	void setUp()
	{
		controller = new SetterInjectedController();
		controller.setGreetingService(new GreetingServiceImpl());
	}
	
	@Test
	void getGreeting()
	{
		System.out.println(controller.getGreeting());
	}
}