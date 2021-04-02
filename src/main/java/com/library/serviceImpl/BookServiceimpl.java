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

@Service
public class BookServiceimpl implements BookService {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private UserBookHistoryService userBookHistoryService;
	
	public BookServiceimpl() {

	}
		
	private BookRepository bookRepository;
	
	@Autowired
	public BookServiceimpl(BookRepository bookrepository) {
		this.bookRepository = bookrepository;
	}
	
	@Autowired
	private ParamService paramService;

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public List<Book> createBooks(List<Book> bookList) {
		List<Book> resultList = new ArrayList<>();
		
		for(Book book: bookList) {
			resultList.add(bookRepository.save(book));
		}
		return resultList;
	}
	
	@Override
	public Book createBook(Book book) {		
		return bookRepository.save(book);
	}

	@Override
	public List<AvailableBooks> getAllAvailableBooks() {
		return bookRepository.getAllAvailableBooks();
	}

	@Override
	public List<Book> getAllBooksByName(String name) {
		return bookRepository.findByNameIgnoreCase(name);
	}

	@Override
	public List<Book>  getAllBooksByAuthor(String name) {
		return bookRepository.findByAuthorIgnoreCase(name);
	}

	@Override
	public List<AvailableBooks> getAvailableBooksByName(String name) {
		return bookRepository.getAvailableBooksByName(name);
	}

	@Override
	public List<AvailableBooks> getAvailableBooksByAuthor(String name) {
		return bookRepository.getAvailableBooksByAuthor(name);
	}

	@Override
	public Book updateBook(long bookId, BookDTO bookDetails) {
		
		Book book = bookRepository.findById(bookId).get();
		if(bookDetails.getAuthor() != null)
			book.setAuthor(bookDetails.getAuthor());
		if(bookDetails.getName() != null)
			book.setName(bookDetails.getName());
		if(bookDetails.getIsbn() != null)
			book.setIsbn(bookDetails.getIsbn());
		
		
		return bookRepository.save(book);
	}

	@Override
	public ParameterMst createParam(ParameterMst param) {

		return paramService.createParam(param);
	}

	@Override
	public Book addUserBook(long bookId) {
		Book book = bookRepository.findById(bookId).get();
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

	@Override
	public ParameterMst updateParam(long paramId, ParameterMst paramDetails) {
		ParameterMst pmst = paramService.findById(paramId);
		
		pmst.setValue(paramDetails.getValue());
		pmst.setDescription(paramDetails.getDescription());
		
		return paramService.createParam(pmst);
	}

	@Override
	public List<ParameterMst> getParams() {
		return paramService.getParams();
	}

	@Override
	public List<Book> getPendingBooks(String username) {
		User user = userService.findByUserName(username);
		return bookRepository.findByUser(user);
	}

	@Override
	public Map<String, Boolean> returnBook(long bookId) {
		Book book = bookRepository.findById(bookId).get();
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


















