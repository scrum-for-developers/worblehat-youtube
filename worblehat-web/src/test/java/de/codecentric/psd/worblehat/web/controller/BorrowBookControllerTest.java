package de.codecentric.psd.worblehat.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.QBook;
import de.codecentric.psd.worblehat.domain.BookRepository;
import de.codecentric.psd.worblehat.web.command.BookBorrowFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

public class BorrowBookControllerTest {
}
