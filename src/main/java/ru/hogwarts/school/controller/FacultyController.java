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
    public Faculty getFaculty(@PathVariable Long id){
        return facultyService.findFaculty(id);
    }
    
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }
    
    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty){
        return facultyService.editFaculty(faculty);
    }
    @DeleteMapping( "{id}")
    public ResponseEntity<Faculty> deleteStudent(@PathVariable Long id){
        Faculty findFaculty = facultyService.findFaculty(id);
        if (findFaculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.deleteFaculty(id));
    }

    @GetMapping("/color{color}")
    public List<Faculty> getFacultyByColor(@PathVariable String color) {
        return facultyService.getFacultyByColor(color);
    }
    
}
