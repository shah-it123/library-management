/*
 * 
 */
package com.library.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.dto.BookDTO;
import com.library.exception.ErrorDetails;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.ParameterMst;
import com.library.model.User;
import com.library.model.UserBookHistory;
import com.library.model.projections.AvailableBooks;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import com.library.service.ParamService;
import com.library.service.UserBookHistoryService;
import com.library.service.UserService;
import com.library.util.DateUtils;
import com.library.util.JwtUtil;

/**
 * The Class BookServiceimpl.
 */
@Service
public class BookServiceimpl implements BookService {
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;
	
	/** The user service. */
	@Autowired
	private UserService userService; 
	
	/** The user book history service. */
	@Autowired
	private UserBookHistoryService userBookHistoryService;
	
	/**
	 * Instantiates a new book serviceimpl.
	 */
	public BookServiceimpl() {

	}
		
	/** The book repository. */
	private BookRepository bookRepository;
	
	/**
	 * Instantiates a new book serviceimpl.
	 *
	 * @param bookrepository {@link BookRepository}
	 */
	@Autowired
	public BookServiceimpl(BookRepository bookrepository) {
		this.bookRepository = bookrepository;
	}
	
	/** The param service. */
	@Autowired
	private ParamService paramService;

	/**
	 * Gets all books.
	 *
	 * @return List of {@link Book}
	 */
	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	/**
	 * Creates the book.
	 *
	 * @param bookList the book list
	 * @return List of {@link Book}
	 */
	@Override
	public List<Book> createBooks(List<Book> bookList) {
		List<Book> resultList = new ArrayList<>();
		
		for(Book book: bookList) {
			resultList.add(bookRepository.save(book));
		}
		return resultList;
	}
	
	/**
	 * Creates the book.
	 *
	 * @param book the book
	 * @return the book
	 */
	@Override
	public Book createBook(Book book) {		
		return bookRepository.save(book);
	}

	/**
	 * Gets the all available books.
	 *
	 * @return the all available books
	 */
	@Override
	public List<AvailableBooks> getAllAvailableBooks() {
		return bookRepository.getAllAvailableBooks();
	}

	/**
	 * Gets the all books by name.
	 *
	 * @param name the name
	 * @return the all books by name
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public List<Book> getAllBooksByName(String name) throws ResourceNotFoundException {
		 List<Book> listBook = bookRepository.findByNameIgnoreCase(name);
		 if(listBook.isEmpty()) {
				throw new ResourceNotFoundException("Book not found for this name :: " + name);
			}
		 return listBook;
	}

	/**
	 * Gets the all books by author.
	 *
	 * @param name the name
	 * @return the all books by author
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public List<Book>  getAllBooksByAuthor(String name) throws ResourceNotFoundException {		
		List<Book> listBook = bookRepository.findByAuthorIgnoreCase(name);
		 if(listBook.isEmpty()) {
				throw new ResourceNotFoundException("Book not found for this author :: " + name);
			}
		 return listBook;
	}

	/**
	 * Gets the available books by name.
	 *
	 * @param name the name
	 * @return the available books by name
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public List<AvailableBooks> getAvailableBooksByName(String name) throws ResourceNotFoundException {
		List<AvailableBooks> availableList = bookRepository.getAvailableBooksByName(name);
		if(availableList.isEmpty()) {
			throw new ResourceNotFoundException("Book not found for this name :: " + name);
		}
		return availableList;
	}

	/**
	 * Gets the available books by author.
	 *
	 * @param name the name
	 * @return the available books by author
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public List<AvailableBooks> getAvailableBooksByAuthor(String name)  throws ResourceNotFoundException {
		List<AvailableBooks> availableList =  bookRepository.getAvailableBooksByAuthor(name);
		
		if(availableList.isEmpty()) {
			throw new ResourceNotFoundException("Book not found for this author :: " + name);
		}
		return availableList;
	}

	/**
	 * Update book.
	 *
	 * @param bookId the book id
	 * @param bookDetails the book details
	 * @return the book
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public Book updateBook(long bookId, BookDTO bookDetails) throws ResourceNotFoundException {
		
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
		if(bookDetails.getAuthor() != null)
			book.setAuthor(bookDetails.getAuthor());
		if(bookDetails.getName() != null)
			book.setName(bookDetails.getName());
		if(bookDetails.getIsbn() != null)
			book.setIsbn(bookDetails.getIsbn());
		
		
		return bookRepository.save(book);
	}

	/**
	 * Creates the param.
	 *
	 * @param param the param
	 * @return the parameter mst
	 */
	@Override
	public ParameterMst createParam(ParameterMst param) {

		return paramService.createParam(param);
	}

	/**
	 * Adds user to the book.
	 * First book is retrieved from the id, then username is retrieved from the jwtUtil,
	 * then Default return days from paramter_mst table.
	 * Just add default days to the current date, and save it with selected book.
	 * Also change the status of book (bookStatus) to P.
	 *
	 * @param bookId the book id
	 * @return the book
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public Book addUserBook(long bookId) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
		String username = jwtUtil.getUsername();
		
		logger.info(">>>>> Logged in user: " + username);
		
		User user = userService.findByUserName(username);
		
		ParameterMst pMst = paramService.findById(1L);
		int returnDays = Integer.valueOf(pMst.getValue());
		LocalDateTime ldt = LocalDateTime.from(new Date().toInstant().atZone(ZoneId.of("UTC"))).plusDays(returnDays);
		Date newReturnDate = DateUtils.asDate(ldt);
		
		book.setUser(user);
		book.setReturnDate(newReturnDate);
		book.setBookStatus("P");
		
		return bookRepository.save(book);
	}

	/**
	 * Update param.
	 *
	 * @param paramId the param id
	 * @param paramDetails the param details
	 * @return the parameter mst
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public ParameterMst updateParam(long paramId, ParameterMst paramDetails) throws ResourceNotFoundException {
		ParameterMst pmst = paramService.findById(paramId);
		
		pmst.setValue(paramDetails.getValue());
		pmst.setDescription(paramDetails.getDescription());
		
		return paramService.createParam(pmst);
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	@Override
	public List<ParameterMst> getParams() {
		return paramService.getParams();
	}

	/**
	 * Gets the pending books.
	 *
	 * @param username the username
	 * @return the pending books
	 */
	@Override
	public List<Book> getPendingBooks(String username) {
		User user = userService.findByUserName(username);
		return bookRepository.findByUser(user);
	}

	/**
	 * Return book from user.
	 * First the book details are retrieved from the database
	 * if present, then status is checked, (if its pending(security reasons))
	 * then bookStatus which was previously set to P(pending) is set to R(received)
	 * and the book will thus be available for other users in the system
	 * 
	 * Save the history of this process in userBookHistory table, which keeps the logs of users and books.
	 *
	 * @param bookId the book id
	 * @return the map
	 * @throws ResourceNotFoundException
	 * {@link ResourceNotFoundException} is a custom exception class which gets the {@link ErrorDetails} class
	 */
	@Override
	public Map<String, Boolean> returnBook(long bookId) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
		Map<String, Boolean> response = new HashMap<>();
		if(book.getBookStatus().equals("P")) {
			book.setBookStatus("R");
			bookRepository.save(book);
			
			UserBookHistory ubh = new UserBookHistory(book.getUser(), book, new Date());
			userBookHistoryService.save(ubh);
			
			
			response.put("returned", Boolean.TRUE);
		}else {
			response.put("returned", Boolean.FALSE);
		}
		
		
		return response;
	}
	
}


















