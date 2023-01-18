package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}") ///localhost:8080/Student/2
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        if (studentService.findStudent(id) != null) {
            return ResponseEntity.ok(studentService.findStudent(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student Student){
        return studentService.createStudent(Student);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student Student){
        return studentService.editStudent(Student);
    }
    @DeleteMapping( "{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
        Student findStudent = studentService.findStudent(id);
        if (findStudent == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("/age{age}")
    public List<Student> getStudentByAge(@PathVariable int age){
        return studentService.getStudentByAge(age);
    }
    
}
