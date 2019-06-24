package com.bookkeeping.librarymanagement.controller;

import com.bookkeeping.librarymanagement.entity.Book;
import com.bookkeeping.librarymanagement.exception.CustomException;
import com.bookkeeping.librarymanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/book")
@Api(value = "Book", description = "An API for Book Management", tags = "Book API")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get the Book information for a given id", response = Book.class)
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        log.info("Fetching book with id {}", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Save Book", response = Book.class)
    public ResponseEntity<Book> updateBook(@RequestHeader(required =  false) String authorization,
                                            @PathVariable("id") int id,
                                            @RequestBody Book book) {
        log.info("Updating book with id {}", id);
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete Book", response = Book.class)
    public ResponseEntity<Boolean> deleteBook(@RequestHeader(required =  false) String authorization,
                                           @PathVariable("id") int id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "Create Book", response = Book.class)
    public ResponseEntity<Book> createBook(@RequestHeader(required =  false) String authorization,
                                           @RequestParam("book") String book) {
        log.info("Creating Book {}", book);
        ObjectMapper om = new ObjectMapper();
        Book bookObj;
        try {
            bookObj = om.readValue(book, Book.class);
            if(bookService.isBookExists(bookObj)) {
                log.error("Unable to create. A Book with name {} already exist", bookObj.getName());
                return new ResponseEntity("Unable to create. A Book with name " +
                        bookObj.getName() + " already exist.", CONFLICT);
            }
            return ResponseEntity.ok(bookService.saveBook(bookObj));
        } catch (Exception exp) {
            log.info("Error parsing book String {} ", book);
            throw new CustomException(exp.getMessage(), HttpStatus.BAD_REQUEST, true);
        }
    }
}
