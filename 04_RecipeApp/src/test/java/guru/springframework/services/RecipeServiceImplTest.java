package guru.springframework.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

class RecipeServiceImplTest
{
	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@BeforeEach
	void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		recipeService = new RecipeServiceImpl(recipeRepository);
	}
	
	@Test
	void getRecipes()
	{
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		
		when(recipeRepository.findAll()).thenReturn(recipeData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(1, recipes.size());
		
		verify(recipeRepository, times(1)).findAll();
	}
}