package com.library.service;

import java.util.List;

import com.library.model.Book;
import com.library.model.projections.AvailableBooks;

public interface BookService {

	List<Book> getAllBooks();
	
	List<AvailableBooks> getAllAvailableBooks();
	
	Book createBook(Book book);

	Book getBookByName(String name);

}
