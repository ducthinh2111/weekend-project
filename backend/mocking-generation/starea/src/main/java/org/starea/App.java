package org.starea;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Hello world!
 */
public class App {

    private static final String FILE_PATH = "src/main/resources/AddressService.txt";

    public static void main(String[] args) throws Exception {

        CompilationUnit cu = StaticJavaParser
                .parse(Files.newInputStream(Paths.get(FILE_PATH)));
        VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter();
        methodNameVisitor.visit(cu, null);
    }
}
