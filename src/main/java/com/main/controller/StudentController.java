package com.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.main.entity.Students;
import com.main.repository.StudentRepository;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentRepository repo;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students")
    public List<Students> getAllStudents() {
        return repo.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/students/{id}")
    public Students getStudent(@PathVariable int id) {
        return repo.findById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/students/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createStudent(@RequestBody Students student) {
        repo.save(student);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/students/update/{id}")
	public Students updateStudent(@PathVariable int id, @RequestBody Students updatedStudent) {
	    Students existingStudent = repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
	    
	    existingStudent.setPassword(updatedStudent.getPassword());
	    existingStudent.setRole(updatedStudent.getRole());
	    existingStudent.setUsername(updatedStudent.getUsername());
	    
	    repo.save(existingStudent);
	    
	    return existingStudent;
	}
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/students/delete/{id}")
	public void StudentDelete(@PathVariable int id) {
		Students std=repo.findById(id).get();
		repo.delete(std);
	}
}
