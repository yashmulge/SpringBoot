package com.example.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.springboot.model.Student;
import com.example.springboot.repository.StudentRepository;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<Student> getStudentsSortedByAge() {
        return studentRepository.findAllByOrderByAgeAsc();
    }

    @Override
    public List<Student> getStudentsSortedByAgeDesc() {
        return studentRepository.findAllByOrderByAgeDesc();
    }

    @Override
    public Page<Student> getStudentsPaged(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Student> getStudentsSortedByAgePaged(int page, int size) {
        return studentRepository.findAllByOrderByAgeAsc(PageRequest.of(page, size));
    }

    @Override
    public Page<Student> getStudentsSortedByAgeDescPaged(int page, int size) {
        return studentRepository.findAllByOrderByAgeDesc(PageRequest.of(page, size));
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        if (studentRepository.existsById(id)) {
            student.setId(id);
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    @Override
    public boolean deleteStudent(String name) {
        Optional<Student> studentOpt = studentRepository.findByName(name);
        if (studentOpt.isPresent()) {
            studentRepository.delete(studentOpt.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

	@Override
	public Optional<Student> getStudentById(long studentId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}