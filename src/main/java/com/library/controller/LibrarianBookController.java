/*
 * 
 */
package com.library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.config.LibrarySecurityConfig;
import com.library.dto.BookDTO;
import com.library.exception.ErrorDetails;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.ParameterMst;
import com.library.service.BookService;

/**
 * Rest Controller for Admin(Librarian) for books related API mapping
 * All requests must be prefixed with "/admin".
 *
 * @author Shahrukh
 */
@RestController
@RequestMapping("/admin")
public class LibrarianBookController {
	
	/** The book service. */
	@Autowired
	BookService bookService;
	
	/**
	 * Get all books, even those that are not available for Users, which means they are taken by some user.
	 * @return {@link ResponseEntity} List of {@link Book}
	 */
	@GetMapping("books")
	public ResponseEntity<List<Book>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	/**
	 * Search all books by name, even those that are not available for Users, which means they are taken by some user.
	 * @param name
	 * book name
	 * @return {@link ResponseEntity} List of {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@GetMapping("/books/{name}")
	public ResponseEntity<List<Book>> getAllBooksByName(@PathVariable(value = "name") String name) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(bookService.getAllBooksByName(name));
	}
	
	/**
	 * Search all books by author, even those that are not available for Users, which means they are taken by some user.
	 * @param name
	 * book name
	 * @return {@link ResponseEntity} List of {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@GetMapping("/books/author/{name}")
	public ResponseEntity<List<Book>> getAllBooksByAuthor(@PathVariable(value = "name") String name) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(bookService.getAllBooksByAuthor(name));
	}
	
	/**
	 * Save book which right now can only be done by Admin. See {@link LibrarySecurityConfig}
	 * @param book
	 * {@link Book} Object
	 * @return {@link Book}
	 */
	@PostMapping("books")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.createBook(book));
	}
	
	/**
	 * Save list of books all at once, which right now can only be done by Admin. See {@link LibrarySecurityConfig}
	 * @param book
	 * {@link Book} Object
	 * @return {@link ResponseEntity} List of {@link Book}
	 */
	@PostMapping("books-list")
	public ResponseEntity<List<Book>> createBooks(@RequestBody List<Book> book) {
		return ResponseEntity.ok(bookService.createBooks(book));
	}
	
	/**
	 * Update Book, by providing name, isbn and author. Other properties cannot be updated right now!
	 * @param bookId
	 * Id of book
	 * @param bookDetails
	 * {@link Book} Object
	 * @return {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@PutMapping("books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable(value = "id") long bookId,
		 @RequestBody BookDTO bookDetails) throws ResourceNotFoundException{
		return ResponseEntity.ok(bookService.updateBook(bookId, bookDetails));
		
	}
	
	/**
	 * Get all Params.
	 * What are Params?
	 * Params are default values that governs whole app.
	 * In here the default return days are 7. So they are params.
	 * @return {@link ResponseEntity} List of {@link ParameterMst}
	 */
	@GetMapping("params")
	public ResponseEntity<List<ParameterMst>> getParams(){
		return ResponseEntity.ok(bookService.getParams());
	}
		
	
	/**
	 * Save the parameter data which contains default values.
	 *
	 * @param param {@link ParameterMst} object
	 * @return {@link ResponseEntity} List of {@link ParameterMst}
	 */
	@PostMapping("params")
	public ResponseEntity<ParameterMst> createParameter(@RequestBody ParameterMst param) {
		return ResponseEntity.ok(bookService.createParam(param));
	}
	
	/**
	 * Updates Parameter data (if Librarian wants to increase or decrease the return date.)	
	 * @param paramId
	 * id of Parameter
	 * @param paramDetails
	 * {@link ParameterMst} object
	 * @return {@link ResponseEntity} List of {@link ParameterMst}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@PutMapping("params/{id}")
	public ResponseEntity<ParameterMst> updateParam(@PathVariable(value = "id") long paramId,
			 @RequestBody ParameterMst paramDetails) throws ResourceNotFoundException {
		return ResponseEntity.ok(bookService.updateParam(paramId, paramDetails));
	}
	
	/**
	 * This gets the books are pending with users, just supply the username, and you will get the info.
	 * @param username
	 * user id of User
	 * @return {@link ResponseEntity} List of {@link Book}
	 */
	@GetMapping("books/pending/{username}")
	public ResponseEntity<List<Book>> getPendingBooks(@PathVariable(value = "username") String username){
		return ResponseEntity.ok(bookService.getPendingBooks(username));
	}
	
	/**
	 * If the user is kind enough to return book to the library, without torn pages or whatsoever, just give the book id, system will do the rest.
	 *
	 * @param bookId id of book
	 * @return {@link Map} returned or not?
	 * @throws ResourceNotFoundException {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@PutMapping("books/return/{id}")
	public Map<String, Boolean> returnBook(@PathVariable(value = "id") long bookId) throws ResourceNotFoundException{
		return bookService.returnBook(bookId);
	}
	
}










