package org.example;

public class StatementService {

    public static void run(Statement statement) {
       Resource resource = new Resource("Student", "localhost:8080/students");
       if (statement.getResource().equalsIgnoreCase(resource.getName())) {
           if (statement.getStatementType() == StatementType.SELECT) {
               resource.call(HttpMethod.GET);
           }
       }
    }
}
