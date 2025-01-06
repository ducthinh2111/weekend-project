package org.starea.boundary;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Student> createStudents(List<Student> students) {
        return studentService.createStudents(students);
    }
}
