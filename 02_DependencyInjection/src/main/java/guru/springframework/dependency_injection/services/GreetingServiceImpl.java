package guru.springframework.dependency_injection.services;

public class GreetingServiceImpl implements GreetingService
{
	@Override
	public String sayGreeting()
	{
		return "Hello World";
	}
}
