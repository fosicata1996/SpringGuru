package guru.springframework.dependency_injection.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.dependency_injection.services.GreetingService;

@Controller
public class ConstructorInjectedController
{
	private final GreetingService greetingService;
	
	//@Autowired --> Not needed for constructor injection
	public ConstructorInjectedController(@Qualifier("constructorGreetingService") GreetingService greetingService)
	{
		this.greetingService = greetingService;
	}
	
	public String getGreeting()
	{
		return greetingService.sayGreeting();
	}
}
