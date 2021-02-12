package guru.springframework.dependency_injection.controllers;

import org.springframework.stereotype.Controller;

import guru.springframework.dependency_injection.services.GreetingService;

@Controller
public class MyController
{
	private final GreetingService greetingService;
	
	public MyController(GreetingService greetingService)
	{
		this.greetingService = greetingService;
	}
	
	public String sayHello()
	{
		return greetingService.sayGreeting();
	}
}
