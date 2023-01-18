package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long count = 0L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(count++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty findFaculty(Long count) {
        return faculties.get(count);
    }

    public Faculty editFaculty(Faculty faculty){
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id){
        return faculties.remove(id);

    }
    public List<Faculty> getFacultyByColor(String color){
        return faculties.values().stream().filter(e -> e.getColor().equals(color)).toList();
    }

}
