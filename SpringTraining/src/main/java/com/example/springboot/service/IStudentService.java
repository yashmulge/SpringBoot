package com.example.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.springboot.model.Student;

public interface IStudentService {
	List<Student> getStudents();

	Optional<Student> getStudentByName(String name);

	List<Student> getStudentsSortedByAge();

	List<Student> getStudentsSortedByAgeDesc();

	Page<Student> getStudentsPaged(int page, int size);

	Page<Student> getStudentsSortedByAgePaged(int page, int size);

	Page<Student> getStudentsSortedByAgeDescPaged(int page, int size);

	Student addStudent(Student student);

	boolean deleteStudent(String name);

	Student updateStudent(Long id, Student student);

	Optional<Student> getStudentById(Long id);

	Optional<Student> getStudentById(long studentId);


}
