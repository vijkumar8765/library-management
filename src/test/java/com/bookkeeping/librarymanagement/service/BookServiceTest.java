package com.bookkeeping.librarymanagement.service;

import com.bookkeeping.librarymanagement.entity.Book;
import com.bookkeeping.librarymanagement.exception.CustomException;
import com.bookkeeping.librarymanagement.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    public void setUp() {
    }

    /*@Test
    public void getBookById() {
        Book resp = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(resp));
        Book response = bookService.getBookById(1);
        assertNotNull(response);
    }*/

    @Test
    public void getBookById() {
        Book resp = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(resp));
        Book response = bookService.getBookById(Integer.MAX_VALUE);
        assertNotNull(response);
    }

    @Test
    public void getBookByIdEmptyResponse() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBookById(1))
                .isInstanceOf(CustomException.class);
    }

    @Test
    public void updateBookEmptyResponse() {
        Book resp = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.updateBook(Integer.MIN_VALUE, resp))
                .isInstanceOf(CustomException.class);
    }

    @Test
    public void updateBook() {
        Book input = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        Book response = Book.builder()
                .id(1)
                .name("book2")
                .author("author2")
                .price(100.01d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(input));
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(response);
        bookService.updateBook(1, input);
    }

    @Test
    public void deleteBookEmptyResponse() {
        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.deleteBook(1))
                .isInstanceOf(CustomException.class);
    }

    @Test
    public void deleteBook() {
        Book input = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(input));
        doNothing().when(bookRepository).deleteById(anyInt());
        bookService.deleteBook(1);
    }

    @Test
    public void isBookExists() {
        Book input = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        List<Book> books = Arrays.asList(Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build());
        when(bookRepository.findByName(anyString())).thenReturn(books);
        assertTrue(bookService.isBookExists(input));
    }

    @Test
    public void isBookExistsEmptyList() {
        Book input = Book.builder()
                .id(1)
                .name("book1")
                .author("author1")
                .price(100.02d)
                .publishedDate(LocalDate.now())
                .build();
        List<Book> books = Arrays.asList();
        when(bookRepository.findByName(anyString())).thenReturn(books);
        assertFalse(bookService.isBookExists(input));
    }

    @Test
    public void saveBook() {
        Book input = Book.builder()
                .id(1)
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
        when(bookRepository.save(input)).thenReturn(resp);
        Book response = bookService.saveBook(input);
        assertNotNull(response);
    }

}
