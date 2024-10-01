package com.example.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByName(String name);

	// Methods for sorted results without pagination
	List<Student> findAllByOrderByAgeAsc();

	List<Student> findAllByOrderByAgeDesc();

	// Methods for pagination with optional sorting
	@Override
	Page<Student> findAll(Pageable pageable); // Paginated results without sorting

	Page<Student> findAllByOrderByAgeAsc(Pageable pageable); // Sorted by age ascending and paginated

	Page<Student> findAllByOrderByAgeDesc(Pageable pageable); // Sorted by age descending and paginated
}
