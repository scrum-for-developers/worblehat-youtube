package de.codecentric.psd.worblehat.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.mysema.query.types.expr.BooleanExpression;
import de.codecentric.psd.worblehat.domain.*;
import de.codecentric.psd.worblehat.web.command.BookBorrowFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for BorrowingBook
 */
@RequestMapping("/borrow")
@Controller
public class BorrowBookController {

	private BookRepository bookRepository;

	@Autowired
	public BorrowBookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void setupForm(final ModelMap model) {
		model.put("borrowFormData", new BookBorrowFormData());
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(ModelMap modelMap,
			@ModelAttribute("borrowFormData") @Valid BookBorrowFormData cmd,
			BindingResult result) {

		if (result.hasErrors()) {
			modelMap.put("borrowFormData", cmd);
			return "borrow";
		}
		QBook qBook = QBook.book;
		BooleanExpression bookWithISDN = qBook.isbn.eq(cmd.getIsbn());
		Book book = (Book) bookRepository.findOne(bookWithISDN);
		if(book == null) {
			result.rejectValue("isbn", "notBorrowable");
			modelMap.put("borrowFormData", cmd);
			return "borrow";
		}
		try {
			book.borrow(cmd.getEmail());
		} catch (BookAlreadyBorrowedException e) {
			result.reject("internalError");
			modelMap.put("borrowFormData", cmd);
			return "borrow";
		}

		return "home";
	}

	@ExceptionHandler(Exception.class)
	public String handleErrors(Exception ex, HttpServletRequest request) {
		return "home";
	}
}
