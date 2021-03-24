package guru.springframework.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;

public class ImageControllerTest
{
	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeService;
	
	ImageController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void getImageForm() throws Exception
	{
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		
		//when
		mockMvc.perform(get("/recipe/1/image"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService).findCommandById(anyLong());
		
	}
	
	@Test
	public void handleImagePost() throws Exception
	{
		MockMultipartFile multipartFile =
				new MockMultipartFile("imageFile", "testing.txt", "text/plain",
						"Spring Framework Guru".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageService).saveImageFile(anyLong(), any());
	}
	
	@Test
	void renderImageFromDB() throws Exception
	{
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		String s = "fake image text";
		
		Byte[] bytesBoxed = new Byte[s.getBytes().length];
		
		int i = 0;
		
		for (byte currentByte : s.getBytes())
		{
			bytesBoxed[i++] = currentByte;
		}
		
		command.setImage(bytesBoxed);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeImage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		assertEquals(s.getBytes().length, responseBytes.length);
	}
}
