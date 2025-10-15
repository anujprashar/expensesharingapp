package com.example.expense.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		/*
		 * http .authorizeHttpRequests(auth -> auth
		 * .requestMatchers("/h2-console/**").permitAll()
		 * .requestMatchers("/**").permitAll() ) .csrf(csrf ->
		 * csrf.ignoringRequestMatchers("/h2-console/**")) .headers(headers ->
		 * headers.frameOptions(frame -> frame.disable()))
		 * .oauth2Login(Customizer.withDefaults());
		 */
		 http .authorizeHttpRequests(auth -> auth
				  .requestMatchers("/h2-console/**").permitAll()
				  .requestMatchers("/**").permitAll() ) .csrf().disable() .headers(headers ->
				  headers.frameOptions(frame -> frame.disable()))
				  .oauth2Login(Customizer.withDefaults());
    	
    	/*http
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());*/
        return http.build();
    }
}
