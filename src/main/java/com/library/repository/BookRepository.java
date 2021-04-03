/*
 * 
 */
package com.library.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.model.Book;
import com.library.model.User;
import com.library.model.projections.AvailableBooks;

/**
 * The Interface BookRepository.
 */
@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long>{
	
//	Librarian Queries Start //
	
	/**
	 * Find by name ignore case.
	 *
	 * @param name String
	 * @return List of {@link Book}
	 */
	List<Book> findByNameIgnoreCase(String name);
	
	/**
	 * Find by author ignore case.
	 *
	 * @param name the name
	 * @return List of {@link Book}
	 */
	List<Book> findByAuthorIgnoreCase(String name);
	
	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @return List of {@link Book}
	 */
	List<Book> findByUser(User user);
	
//	Librarian Queries End //
		
	
//	User Queries Start //
	
	/**
	 * Gets all available books.
	 *
	 * @return List of {@link AvailableBooks}
	 */
	@Query("SELECT b.id as id, b.isbn as isbn, b.name as name, b.author as author "
				+ "from Book b where b.bookStatus is null or b.bookStatus = 'R'")
	List<AvailableBooks> getAllAvailableBooks();

	/**
	 * Gets the available books by name.
	 *
	 * @param name the name
	 * @return List of {@link AvailableBooks}
	 */
	@Query("SELECT b.id as id, b.isbn as isbn, b.name as name, b.author as author "
			+ "from Book b where (b.bookStatus is null  or b.bookStatus = 'R') and lower(b.name) LIKE lower(?1) ")
	List<AvailableBooks> getAvailableBooksByName(String name);

	/**
	 * Gets the available books by author.
	 *
	 * @param name the name
	 * @return List of {@link AvailableBooks}
	 */
	@Query("SELECT b.id as id, b.isbn as isbn, b.name as name, b.author as author "
			+ "from Book b where (b.bookStatus is null  or b.bookStatus = 'R') and lower(b.author) LIKE lower(?1)")
	List<AvailableBooks> getAvailableBooksByAuthor(String name);
	
//	User Queries End //
	
	
	
}
