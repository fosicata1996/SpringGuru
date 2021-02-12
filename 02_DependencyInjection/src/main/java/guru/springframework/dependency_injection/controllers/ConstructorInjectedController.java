package guru.springframework.dependency_injection.controllers;

import guru.springframework.dependency_injection.services.GreetingService;

public class ConstructorInjectedController
{
	private final GreetingService greetingService;
	
	public ConstructorInjectedController(GreetingService greetingService)
	{
		this.greetingService = greetingService;
	}
	
	public String getGreeting()
	{
		return greetingService.sayGreeting();
	}
}
