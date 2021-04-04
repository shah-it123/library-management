/*
 * 
 */
package com.library.config;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.library.filter.CorsFilter;
import com.library.filter.JwtFilter;
import com.library.service.UserService;

// TODO: Auto-generated Javadoc
/**
 * Security configuration of application.
 *
 * @author Shahrukh
 */
@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {
	
	/** The custom authentication provider. */
	@Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
	
	/** The security data source. */
	@Autowired
	private DataSource securityDataSource;
	
	/** The user service. */
	@Autowired
    private UserService userService;
	
	/** The jwt filter. */
	@Autowired
    private JwtFilter jwtFilter;
    
    /** The cors filter. */
    @Autowired
    private CorsFilter corsFilter;	
	
    /**
     * Overriding configure method with {@link AuthenticationManagerBuilder} as parameter, from {@link WebSecurityConfigurerAdapter}.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.authenticationProvider(customAuthenticationProvider);
	}
	
	/**
	 * Overriding configure method with {@link HttpSecurity} as parameter, from {@link WebSecurityConfigurerAdapter}.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/admin/**").hasRole("LIBRARIAN")
			.antMatchers("/security/**").hasRole("LIBRARIAN")
			.antMatchers("/user/**").hasRole("USER")
			.and()
			.formLogin().disable();
			http
			.httpBasic().disable();
			http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
					
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(corsFilter, JwtFilter.class);
		
	}	
	
	/**
	 * Bean definition of {@link BCryptPasswordEncoder}, to be used later for encoding and comparing.
	 *
	 * @return {@link BCryptPasswordEncoder}
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Authentication Provider Bean definition.
	 *
	 * @return {@link DaoAuthenticationProvider}
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		return auth;
	}
	
	/**
	 * Bean definition of UserDetailsManager Interface, which helps us to create and update users.
	 * It extends {@link UserDetailsService}
	 * @return {@link UserDetailsManager}
	 */
	@Bean
	public UserDetailsManager userDetailsManager() {
		
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		
		jdbcUserDetailsManager.setDataSource(securityDataSource);
		
		return jdbcUserDetailsManager; 
	}
	
	/**
	 * Processes an authentication request.
	 *
	 * @return the authentication manager
	 * @throws Exception the exception
	 */
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	/**
	 * Used for mapping entities.
	 *
	 * @return the model mapper
	 */
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
}



















