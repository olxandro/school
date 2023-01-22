package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByNameOrColorIgnoreCase(String name, String color);

    Faculty findFacultyByNameIgnoreCase(String name);
}
