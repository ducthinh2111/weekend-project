package org.starea;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodNamePrinter extends VoidVisitorAdapter<Void> {

    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        System.out.println("Method Name Printed: " + md.getName());
    }
}
