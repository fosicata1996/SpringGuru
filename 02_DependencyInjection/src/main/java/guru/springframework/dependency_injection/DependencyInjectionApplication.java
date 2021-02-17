package guru.springframework.dependency_injection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import guru.springframework.dependency_injection.controllers.MyController;
import guru.springframework.dependency_injection.examplebeans.FakeDataSource;
import guru.springframework.dependency_injection.examplebeans.FakeJmsBroker;

@SpringBootApplication
public class DependencyInjectionApplication
{
	
	public static void main(String[] args)
	{
		ApplicationContext ctx = SpringApplication.run(DependencyInjectionApplication.class, args);
		
		MyController myController = (MyController) ctx.getBean("myController");
		
		FakeDataSource fakeDataSource = ctx.getBean(FakeDataSource.class);
		
		System.out.println("######## " + fakeDataSource.getUser());
		
		FakeJmsBroker fakeJmsBroker = ctx.getBean(FakeJmsBroker.class);
		
		System.out.println("######## " + fakeJmsBroker.getUser());
		
	}
	
}
