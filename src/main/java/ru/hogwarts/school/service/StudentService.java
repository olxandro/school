package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long count = 0L;

    public Student createStudent(Student student) {
        student.setId(count++);
        students.put(student.getId(), student);
        return student;
    }

    public Student findStudent(Long count) {
           return students.get(count);
    }

    public Student editStudent(Student Student){
        students.put(Student.getId(), Student);
        return Student;
    }

    public Student deleteStudent(Long id){
        return students.remove(id);

    }
    public List<Student> getStudentByAge(int age){
        return students.values().stream().filter(e -> e.getAge() == age).toList();
    }
}
