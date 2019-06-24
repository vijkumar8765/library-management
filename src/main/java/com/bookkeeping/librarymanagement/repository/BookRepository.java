package com.bookkeeping.librarymanagement.repository;

import com.bookkeeping.librarymanagement.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>{


	List<Book> findByName(String name);

}
