package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("faculty") //localhost:8080/faculty
public class FacultyController {
    @Autowired
    public final FacultyService facultyService;
    private final FacultyRepository facultyRepository;

    public FacultyController(FacultyService facultyService, FacultyRepository facultyRepository) {
        this.facultyService = facultyService;
        this.facultyRepository = facultyRepository;
    }
    
    @GetMapping("{id}") ///localhost:8080/Student?id=2
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @GetMapping("color")
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("nameAndColor")
    public ResponseEntity<Collection<Faculty>> findByNameOrColor(@RequestParam (required = false)String name, @RequestParam(required = false)String color) {
        Collection<Faculty> findFaculty = facultyService.findByNameOrColor(name, color);
        return ResponseEntity.ok(findFaculty);
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> findStudentsByFacultyId(@PathVariable Long id) {
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty != null) {
            return ResponseEntity.ok(faculty.collectionsStudents());
        }
        return null;
    }

    @GetMapping("names/longest")
    public ResponseEntity<String> longestNames() {
        return ResponseEntity.ok(facultyService.longestNames());
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
}
