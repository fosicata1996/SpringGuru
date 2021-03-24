package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;

@Controller
public class ImageController
{
	private final ImageService imageService;
	private final RecipeService recipeService;
	
	public ImageController(ImageService imageService, RecipeService recipeService)
	{
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	@GetMapping("recipe/{id}/image")
	public String showUploadForm(@PathVariable String id, Model model)
	{
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		
		return "recipe/imageUploadForm";
	}
	
	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable String id, @RequestParam("imageFile") MultipartFile file)
	{
		imageService.saveImageFile(Long.valueOf(id), file);
		
		return "redirect:/recipe/" + id + "/show";
	}
}