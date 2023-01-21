package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty") //localhost:8080/faculty
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    
    @GetMapping("{id}") ///localhost:8080/Student?id=2
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }
    
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }
    
    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty){
        faculty = facultyService.editFaculty(faculty);
        if (faculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @DeleteMapping( "{id}")
    public ResponseEntity<Faculty> deleteStudent(@PathVariable Long id){
        Faculty findFaculty = facultyService.findFaculty(id);
        if (findFaculty == null){
            return ResponseEntity.badRequest().build();
        }
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok(findFaculty);
    }

    @GetMapping("/color{color}")
    public ResponseEntity<List<Faculty>> getFacultyByColor(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }
    
}
