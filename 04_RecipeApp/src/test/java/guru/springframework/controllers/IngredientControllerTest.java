package guru.springframework.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;

public class IngredientControllerTest
{
	@Mock
	RecipeService recipeService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		controller = new IngredientController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	void testListIngredients() throws Exception
	{
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService).findCommandById(anyLong());
	}
}
