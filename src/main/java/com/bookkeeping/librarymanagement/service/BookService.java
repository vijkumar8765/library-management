package com.bookkeeping.librarymanagement.service;

import com.bookkeeping.librarymanagement.entity.Book;
import com.bookkeeping.librarymanagement.exception.CustomException;
import com.bookkeeping.librarymanagement.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
public class BookService {

	public int returnValue(int id) {
		long result = (long) id;
		if(result > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}else if (result < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		return (int) result;
	}

	@Autowired
	BookRepository bookRepository;

	public Book getBookById(int id) {
		returnValue(id);
		if(!(id >= Integer.MIN_VALUE && id <= Integer.MAX_VALUE)) {
			throw new CustomException("book id overflow for value " + id, NOT_FOUND, true);
		}
		Optional<Book> book = bookRepository.findById(id);
		if(!book.isPresent()) {
			log.error("Book with id {} not found", id);
			throw new CustomException("Book with id {} not found" + id, NOT_FOUND, true);
		}
		return book.get();
	}

	public Book updateBook(int id, Book book) {
		returnValue(id);
		Optional<Book> currentBook = bookRepository.findById(id);
		if (!currentBook.isPresent()) {
			log.error("Unable to update. Book with id {} not found.", id);
			throw new CustomException("Unable to update. Book with id {} not found" + id, NOT_FOUND, true);
		}
		Book newBook = currentBook.get().builder()
				.name(book.getName())
				.author(book.getAuthor())
				.publishedDate(book.getPublishedDate())
				.price(book.getPrice()).build();
		return Optional.of(bookRepository.save(newBook)).get();
	}

	public Boolean deleteBook(int id) {
		returnValue(id);
		Optional<Book> currentBook = bookRepository.findById(id);
		if (!currentBook.isPresent()) {
			log.error("Unable to delete. Book with id {} not found.", id);
			throw new CustomException("Unable to delete. Book with id {} not found" + id, NOT_FOUND, true);
		}
		bookRepository.deleteById(id);
		return true;
	}

	public boolean isBookExists(Book book) {
		Book newBook = Book.builder()
							.name(book.getName())
							.author(book.getAuthor())
							.price(book.getPrice())
							.publishedDate(book.getPublishedDate()).build();
		return bookRepository.findByName(newBook.getName()).size() > 0;
	}

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
}
