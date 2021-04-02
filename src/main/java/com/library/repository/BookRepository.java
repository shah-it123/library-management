package com.library.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.model.Book;
import com.library.model.User;
import com.library.model.projections.AvailableBooks;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long>{
	
//	Librarian Queries Start //
	
	List<Book> findByNameIgnoreCase(String name);
	List<Book> findByAuthorIgnoreCase(String name);
	List<Book> findByUser(User user);
	
//	Librarian Queries End //
		
	
//	User Queries Start //
	
	@Query("SELECT b.id as id, b.isbn as isbn, b.name as name, b.author as author "
			+ "from Book b where b.bookStatus is null or b.bookStatus = 'R'")
	List<AvailableBooks> getAllAvailableBooks();

	@Query("SELECT b.id as id, b.isbn as isbn, b.name as name, b.author as author "
			+ "from Book b where (b.bookStatus is null  or b.bookStatus = 'R') and lower(b.name) LIKE lower(?1) ")
	List<AvailableBooks> getAvailableBooksByName(String name);

	@Query("SELECT b.id as id, b.isbn as isbn, b.name as name, b.author as author "
			+ "from Book b where (b.bookStatus is null  or b.bookStatus = 'R') and lower(b.author) LIKE lower(?1)")
	List<AvailableBooks> getAvailableBooksByAuthor(String name);
	
//	User Queries End //
	
	
	
}
