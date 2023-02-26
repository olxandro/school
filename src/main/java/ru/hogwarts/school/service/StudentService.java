package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    }

    public int getNumberOfAllStudents() {
        return studentRepository.getNumberOfAllStudents();
    }

    public int getAverageAge() {
        logger.debug("Method getAverageAge was called");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getFiveLastStudents() {
        logger.debug("Method getFiveLastStudents was called");
        return studentRepository.getFiveLastStudents();
    }

    public Student createStudent(Student student) {
        logger.debug("Method createStudent was called");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.debug("Method findStudent was called");
           return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student){
        logger.warn("Method editStudent was called");
        if(findStudent(student.getId()) == null){
            return null; }
        return studentRepository.save(student);

    }

    public void deleteStudent(Long id){
        logger.warn("Method deleteStudent was called");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.warn("Method getAllStudents was called");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        logger.warn("Method findByAge was called");
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.warn("Method findByAgeBetween was called");
        return  studentRepository.findByAgeBetween(min, max);
    }
}
