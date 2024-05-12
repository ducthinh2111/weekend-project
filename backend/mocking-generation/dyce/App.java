import java.io.File;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import static com.github.javaparser.StaticJavaParser.*;

public class App {
    public static void main(String[] args) {
        if (args.length < 1)
            throw new RuntimeException("file name must be provided!");
        try {
            new App().gen(args[0]);
        } catch (Exception e) {
            throw new RuntimeException("__[xX]: ", e);
        }
    }

    public void gen(String file) throws Exception {
        String className = toClassName(file);
        System.out.println("class: " + className);
        CompilationUnit cu = parse(new File(file));
        System.out.println(cu);
        System.out.println("---- ---- ---- ---- ---- ---- ---- ");
        ClassOrInterfaceDeclaration clazz = cu.getClassByName(className).get();
        System.out.println(clazz.getName());
    }

    private String toClassName(String filename) {
        int idx = filename.indexOf('.');
        return filename.substring(0, idx);
    }
}

