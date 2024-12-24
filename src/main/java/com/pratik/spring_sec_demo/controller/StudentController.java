package com.pratik.spring_sec_demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pratik.spring_sec_demo.model.Student;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class StudentController {

	List<Student> students=new ArrayList<>(List.of(
			new Student(1,"Pratik","Java"),
			new Student(2,"Sonu","React")
			));
	
	@GetMapping("csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest req) {
		return (CsrfToken)req.getAttribute("_csrf");
	}
	
	@GetMapping("students")
	public List<Student> getStudents(){
		return students;
	}
	
	@PostMapping("students")
	public List<Student> postMethodName(@RequestBody Student student) {
		//TODO: process POST request
		students.add(student);
		return students;
	}
	
}
