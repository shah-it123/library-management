package com.library.serviceImpl;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.model.Book;
import com.library.model.projections.AvailableBooks;
import com.library.repository.BookRepository;
import com.library.service.BookService;

@Service
public class BookServiceimpl implements BookService {
	
	private BookRepository bookRepository;
	
	@Autowired
	public BookServiceimpl(BookRepository bookrepository) {
		this.bookRepository = bookrepository;
	}

	@Override
	@Transactional
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	@Transactional
	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	@Transactional
	public List<AvailableBooks> getAllAvailableBooks() {
		return bookRepository.getAllAvailableBooks();
	}

	@Override
	public Book getBookByName(String name) {
		return bookRepository.findByName(name);
	}
	
}


















