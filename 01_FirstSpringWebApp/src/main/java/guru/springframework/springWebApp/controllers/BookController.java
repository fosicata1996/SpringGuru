package guru.springframework.springWebApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.springWebApp.repositories.BookRepository;

@Controller
public class BookController
{
	private final BookRepository bookRepository;
	
	public BookController(BookRepository bookRepository)
	{
		this.bookRepository = bookRepository;
	}
	
	@RequestMapping("/books")
	public String getBooks(Model model)
	{
		model.addAttribute("books", bookRepository.findAll());
		
		return "books/list";
	}
}
