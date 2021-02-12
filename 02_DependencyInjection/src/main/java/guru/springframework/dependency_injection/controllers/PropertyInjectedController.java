package guru.springframework.dependency_injection.controllers;

import guru.springframework.dependency_injection.services.GreetingService;

public class PropertyInjectedController
{
	public GreetingService greetingService;
	
	public String getGreeting()
	{
		return greetingService.sayGreeting();
	}
}
