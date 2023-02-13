package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, StudentRepository studentRepository, AvatarService avatarService) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.avatarService = avatarService;
    }

    @GetMapping("{id}") ///localhost:8080/Student/2
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student != null) {
            return ResponseEntity.ok(studentService.findStudent(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("ageBetween")
    public ResponseEntity<Collection<Student>> findInBetween(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        Collection<Student> findStudent = studentService.findByAgeBetween(min, max);
        return ResponseEntity.ok(Objects.requireNonNullElse(findStudent, Collections.emptyList()));
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudentId(long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            return ResponseEntity.ok(student.getFaculty());
        } else {
            return null;
        }
    }

    @GetMapping("{age}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@PathVariable int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("{id}/avatar/data")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @PostMapping
    public Student createStudent(@RequestBody Student Student) {
        return studentService.createStudent(Student);
    }

    @PostMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar (@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 250) {
            return ResponseEntity.badRequest().body("Large file size!");
        }
        avatarService.uploadCover(id, avatar);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        student = studentService.editStudent(student);
        if (student == null) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student findStudent = studentService.findStudent(id);
        if (findStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        studentService.deleteStudent(id);
        return ResponseEntity.ok(findStudent);
    }
}
