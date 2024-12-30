package org.starea.boundary;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.starea.control.StudentService;
import org.starea.dto.Student;

import java.util.List;

@Path("/students")
public class StudentResource {

    @Inject
    StudentService studentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getStudents() {
        return studentService.getStudents();
    }
}
