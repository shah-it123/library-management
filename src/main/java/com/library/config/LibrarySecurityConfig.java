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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.library.filter.CorsFilter;
import com.library.filter.JwtFilter;
import com.library.service.UserService;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	private DataSource securityDataSource;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private JwtFilter jwtFilter;
    
    @Autowired
    private CorsFilter corsFilter;	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.authenticationProvider(customAuthenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/admin/**").hasRole("LIBRARIAN")
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
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		return auth;
	}
	
	
	@Bean
	public UserDetailsManager userDetailsManager() {
		
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		
		jdbcUserDetailsManager.setDataSource(securityDataSource);
		
		return jdbcUserDetailsManager; 
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
}



















