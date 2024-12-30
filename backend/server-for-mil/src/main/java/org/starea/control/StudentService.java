package org.starea.control;

import jakarta.enterprise.context.Dependent;
import org.starea.dto.Student;

import java.util.List;

@Dependent
public class StudentService {

    public List<Student> getStudents() {
        return List.of(
                new Student("1", "starea", 25),
                new Student("2", "starea", 26)
        );
    }
}
