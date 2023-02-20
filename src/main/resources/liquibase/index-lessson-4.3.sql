-- changeset one
create index index_student_name on student (name);

-- changeset two
create index index_faculty_name_color on faculty (name, color);
