/*
 * 
 */
package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.exception.ErrorDetails;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.projections.AvailableBooks;
import com.library.service.BookService;

/**
 * Rest Controller for User for books related API mapping
 * All requests must be prefixed with "/user".
 *
 * @author Shahrukh
 */
@RestController
@RequestMapping("/user")
public class UserBookController {
	
	/** The book service. */
	@Autowired
	BookService bookService;
	
	/**
	 * Get all books only those that are available, that has book status flag as R(Received) or null.
	 * @return {@link ResponseEntity} List of {@link Book} 
	 */
	@GetMapping("books")
	public ResponseEntity<List<AvailableBooks>> getAllAvailableBooks(){
		return ResponseEntity.ok(bookService.getAllAvailableBooks());
	}
	
	/**
	 * Search all books by name, only those that are available, that has book status flag as R(Received) or null.
	 *
	 * @param name 
	 * Book name
	 * @return {@link ResponseEntity} List of {@link Book}
	 * 
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@GetMapping("/books/{name}")
	public ResponseEntity<List<AvailableBooks>> getAvailableBooksByName(@PathVariable(value = "name") String name) throws ResourceNotFoundException{	
		return ResponseEntity.ok().body(bookService.getAvailableBooksByName(name));
	}
		
	/**
	 * Search all books by author, only those that are available, that has book status flag as R(Received) or null.
	 *
	 * @param name 
	 * Book name
	 * @return {@link ResponseEntity} List of {@link Book}
	 * 
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@GetMapping("/books/author/{name}")
	public ResponseEntity<List<AvailableBooks>> getAvailableBooksByAuthor(@PathVariable(value = "name") String name) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(bookService.getAvailableBooksByAuthor(name));
	}
	
	/**
	 * Adds the user to the book which means the book status is now pending.
	 *
	 * @param bookId 
	 * the book id
	 * @return {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@PutMapping("/books/add/{id}")
	public ResponseEntity<Book> addUserBook(@PathVariable(value = "id") long bookId) throws ResourceNotFoundException{
		return ResponseEntity.ok(bookService.addUserBook(bookId));
		
	} 
	
}










