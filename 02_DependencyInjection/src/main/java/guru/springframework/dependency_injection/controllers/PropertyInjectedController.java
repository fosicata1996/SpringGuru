package guru.springframework.dependency_injection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.dependency_injection.services.GreetingService;

@Controller
public class PropertyInjectedController
{
	@Qualifier("propertyGreetingService")
	@Autowired
	public GreetingService greetingService;
	
	public String getGreeting()
	{
		return greetingService.sayGreeting();
	}
}
