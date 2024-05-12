import static com.github.javaparser.StaticJavaParser.*;
import com.github.javaparser.ast.CompilationUnit;

public class App {
    public static void main(String[] args) {
        CompilationUnit cu = parse("class X{}");
        System.out.println("Hi there!" + cu);
    }
}

