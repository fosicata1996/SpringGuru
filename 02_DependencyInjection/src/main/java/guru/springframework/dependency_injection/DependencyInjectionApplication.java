package guru.springframework.dependency_injection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import guru.springframework.dependency_injection.controllers.ConstructorInjectedController;
import guru.springframework.dependency_injection.controllers.I18nController;
import guru.springframework.dependency_injection.controllers.MyController;
import guru.springframework.dependency_injection.controllers.PropertyInjectedController;
import guru.springframework.dependency_injection.controllers.SetterInjectedController;

@SpringBootApplication
@ComponentScan(basePackages = { "guru.services", "guru.springframework" })
public class DependencyInjectionApplication
{
	
	public static void main(String[] args)
	{
		ApplicationContext ctx = SpringApplication.run(DependencyInjectionApplication.class, args);
		
		MyController myController = (MyController) ctx.getBean("myController");
		
		System.out.println(">>>>>> Primary Bean <<<<<");
		System.out.println(myController.sayHello());
		
		System.out.println("--------------------------------------------------");
		
		System.out.println(">>>>> Property <<<<<");
		PropertyInjectedController propertyC = (PropertyInjectedController) ctx.getBean("propertyInjectedController");
		System.out.println(propertyC.getGreeting());
		
		System.out.println(">>>>> Setter <<<<<");
		SetterInjectedController setterC = (SetterInjectedController) ctx.getBean("setterInjectedController");
		System.out.println(setterC.getGreeting());
		
		System.out.println(">>>>> Constructor <<<<<");
		ConstructorInjectedController constructorC = (ConstructorInjectedController) ctx.getBean("constructorInjectedController");
		System.out.println(constructorC.getGreeting());
		
		System.out.println("--------------------------------------------------");
		
		System.out.println("--> @Profile");
		I18nController i18nController = (I18nController) ctx.getBean("i18nController");
		System.out.println(i18nController.sayHello());
		
	}
	
}
