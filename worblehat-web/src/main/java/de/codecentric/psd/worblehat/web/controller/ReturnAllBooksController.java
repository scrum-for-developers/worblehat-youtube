package de.codecentric.psd.worblehat.web.controller;

import javax.inject.Inject;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.command.ReturnAllBooksFormData;
import de.codecentric.psd.worblehat.web.validator.ValidateReturnAllBooks;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller class for the
 * 
 * @author psd
 * 
 */
@Controller
@RequestMapping("/returnAllBooks")
public class ReturnAllBooksController {

	ValidateReturnAllBooks validateReturnAllBooks = new ValidateReturnAllBooks();

	private BookService bookService;

	@Inject
	public ReturnAllBooksController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void prepareView(ModelMap modelMap) {
		modelMap.put("returnAllBookFormData", new ReturnAllBooksFormData());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String returnAllBooks(
			@ModelAttribute("returnAllBookFormData") ReturnAllBooksFormData formData,
			BindingResult result) {
		validateReturnAllBooks.validate(formData, result);
		if (result.hasErrors()) {
			return "/returnAllBooks";
		} else {
			bookService.returnAllBooksByBorrower(formData.getEmailAddress());
			return "/home";
		}
	}

}
