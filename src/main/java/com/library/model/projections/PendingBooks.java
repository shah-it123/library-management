package com.library.model.projections;

public interface PendingBooks {
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
	
	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	String getAuthor();
	
	/**
	 * Gets the isbn.
	 *
	 * @return the isbn
	 */
	String getIsbn();
}
