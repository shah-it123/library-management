package com.library.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.model.Book;
import com.library.model.projections.AvailableBooks;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long>{
	
	Book findByName(String name);
		
	@Query("SELECT b.id as id, b.isbn as isbn, b.name as name, b.author as author "
			+ "from Book b where b.bookStatus is null")
	List<AvailableBooks> getAllAvailableBooks();
	
}
