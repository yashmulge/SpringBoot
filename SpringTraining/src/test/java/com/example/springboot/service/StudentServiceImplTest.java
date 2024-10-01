package com.example.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.springboot.model.Student;
import com.example.springboot.repository.StudentRepository;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudents() {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math"), new Student("Bob", 22, "Physics"));
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getStudents();
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
    }

    @Test
    void testGetStudentByName() {
        Student student = new Student("Alice", 20, "Math");
        when(studentRepository.findByName("Alice")).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentByName("Alice");
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void testGetStudentsPaged() {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math"), new Student("Bob", 22, "Physics"));
        Page<Student> pagedStudents = new PageImpl<>(students);
        when(studentRepository.findAll(PageRequest.of(0, 2))).thenReturn(pagedStudents);

        Page<Student> result = studentService.getStudentsPaged(0, 2);
        assertEquals(2, result.getContent().size());
        assertEquals("Alice", result.getContent().get(0).getName());
        assertEquals("Bob", result.getContent().get(1).getName());
    }

    @Test
    void testGetStudentsSortedByAge() {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math"), new Student("Bob", 22, "Physics"));
        when(studentRepository.findAllByOrderByAgeAsc()).thenReturn(students);

        List<Student> result = studentService.getStudentsSortedByAge();
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
    }

    @Test
    void testGetStudentsSortedByAgeDesc() {
        List<Student> students = Arrays.asList(new Student("Bob", 22, "Physics"), new Student("Alice", 20, "Math"));
        when(studentRepository.findAllByOrderByAgeDesc()).thenReturn(students);

        List<Student> result = studentService.getStudentsSortedByAgeDesc();
        assertEquals(2, result.size());
        assertEquals("Bob", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());
    }

    @Test
    void testGetStudentsSortedByAgePaged() {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math"), new Student("Bob", 22, "Physics"));
        Page<Student> pagedStudents = new PageImpl<>(students);
        when(studentRepository.findAllByOrderByAgeAsc(PageRequest.of(0, 2))).thenReturn(pagedStudents);

        Page<Student> result = studentService.getStudentsSortedByAgePaged(0, 2);
        assertEquals(2, result.getContent().size());
        assertEquals("Alice", result.getContent().get(0).getName());
        assertEquals("Bob", result.getContent().get(1).getName());
    }

    @Test
    void testGetStudentsSortedByAgeDescPaged() {
        List<Student> students = Arrays.asList(new Student("Bob", 22, "Physics"), new Student("Alice", 20, "Math"));
        Page<Student> pagedStudents = new PageImpl<>(students);
        when(studentRepository.findAllByOrderByAgeDesc(PageRequest.of(0, 2))).thenReturn(pagedStudents);

        Page<Student> result = studentService.getStudentsSortedByAgeDescPaged(0, 2);
        assertEquals(2, result.getContent().size());
        assertEquals("Bob", result.getContent().get(0).getName());
        assertEquals("Alice", result.getContent().get(1).getName());
    }

    @Test
    void testAddStudent() {
        Student student = new Student("Alice", 20, "Math");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.addStudent(student);
        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals(20, result.getAge());
        assertEquals("Math", result.getCourse());
    }

    @Test
    void testUpdateStudent() {
        long studentId = 1L;
        Student existingStudent = new Student("John", 20, "Math");
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

        Student updatedStudent = new Student("John", 21, "Science");
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        Student result = studentService.updateStudent(studentId, updatedStudent);

        assertNotNull(result);
        assertEquals(21, result.getAge());
        assertEquals("Science", result.getCourse());
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student("Alice", 20, "Math");
        when(studentRepository.findByName("Alice")).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        boolean result = studentService.deleteStudent("Alice");
        assertTrue(result);
    }
}