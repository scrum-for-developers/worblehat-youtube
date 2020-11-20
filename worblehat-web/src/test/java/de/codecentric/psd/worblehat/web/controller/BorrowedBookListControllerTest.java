package de.codecentric.psd.worblehat.web.controller;


import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.Borrowing;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.ui.ModelMap;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.List;

import static de.codecentric.psd.worblehat.web.controller.BorrowingTestData.borrowingWith;
import static de.codecentric.psd.worblehat.web.controller.ReturnAllBooksFormTestData.emailAddress;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings
class BorrowedBookListControllerTest {

    @Mock
    private BookService bookServiceMock;

    @InjectMocks
    private BorrowedBookListController controllerUnderTest;


    @Test
    void setupForm(){
        var modelMap = new ModelMap();

        controllerUnderTest.get(modelMap);

        assertThat(modelMap.get("returnAllBookFormData")).isNotNull();
    }


    @Test
    void shouldReturnOneBorrowedBook(){
        String emailAddress = "sandra@worblehat.net";
        when(bookServiceMock.findAllBorrowingsByEmailAddress(emailAddress)).thenReturn(List.of(borrowingWith(emailAddress)));

        var borrowedBookList = new ModelMap();
        String page = controllerUnderTest.findBorrowedBooksByEmailAddress(emailAddress(emailAddress), new MapBindingResult(new HashMap<>(), ""), borrowedBookList);

        assertThat(page).isEqualTo("borrowedBookList");
        assertThat(borrowedBookList.get("borrowings")).isInstanceOf(List.class);
        List<Borrowing> borrowedBooks = (List<Borrowing>)borrowedBookList.get("borrowings");
        assertThat(borrowedBooks).hasSize(1);
    }

}
