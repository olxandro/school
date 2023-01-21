package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
        Student student = studentService.findStudent(id);
        if (student != null) {
            return ResponseEntity.ok(studentService.findStudent(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student Student){
        return studentService.createStudent(Student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student){
        student = studentService.editStudent(student);
        if (student == null){
            ResponseEntity.badRequest().build();
        }
       return ResponseEntity.ok(student);
    }
    @DeleteMapping( "{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
        Student findStudent = studentService.findStudent(id);
        if (findStudent == null){
            return ResponseEntity.badRequest().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok(findStudent);
    }

    @GetMapping("/age{age}")
    public ResponseEntity<List<Student>> getStudentByAge(@PathVariable int age){
        return ResponseEntity.ok(studentService.getStudentByAge(age));
    }
    
}
