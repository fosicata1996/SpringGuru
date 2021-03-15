package guru.springframework.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService
{
	private final IngredientToIngredientCommand converter;
	private final RecipeRepository recipeRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand converter, RecipeRepository recipeRepository)
	{
		this.converter = converter;
		this.recipeRepository = recipeRepository;
	}
	
	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId)
	{
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if (!recipeOptional.isPresent())
		{
			log.error("Recipe id not found. ID = " + recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(converter::convert).findAny();
		
		if (!ingredientCommandOptional.isPresent())
		{
			log.error("Ingredient id not found. ID = " + ingredientId);
		}
		
		return ingredientCommandOptional.get();
	}
}
