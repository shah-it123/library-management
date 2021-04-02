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

import com.library.dto.BookDTO;
import com.library.model.Book;
import com.library.model.ParameterMst;
import com.library.model.UserBookHistory;
import com.library.service.BookService;

@RestController
@RequestMapping("/admin")
public class LibrarianBookController {
	
	@Autowired
	BookService bookService;
	
	// get all books
	@GetMapping("books")
	public ResponseEntity<List<Book>> getAllBooks(){
		return ResponseEntity.ok(bookService.getAllBooks());
	}
	
	// get all books by name
	@GetMapping("/books/{name}")
	public ResponseEntity<List<Book>> getAllBooksByName(@PathVariable(value = "name") String name){
		return ResponseEntity.ok().body(bookService.getAllBooksByName(name));
	}
	
	// get all books by author
	@GetMapping("/books/author/{name}")
	public ResponseEntity<List<Book>> getAllBooksByAuthor(@PathVariable(value = "name") String name){
		return ResponseEntity.ok().body(bookService.getAllBooksByAuthor(name));
	}
	
	// save book
	@PostMapping("books")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.createBook(book));
	}
	
	// save books
	@PostMapping("books-list")
	public ResponseEntity<List<Book>> createBooks(@RequestBody List<Book> book) {
		return ResponseEntity.ok(bookService.createBooks(book));
	}
	
	// update books
	@PutMapping("books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable(value = "id") long bookId,
		 @RequestBody BookDTO bookDetails){
		return ResponseEntity.ok(bookService.updateBook(bookId, bookDetails));
		
	}
	
	// get all books
	@GetMapping("params")
	public ResponseEntity<List<ParameterMst>> getParams(){
		return ResponseEntity.ok(bookService.getParams());
	}
		
	
	// save parameter
	@PostMapping("params")
	public ResponseEntity<ParameterMst> createParameter(@RequestBody ParameterMst param) {
		return ResponseEntity.ok(bookService.createParam(param));
	}
	
	// update parameter		
	@PutMapping("params/{id}")
	public ResponseEntity<ParameterMst> updateParam(@PathVariable(value = "id") long paramId,
			 @RequestBody ParameterMst paramDetails) {
		return ResponseEntity.ok(bookService.updateParam(paramId, paramDetails));
	}
	
	// pending user books
	@GetMapping("books/pending/{username}")
	public ResponseEntity<List<Book>> getPendingBooks(@PathVariable(value = "username") String username){
		return ResponseEntity.ok(bookService.getPendingBooks(username));
	}
	
	// return book from user
	@PutMapping("books/return/{id}")
	public Map<String, Boolean> returnBook(@PathVariable(value = "id") long bookId){
		return bookService.returnBook(bookId);
		
	}
	
}










