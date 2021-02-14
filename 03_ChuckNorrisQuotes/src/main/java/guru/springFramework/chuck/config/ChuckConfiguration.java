package guru.springFramework.chuck.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;

@Configuration
public class ChuckConfiguration
{
	@Bean
	public ChuckNorrisQuotes getChuckNorrisQuotesBean()
	{
		return new ChuckNorrisQuotes();
	}
}
