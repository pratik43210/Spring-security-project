package com.pratik.spring_sec_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		
		return provider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
//		//disabling csrf token
//		http.csrf(customizer->customizer.disable());
//		http.authorizeHttpRequests(request->request.anyRequest().authenticated());
//		http.formLogin(Customizer.withDefaults());
//		http.httpBasic(Customizer.withDefaults());
//		//make session stateless
//		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http
			.csrf(customizer->customizer.disable())
			.authorizeHttpRequests(request->request
					.requestMatchers("register")
					.permitAll()
					.anyRequest()
					.authenticated())
			.httpBasic(Customizer.withDefaults())
			.sessionManagement(session->
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		//will prevent looking for user details in application.properties and
//		//will allow for multiple users
////		UserDetails user=User.builder().build();
//		UserDetails user=User
//				.withDefaultPasswordEncoder()
//				.username("pratik")
//				.password("1234")
//				.roles("USER")
//				.build();
//		
//		UserDetails admin=User
//				.withDefaultPasswordEncoder()
//				.username("sonu")
//				.password("0000")
//				.roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user,admin);
//	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
}
