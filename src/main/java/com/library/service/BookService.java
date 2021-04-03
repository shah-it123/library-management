package com.library.service;

import java.util.List;
import java.util.Map;

import com.library.dto.BookDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.ParameterMst;
import com.library.model.projections.AvailableBooks;

public interface BookService {

	List<Book> getAllBooks();
	
	List<AvailableBooks> getAllAvailableBooks();
	
	Book createBook(Book book);
	
	List<Book> createBooks(List<Book> bookList);

	List<Book> getAllBooksByName(String name) throws ResourceNotFoundException;

	List<Book> getAllBooksByAuthor(String name) throws ResourceNotFoundException;

	List<AvailableBooks> getAvailableBooksByName(String name) throws ResourceNotFoundException;
	
	List<AvailableBooks> getAvailableBooksByAuthor(String name) throws ResourceNotFoundException;

	Book updateBook(long bookId, BookDTO bookDetails) throws ResourceNotFoundException;

	ParameterMst createParam(ParameterMst param);

	Book addUserBook(long bookId) throws ResourceNotFoundException;

	ParameterMst updateParam(long paramId, ParameterMst paramDetails) throws ResourceNotFoundException;

	List<ParameterMst> getParams();

	List<Book> getPendingBooks(String username);

	Map<String, Boolean> returnBook(long bookId) throws ResourceNotFoundException;

}
