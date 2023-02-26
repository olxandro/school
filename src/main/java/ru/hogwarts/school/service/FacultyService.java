package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Method createFaculty was called");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.debug("Method findFaculty was called");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Method editFaculty was called");
        if (findFaculty(faculty.getId()) == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.debug("Method deleteFaculty was called");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.debug("Method getAllFaculties was called");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByColor(String color) {
        logger.debug("Method getFacultiesByColor was called");
        return facultyRepository.findByColorEquals(color);
    }


    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.debug("Method findFacultyByNameOrColor was called");
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }
}
