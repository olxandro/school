package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    public List<Student> getStudentByAge(int age){
        return studentRepository.findAll().stream().filter(e -> e.getAge() == age).toList();
    }
}
