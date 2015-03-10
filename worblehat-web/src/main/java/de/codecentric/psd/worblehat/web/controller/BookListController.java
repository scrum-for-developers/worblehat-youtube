package de.codecentric.psd.worblehat.web.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookRepository;

/**
 * Controller class for the book table result.
 * 
 * @author psd
 * 
 */

@Controller
@RequestMapping("/bookList")
public class BookListController {

	private BookRepository bookRepository;

	@Inject
	public BookListController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(ModelMap modelMap) {
		List<Book> books = bookRepository.findAllBooks();
		modelMap.addAttribute("books", books);
		return "bookList";
	}

}
