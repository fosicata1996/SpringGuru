package guru.springframework.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService
{
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient,
			RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository)
	{
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
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
				.map(ingredientToIngredientCommand::convert).findAny();
		
		if (!ingredientCommandOptional.isPresent())
		{
			log.error("Ingredient id not found. ID = " + ingredientId);
		}
		
		return ingredientCommandOptional.get();
	}
	
	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command)
	{
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		
		if (!recipeOptional.isPresent())
		{
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		}
		else
		{
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();
			
			if (ingredientOptional.isPresent())
			{
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUom(
						unitOfMeasureRepository.findById(command.getUom().getId())
								.orElseThrow(() -> new RuntimeException("UOM Not Found!!")));
			}
			else
			{
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
					.findFirst();
			
			if (!savedIngredientOptional.isPresent())
			{
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
						.findFirst();
			}
			
			return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		}
	}
	
	@Override
	public void deleteById(Long recipeId, Long idToDelete)
	{
		log.debug("Deleting ingredient: " + recipeId + " --> " + idToDelete);
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if (recipeOptional.isPresent())
		{
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(idToDelete))
					.findFirst();
			
			if (ingredientOptional.isPresent())
			{
				Ingredient ingredientToDelete = ingredientOptional.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientOptional.get());
				recipeRepository.save(recipe);
			}
		}
		else
		{
			log.debug("Recipe not found. Id: " + recipeId);
		}
	}
}