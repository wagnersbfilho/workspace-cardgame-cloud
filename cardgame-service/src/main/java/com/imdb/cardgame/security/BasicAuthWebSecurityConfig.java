package com.imdb.cardgame.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthWebSecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/cardgame/authentication/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic();
		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		List<UserDetails> users = new ArrayList<UserDetails>();
		
		UserDetails user1 = 
				User.withUsername("imdb")
				.password("{noop}123")
				.roles("USER")
				.build();
		UserDetails user2 = 
				User.withUsername("cinema")
				.password("{noop}123")
				.roles("USER")
				.build();

		users.add(user1);
		users.add(user2);
		return new InMemoryUserDetailsManager(users);
	}
	
    @Bean
    public AuthenticationManager authenticationManager(
    		AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	/**
	 * Método responsável por gerar manualmente o encoding do
	 * método de autorização para ser inserido no Header do request
	 * dos testes em Postman.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Generate authorization for user 'cinema'
		String encoding = Base64.getEncoder().encodeToString(("cinema" + ":" + "123").getBytes());
		String authHeader = "Basic " + encoding;
		System.out.println(authHeader);

		// Generate authorization for user 'imdb'
		String encoding2 = Base64.getEncoder().encodeToString(("imdb" + ":" + "123").getBytes());
		String authHeader2 = "Basic " + encoding2;
		System.out.println(authHeader2);
	}
}
