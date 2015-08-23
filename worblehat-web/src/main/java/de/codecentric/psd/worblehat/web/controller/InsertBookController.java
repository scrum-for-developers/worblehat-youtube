package de.codecentric.psd.worblehat.web.controller;

import javax.validation.Valid;
import java.util.List;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookFactory;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.web.command.BookDataFormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/insertBooks")
public class InsertBookController {

	private static final Logger LOG = LoggerFactory
			.getLogger(InsertBookController.class);

	private BookFactory bookFactory;

	private BookRepository bookRepository;

	@Autowired
	public InsertBookController(BookFactory bookFactory, BookRepository bookRepository) {
		this.bookFactory = bookFactory;
		this.bookRepository = bookRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void setupForm(ModelMap modelMap) {
		modelMap.put("bookDataFormData", new BookDataFormData());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(ModelMap modelMap,
			@ModelAttribute("bookDataFormData") @Valid BookDataFormData cmd,
			BindingResult result) {

		modelMap.put("bookDataFormData", cmd);
		if (result.hasErrors()) {
			return "insertBooks";
		} else {
			bookFactory.createBook(cmd.getTitle(), cmd.getAuthor(),
					cmd.getEdition(), cmd.getIsbn(),
					Integer.parseInt(cmd.getYearOfPublication()));
			LOG.debug("new book instance is created: " + cmd.getIsbn());

			List<Book> books = bookRepository.findAllBooks();
			modelMap.addAttribute("books", books);

			return "bookList";
		}
	}

}
