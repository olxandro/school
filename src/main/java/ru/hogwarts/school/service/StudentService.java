package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
           return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student){
        if(findStudent(student.getId()) == null){
            return null; }
        return studentRepository.save(student);

    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return  studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudent(Long studentId) {
        Student student = studentRepository.findStudentById(studentId);
        return facultyRepository.findFacultyByStudents(student);
    }
}
