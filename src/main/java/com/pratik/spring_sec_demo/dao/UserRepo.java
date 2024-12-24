package com.pratik.spring_sec_demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pratik.spring_sec_demo.model.User;
import java.util.List;


public interface UserRepo extends JpaRepository<User, Integer>{
	User findByUsername(String username);
}
