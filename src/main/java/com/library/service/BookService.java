/*
 * 
 */
package com.library.service;

import java.util.List;
import java.util.Map;

import com.library.dto.BookDTO;
import com.library.exception.ErrorDetails;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.ParameterMst;
import com.library.model.projections.AvailableBooks;

/**
 * The Interface BookService.
 */
public interface BookService {

	/**
	 * Gets the all books.
	 *
	 * @return List of {@link Book}
	 */
	List<Book> getAllBooks();
	
	/**
	 * Gets the all available books.
	 *
	 *@return List of {@link AvailableBooks}
	 */
	List<AvailableBooks> getAllAvailableBooks();
	
	/**
	 * Creates the book.
	 *
	 * @param book the book
	 * @return {@link Book}
	 */
	Book createBook(Book book);
	
	/**
	 * Creates the books.
	 *
	 * @param bookList the book list
	 * @return List of {@link Book}
	 */
	List<Book> createBooks(List<Book> bookList);

	/**
	 * Gets the all books by name.
	 *
	 * @param name the name
	 * @return List of {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	List<Book> getAllBooksByName(String name) throws ResourceNotFoundException;

	/**
	 * Gets the all books by author.
	 *
	 * @param name the name
	 * @return List of {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	List<Book> getAllBooksByAuthor(String name) throws ResourceNotFoundException;

	/**
	 * Gets the available books by name.
	 *
	 * @param name the name
	 * @return List of {@link AvailableBooks}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	List<AvailableBooks> getAvailableBooksByName(String name) throws ResourceNotFoundException;
	
	/**
	 * Gets the available books by author.
	 *
	 * @param name the name
	 * @return List of {@link AvailableBooks}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	List<AvailableBooks> getAvailableBooksByAuthor(String name) throws ResourceNotFoundException;

	/**
	 * Update book.
	 *
	 * @param bookId the book id
	 * @param bookDetails the book details
	 * @return {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	Book updateBook(long bookId, BookDTO bookDetails) throws ResourceNotFoundException;

	/**
	 * Creates the param.
	 *
	 * @param param the param
	 * @return {@link ParameterMst}
	 */
	ParameterMst createParam(ParameterMst param);

	/**
	 * Adds the user book.
	 *
	 * @param bookId the book id
	 * @return {@link Book}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	Book addUserBook(long bookId) throws ResourceNotFoundException;

	/**
	 * Update param.
	 *
	 * @param paramId the param id
	 * @param paramDetails the param details
	 * @return {@link ParameterMst}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	ParameterMst updateParam(long paramId, ParameterMst paramDetails) throws ResourceNotFoundException;

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	List<ParameterMst> getParams();

	/**
	 * Gets the pending books.
	 *
	 * @param username the username
	 * @return List of {@link Book}
	 */
	List<Book> getPendingBooks(String username);

	/**
	 * Return book.
	 *
	 * @param bookId the book id
	 * @return {@link Map}
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	Map<String, Boolean> returnBook(long bookId) throws ResourceNotFoundException;

}
