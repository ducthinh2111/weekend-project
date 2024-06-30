package org.starea;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class GenerateMockVisitor extends VoidVisitorAdapter<Void> {

    public void visit(FieldDeclaration md, Void arg) {
        super.visit(md, arg);
        List<VariableDeclarator> vars = md.getVariables();
        for (VariableDeclarator var : vars) {
            System.out.println(var.getName());
        }
    }
}
