package org.starea.control;

import jakarta.enterprise.context.Dependent;
import org.starea.dto.Student;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Dependent
public class StudentService {

    public List<Student> getStudents() {
        return List.of(
                new Student("1", "starea", 25),
                new Student("2", "starea", 26)
        );
    }
    
    public List<Student> createStudents(List<Student> students) {
        return List.of(
                new Student(UUID.randomUUID().toString(), "starea", 25),
                new Student(UUID.randomUUID().toString(), "starea", 26)
        );
    }
}
