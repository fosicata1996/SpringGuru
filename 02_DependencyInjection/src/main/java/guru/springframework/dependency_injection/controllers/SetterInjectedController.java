package guru.springframework.dependency_injection.controllers;

import guru.springframework.dependency_injection.services.GreetingService;

public class SetterInjectedController
{
	private GreetingService greetingService;
	
	public void setGreetingService(GreetingService greetingService)
	{
		this.greetingService = greetingService;
	}
	
	public String getGreeting()
	{
		return greetingService.sayGreeting();
	}
}
