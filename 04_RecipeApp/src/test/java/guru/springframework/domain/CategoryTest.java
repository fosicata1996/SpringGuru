package guru.springframework.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest
{
	Category category;
	
	@BeforeEach
	public void setUp()
	{
		category = new Category();
	}
	
	@Test
	void getId()
	{
		Long idValue = 4L;
		category.setId(idValue);
		
		assertEquals(idValue, category.getId());
	}
	
	@Test
	void getDescription()
	{
	}
	
	@Test
	void getRecipes()
	{
	}
}