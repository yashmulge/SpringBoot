package com.example.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import com.example.springboot.controller.StudentController;
import com.example.springboot.model.Student;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentService studentService;

    @MockBean
    private CurrencyConversionService currencyConversionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudentsSortedByAge() throws Exception {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math", 0), new Student("Bob", 22, "Physics", 0));
        given(studentService.getStudentsSortedByAge()).willReturn(students);

        mockMvc.perform(get("/api/students/sorted")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice")).andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    void testGetAllStudentsSortedByAgeDesc() throws Exception {
        List<Student> students = Arrays.asList(new Student("Bob", 22, "Physics", 0), new Student("Alice", 20, "Math", 0));
        given(studentService.getStudentsSortedByAgeDesc()).willReturn(students);

        mockMvc.perform(get("/api/students/sorted/desc")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bob")).andExpect(jsonPath("$[1].name").value("Alice"));
    }

    @Test
    void testGetStudents() throws Exception {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math", 0), new Student("Bob", 22, "Physics", 0));
        given(studentService.getStudents()).willReturn(students);

        mockMvc.perform(get("/api/students")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    void testGetStudentByName() throws Exception {
        Student student = new Student("Alice", 20, "Math", 0);
        given(studentService.getStudentByName("Alice")).willReturn(Optional.of(student));

        mockMvc.perform(get("/api/students/Alice")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    void testGetStudentByName_NotFound() throws Exception {
        given(studentService.getStudentByName("Alice")).willReturn(Optional.empty());

        mockMvc.perform(get("/api/students/Alice")).andExpect(status().isNotFound());
    }

    @Test
    void testGetStudentsPaged() throws Exception {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math", 0), new Student("Bob", 22, "Physics", 0));
        Page<Student> pagedStudents = new PageImpl<>(students);
        given(studentService.getStudentsPaged(0, 2)).willReturn(pagedStudents);

        mockMvc.perform(get("/api/students/paged?page=0&size=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[1].name").value("Bob"));
    }

    @Test
    void testGetStudentsSortedByAgePaged() throws Exception {
        List<Student> students = Arrays.asList(new Student("Alice", 20, "Math", 0), new Student("Bob", 22, "Physics", 0));
        Page<Student> pagedStudents = new PageImpl<>(students);
        given(studentService.getStudentsSortedByAgePaged(0, 2)).willReturn(pagedStudents);

        mockMvc.perform(get("/api/students/sorted/paged?page=0&size=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Alice"))
                .andExpect(jsonPath("$.content[1].name").value("Bob"));
    }

    @Test
    void testGetStudentsSortedByAgeDescPaged() throws Exception {
        List<Student> students = Arrays.asList(new Student("Bob", 22, "Physics", 0), new Student("Alice", 20, "Math", 0));
        Page<Student> pagedStudents = new PageImpl<>(students);
        given(studentService.getStudentsSortedByAgeDescPaged(0, 2)).willReturn(pagedStudents);

        mockMvc.perform(get("/api/students/sorted/desc/paged?page=0&size=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Bob"))
                .andExpect(jsonPath("$.content[1].name").value("Alice"));
    }

    @Test
    void testAddStudent() throws Exception {
        Student student = new Student("Alice", 20, "Math", 0);
        given(studentService.addStudent(any(Student.class))).willReturn(student);

        mockMvc.perform(post("/api/students").contentType("application/json")
                .content("{\"name\":\"Alice\",\"age\":20,\"course\":\"Math\",\"fees\":0}")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice")).andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.course").value("Math")).andExpect(jsonPath("$.fees").value(0.0));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student("John", 21, "Science", 10000);
        given(studentService.updateStudent(1L, student)).willReturn(student);
        mockMvc.perform(put("/api/students/1").contentType("application/json")
                .content("{\"name\":\"John\",\"age\":21,\"course\":\"Science\",\"fees\":10000}")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(21)).andExpect(jsonPath("$.course").value("Science"))
                .andExpect(jsonPath("$.fees").value(10000.0));
    }

    @Test
    void testDeleteStudent() throws Exception {
        given(studentService.deleteStudent("Alice")).willReturn(true);

        mockMvc.perform(delete("/api/students/Alice")).andExpect(status().isOk())
                .andExpect(content().string("Student with name Alice deleted."));
    }

    @Test
    void testDeleteStudent_NotFound() throws Exception {
        given(studentService.deleteStudent("Alice")).willReturn(false);

        mockMvc.perform(delete("/api/students/Alice")).andExpect(status().isNotFound())
                .andExpect(content().string("Student with name Alice not found."));
    }

    @Test
    void testConvertFees() throws Exception {
        double feesInUsd = 1500;
        double feesInInr = 110000; // example conversion rate result
        given(currencyConversionService.convertUsdToInr(feesInUsd)).willReturn(feesInInr);

        mockMvc.perform(get("/api/students/convertFees/1500")).andExpect(status().isOk())
                .andExpect(content().string("110000.0"));
    }

    @Test
    void testSetFees() throws Exception {
        long studentId = 1L;
        double feesInUsd = 1500;
        double feesInInr = 110000; // example conversion rate result
        Student student = new Student("John", 21, "Science", feesInInr);
        given(currencyConversionService.convertUsdToInr(feesInUsd)).willReturn(feesInInr);
        given(studentService.getStudentById(studentId)).willReturn(Optional.of(student));
        given(studentService.updateStudent(studentId, student)).willReturn(student);

        mockMvc.perform(post("/api/students/setFees/1/1500")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.course").value("Science"))
                .andExpect(jsonPath("$.fees").value(110000.0));
    }
}