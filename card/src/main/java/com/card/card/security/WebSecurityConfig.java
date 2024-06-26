package com.card.card.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
	    .authorizeRequests()
	        .antMatchers("/user/login").permitAll()
	        .antMatchers("/user/**").authenticated()
	        .antMatchers("/**").permitAll()
	    .and()
	    .formLogin()
	        .loginPage("/user")  // Specify the login page URL
	        .permitAll()
	    .and()
	    .logout()
	        .logoutUrl("/logout")  // Specify the logout URL
	        .logoutSuccessUrl("/")
	        .permitAll();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false).ignoring()
				.antMatchers("/images/**", "/js/**", "/css/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsService)
         .passwordEncoder(bCryptPasswordEncoder);
	}
}

