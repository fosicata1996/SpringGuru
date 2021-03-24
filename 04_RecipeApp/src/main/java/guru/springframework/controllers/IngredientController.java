package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController
{
	
	private final RecipeService recipeService;
	
	private final IngredientService ingredientService;
	
	private final UnitOfMeasureService unitOfMeasureService;
	
	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService)
	{
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model)
	{
		log.debug("Getting ingredient list for recipe id: " + recipeId);
		
		// use command object to avoid lazy load errors in Thymeleaf.
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model)
	{
		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		return "recipe/ingredient/show";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable String recipeId, Model model)
	{
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", ingredientCommand);
		
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
			@PathVariable String id, Model model)
	{
		model.addAttribute("ingredient",
				ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command)
	{
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		log.debug("Saved recipe id: " + savedCommand.getRecipeId());
		log.debug("Saved ingredient id: " + savedCommand.getId());
		
		return String.format("redirect:/recipe/%s/ingredient/%s/show", savedCommand.getRecipeId(), savedCommand.getId());
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId,
			@PathVariable String id)
	{
		log.debug("Deleting ingredient id: " + id);
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
		
		return String.format("redirect:/recipe/%s/ingredients", recipeId);
	}
}