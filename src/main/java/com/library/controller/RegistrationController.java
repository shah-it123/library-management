package com.library.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.service.UserService;
import com.library.util.LibraryUser;

/**
 * The Class RegistrationController.
 */
@RestController
@RequestMapping("/security")
public class RegistrationController {
	
	/** The user service. */
	@Autowired
	private UserService userService;
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName());
    
	/**
	 * Inits the binder.
	 * Trims the Strings, by removing spaces before and after the string.
	 * @param dataBinder {@link WebDataBinder}
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	/**
	 * Adds the user.
	 *
	 * @param libraryUser {@link LibraryUser}
	 * @param theBindingResult gets us the binding result of incoming data which has been binded with {@link LibraryUser}
	 * @return the string Either username or Error in {@link BindingResult}
	 */
	@PostMapping("/users")
	public String addUser(@RequestBody LibraryUser libraryUser,
			BindingResult theBindingResult) {
		String sendErrors = "";
		if(theBindingResult.hasErrors()) {
			for(ObjectError error: theBindingResult.getAllErrors() ) {
				sendErrors = sendErrors + " | " + error.getDefaultMessage();
				logger.info(">>>>> Errors: "+error.getDefaultMessage());
			}
			return sendErrors.trim().substring(1);
		}
		
		return userService.save(libraryUser);
	}
	
}














