package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.library.model.Book;
import com.library.model.projections.AvailableBooks;
import com.library.service.BookService;

@RestController
public class BookController {
	
	@Autowired
	BookService bookService;
	
	// get all books
	@GetMapping("all-books")
	public ResponseEntity<List<Book>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	// get all available books
	@GetMapping("books")
	public ResponseEntity<List<AvailableBooks>> getAllAvailableBooks(){
		return ResponseEntity.ok(bookService.getAllAvailableBooks());
	}
	
	// get book by name
	@GetMapping("/books/{name}")
	public ResponseEntity<Book> getBookbyName(@PathVariable(value = "name") String name){
		Book book = bookService.getBookByName(name);
			
		return ResponseEntity.ok().body(book);
	}
	
	// save books
	@PostMapping("books")
	public ResponseEntity<Book> createEmployee(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.createBook(book));
	}
	
}










