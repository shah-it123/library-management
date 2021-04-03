/*
 * 
 */
package com.library.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.config.CustomAuthenticationProvider;
import com.library.model.Book;
import com.library.model.ParameterMst;
import com.library.service.BookService;
import com.library.service.UserService;
import com.library.util.JwtUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class LibrarianBookControllerTest.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(LibrarianBookController.class)
@WithMockUser(username = "admin", roles= {"LIBRARIAN"})
public class LibrarianBookControllerTest {
	
	/** The mock mvc. */
	@Autowired
    MockMvc mockMvc;
	
	/** The book service. */
	@MockBean
	BookService bookService;
	
	/** The custom authentication provider. */
	@MockBean
	CustomAuthenticationProvider customAuthenticationProvider;
	
	/** The data source. */
	@MockBean
	DataSource dataSource;
	
	/** The user service. */
	@MockBean
	UserService userService;
	
	/** The jwt util. */
	@MockBean
	JwtUtil jwtUtil;
	
	/**
	 * Gets the all books.
	 *
	 * @return the all books
	 * @throws Exception the exception
	 */
	@Test
	void getAllBooks() throws Exception {
		List<Book> bookList = new ArrayList<>();
		bookList.add(new Book("Schindler's List", "ASC124SDFF", "Oscar"));
		bookList.add(new Book("Gone Girl", "ASC124SHHHDFF", "Girl"));
		
		when(bookService.getAllBooks()).thenReturn(bookList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/books")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(2))).andDo(print());
	}
	
	/**
	 * Creates the book.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void createBook() throws Exception {
		Book book = new Book("The Great Gatsby","GGRR1133","Gatsby");
		when(bookService.createBook(any(Book.class))).thenReturn(book);
		ObjectMapper objectMapper = new ObjectMapper();
		String bookJSON = objectMapper.writeValueAsString(book);
		
		ResultActions result = mockMvc.perform(post("/admin/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bookJSON)
		);
		
		result.andExpect(status().is(200))
			.andExpect(jsonPath("$.name").value("The Great Gatsby"))
			.andExpect(jsonPath("$.isbn").value("GGRR1133"))
			.andExpect(jsonPath("$.author").value("Gatsby"));
	}
	
	/**
	 * Gets the params.
	 *
	 * @return the params
	 * @throws Exception the exception
	 */
	@Test
	void getParams() throws Exception {
		// 7 days - Default Return Date
		List<ParameterMst> pmstList = new ArrayList<>();
		pmstList.add(new ParameterMst("7","Return Date"));
		
		when(bookService.getParams()).thenReturn(pmstList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/params")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(1))).andDo(print());
		
	}
	
	/**
	 * Creates the parameter.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void createParameter() throws Exception {
		ParameterMst pmst = new ParameterMst("7","Return Date");
		when(bookService.createParam(any(ParameterMst.class))).thenReturn(pmst);
		ObjectMapper objectMapper = new ObjectMapper();
		String pmstJSON = objectMapper.writeValueAsString(pmst);
		
		ResultActions result = mockMvc.perform(post("/admin/params")
				.contentType(MediaType.APPLICATION_JSON)
				.content(pmstJSON)
		);
		
		result.andExpect(status().is(200))
			.andExpect(jsonPath("$.value").value("7"))
			.andExpect(jsonPath("$.description").value("Return Date"));
	}
	
}















