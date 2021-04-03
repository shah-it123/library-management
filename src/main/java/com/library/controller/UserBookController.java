package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.projections.AvailableBooks;
import com.library.service.BookService;

@RestController
@RequestMapping("/user")
public class UserBookController {
	
	@Autowired
	BookService bookService;
	
	// get all available books
	@GetMapping("books")
	public ResponseEntity<List<AvailableBooks>> getAllAvailableBooks(){
		return ResponseEntity.ok(bookService.getAllAvailableBooks());
	}
	
	// get available books by name
	@GetMapping("/books/{name}")
	public ResponseEntity<List<AvailableBooks>> getAvailableBooksByName(@PathVariable(value = "name") String name) throws ResourceNotFoundException{	
		return ResponseEntity.ok().body(bookService.getAvailableBooksByName(name));
	}
		
	// get available books by author
	@GetMapping("/books/author/{name}")
	public ResponseEntity<List<AvailableBooks>> getAvailableBooksByAuthor(@PathVariable(value = "name") String name) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(bookService.getAvailableBooksByAuthor(name));
	}
	
	// add books to user
	@PutMapping("/books/add/{id}")
	public ResponseEntity<Book> addUserBook(@PathVariable(value = "id") long bookId) throws ResourceNotFoundException{
		return ResponseEntity.ok(bookService.addUserBook(bookId));
		
	} 
	
}










