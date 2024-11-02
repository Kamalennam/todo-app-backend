package com.Todo.TodoProject.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.cors()
	        	.and()
	            .csrf().disable() 
	            .authorizeRequests()
	            .requestMatchers("/createTask/{projectId}").permitAll()
	            .requestMatchers("/createProject").permitAll()
	            .requestMatchers("/allProjects").permitAll()
	            .requestMatchers("/deleteProject/{id}").permitAll()
	            .requestMatchers(HttpMethod.PATCH,"/updateProject/**").permitAll()
	            .requestMatchers("/allTasks").permitAll()
	            .requestMatchers("/deleteTask/{id}").permitAll()
	            .requestMatchers("/updateTask/{id}").permitAll()
	            .anyRequest().authenticated(); 

	        return http.build();
	    }
}
