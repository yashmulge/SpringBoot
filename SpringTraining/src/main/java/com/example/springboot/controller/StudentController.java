package com.example.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.model.Student;
import com.example.springboot.service.IStudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/students")
@Tag(name = "Student API", description = "API for managing students")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @Operation(summary = "Get a student by name", description = "Retrieve a student by their name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/name/{name}")
    public Optional<Student> getStudentByName(@PathVariable String name) {
        return studentService.getStudentByName(name);
    }

    @Operation(summary = "Get students sorted by age", description = "Retrieve a list of students sorted by age")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/sorted/age")
    public List<Student> getStudentsSortedByAge() {
        return studentService.getStudentsSortedByAge();
    }

    @Operation(summary = "Get students with pagination", description = "Retrieve a paginated list of students")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/paged")
    public Page<Student> getStudentsPaged(
        @RequestParam int page,
        @RequestParam int size
    ) {
        return studentService.getStudentsPaged(page, size);
    }

    @Operation(summary = "Add a new student", description = "Add a student to the database")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Student created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @Operation(summary = "Delete a student by name", description = "Delete a student by their name")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/name/{name}")
    public boolean deleteStudent(@PathVariable String name) {
        return studentService.deleteStudent(name);
    }

    @Operation(summary = "Update a student", description = "Update an existing student's details")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Student updateStudent(
        @PathVariable Long id,
        @RequestBody Student student
    ) {
        return studentService.updateStudent(id, student);
    }
}