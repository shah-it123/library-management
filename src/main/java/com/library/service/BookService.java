package com.library.service;

import java.util.List;
import java.util.Map;

import com.library.dto.BookDTO;
import com.library.model.Book;
import com.library.model.ParameterMst;
import com.library.model.UserBookHistory;
import com.library.model.projections.AvailableBooks;

public interface BookService {

	List<Book> getAllBooks();
	
	List<AvailableBooks> getAllAvailableBooks();
	
	Book createBook(Book book);
	
	List<Book> createBooks(List<Book> bookList);

	List<Book> getAllBooksByName(String name);

	List<Book> getAllBooksByAuthor(String name);

	List<AvailableBooks> getAvailableBooksByName(String name);
	
	List<AvailableBooks> getAvailableBooksByAuthor(String name);

	Book updateBook(long bookId, BookDTO bookDetails);

	ParameterMst createParam(ParameterMst param);

	Book addUserBook(long bookId);

	ParameterMst updateParam(long paramId, ParameterMst paramDetails);

	List<ParameterMst> getParams();

	List<Book> getPendingBooks(String username);

	Map<String, Boolean> returnBook(long bookId);

}
