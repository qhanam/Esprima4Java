package esprima4java.cfg;

import java.util.ArrayList;
import java.util.List;

import esprima4java.ast.Function;
import esprima4java.ast.Node;
import esprima4java.ast.Program;
import esprima4java.utilities.NodeVisitor;

/**
 * Generates a list of all the functions in an AST.
 */
public class FunctionVisitor implements NodeVisitor {

    private List<Function> functions;

    public FunctionVisitor() {
	this.functions = new ArrayList<Function>();
    }

    public static List<Function> getFunctions(Program program) {
	FunctionVisitor visitor = new FunctionVisitor();
	program.accept(visitor);
	return visitor.functions;
    }

    @Override
    public boolean visit(Node node) {
	switch (node.type()) {
	case FUNCTION_DECLARATION:
	case FUNCTION_EXPRESSION:
	case ARROW_FUNCTION_EXPRESSION:
	    this.functions.add((Function) node);
	default:
	}
	return true;
    }

}