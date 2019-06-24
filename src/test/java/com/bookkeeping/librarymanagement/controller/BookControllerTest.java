package com.bookkeeping.librarymanagement.controller;

import com.bookkeeping.librarymanagement.entity.Book;
import com.bookkeeping.librarymanagement.exception.CustomErrorType;
import com.bookkeeping.librarymanagement.exception.CustomException;
import com.bookkeeping.librarymanagement.repository.BookRepository;
import com.bookkeeping.librarymanagement.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class, secure = false)
@ContextConfiguration(classes = {BookController.class})
public class BookControllerTest {

    @Autowired
    BookController bookController;

    @MockBean
    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    public void setUp() {
    }

    @Test
    public void getBookByIdTest() {
        Book resp = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookService.getBookById(anyInt())).thenReturn(resp);
        ResponseEntity<Book> response = bookController.getBookById(1);
        assert_checks(resp,response);
    }

    public static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }

    @Test
    public void getBookByIWithMaxIdValue() {
        int maxid = Integer.MAX_VALUE + 1;
        System.out.println("max_value: " + Integer.MAX_VALUE);
        System.out.println("maxid: " + Math.abs(maxid));
        Book resp = Book.builder()
                .id(Math.abs(maxid))
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookService.getBookById(Math.abs(maxid))).thenThrow(CustomErrorType.class);
        assertThatThrownBy(() -> bookController.getBookById(Math.abs(maxid))).isInstanceOf(CustomErrorType.class);
    }

    @Test
    public void updateBookTest() {
        Book currentBook = Book.builder()
                .id(Math.abs(1))
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        Book resp = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookService.updateBook(anyInt(), any(Book.class))).thenReturn(currentBook);
        ResponseEntity<Book> response = bookController.updateBook(anyString(), anyInt(), resp);
        assert_checks(currentBook,response);
    }

    @Test
    public void deleteBook() {
        when(bookService.deleteBook(anyInt())).thenReturn(true);
        ResponseEntity<Boolean> response = bookController.deleteBook("", 1);
        assertNotNull("true", response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void createBook() {
        String book_json = "{\n" +
                "\n" +
                "\"id\": 3,\n" +
                "\n" +
                "\"name\": \"ElvisPriceley\",\n" +
                "\n" +
                "\"author\": \"TestAuthor\",\n" +
                "\n" +
                "\"publishedDate\": null,\n" +
                "\n" +
                "\"price\": 102.20\n" +
                "}";
        Book currentBook = Book.builder()
                .id(Math.abs(1))
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookService.saveBook(any(Book.class))).thenReturn(currentBook);
        ResponseEntity<Book> response = bookController.createBook("", book_json);
        assert_checks(currentBook,response);
    }

    @Test
    public void createBookAlreadyExistsTest() {
        String book_json = "{\n" +
                "\n" +
                "\"id\": 3,\n" +
                "\n" +
                "\"name\": \"ElvisPriceley\",\n" +
                "\n" +
                "\"author\": \"TestAuthor\",\n" +
                "\n" +
                "\"publishedDate\": null,\n" +
                "\n" +
                "\"price\": 102.20\n" +
                "}";
        when(bookService.isBookExists(any(Book.class))).thenReturn(true);
        ResponseEntity<Book> errResp = bookController.createBook("", book_json);
        assertEquals(HttpStatus.CONFLICT,errResp.getStatusCode());
    }

    @Test
    public void createBookExceptionTest() {
        String book_json = "{\n" +
                "\n" +
                "\"id\": 3,\n" +
                "\n" +
                "\"name\": \"ElvisPriceley\",\n" +
                "\n" +
                "\"author\": \"TestAuthor\",\n" +
                "\n" +
                "\"publishedDate\": null,\n" +
                "\n" +
                "\"price\": 102.20\n" +
                "}";
        when(bookService.saveBook(any(Book.class))).thenThrow(CustomException.class);
        assertThatThrownBy(() -> bookController.createBook("", book_json)).isInstanceOf(CustomException.class);
    }

    private void assert_checks(Object object, ResponseEntity response){
        assertNotNull(response);
        assertEquals(object, response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    private void assert_books(Book book1, Book book2){
        assertNotNull(book1);
        assertNotNull(book2);
        assertEquals(book1.getId(), book2.getId());
        assertEquals(book1.getName(), book2.getName());
        assertEquals(book1.getAuthor(), book2.getAuthor());
        assertEquals(book1.getPrice(), book2.getPrice());
        assertEquals(book1.getPublishedDate(), book2.getPublishedDate());
    }
}
