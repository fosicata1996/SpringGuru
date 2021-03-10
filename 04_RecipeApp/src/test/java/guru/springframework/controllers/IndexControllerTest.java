package guru.springframework.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import guru.springframework.services.RecipeService;

class IndexControllerTest
{
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController controller;
	
	@BeforeEach
	void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		controller = new IndexController(recipeService);
	}
	
	@Test
	void getIndexPage()
	{
		String viewName = controller.getIndexPage(model);
		
		assertEquals("index", viewName);
		verify(recipeService).getRecipes();
		verify(model).addAttribute(eq("recipes"), anySet());
	}
}