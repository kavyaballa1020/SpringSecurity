package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entity.Students;

public interface StudentRepository extends JpaRepository<Students, Integer>{
	
}
