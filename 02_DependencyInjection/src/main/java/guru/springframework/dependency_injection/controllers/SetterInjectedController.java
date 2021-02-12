package guru.springframework.dependency_injection.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import guru.springframework.dependency_injection.services.GreetingService;

@Controller
public class SetterInjectedController
{
	private GreetingService greetingService;
	
	@Autowired
	public void setGreetingService(@Qualifier("setterGreetingService") GreetingService greetingService)
	{
		this.greetingService = greetingService;
	}
	
	public String getGreeting()
	{
		return greetingService.sayGreeting();
	}
}
