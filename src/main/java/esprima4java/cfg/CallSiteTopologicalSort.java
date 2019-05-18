package esprima4java.cfg;

import java.util.ArrayList;
import java.util.List;

import esprima4java.ast.CallExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.utilities.NodeVisitor;

/**
 * A utility for creating an ordered list of function calls within a statement.
 */
public class CallSiteTopologicalSort implements NodeVisitor {

    /**
     * Returns an ordered list of function calls within a statement. Function calls
     * are sorted in topological order, so that the call sites that have
     * dependencies occur in the list before their dependencies, and can therefore
     * be evaluated first.
     */
    public static CallExpression[] topSort(Node expression) {
	CallSiteTopologicalSort visitor = new CallSiteTopologicalSort();
	expression.accept(visitor);
	return (CallExpression[]) visitor.callSites.toArray();
    }

    private List<CallExpression> callSites;

    private CallSiteTopologicalSort() {
	callSites = new ArrayList<>();
    }

    @Override
    public boolean visit(Node node) {
	switch (node.type()) {
	case FUNCTION_EXPRESSION:
	case ARROW_FUNCTION_EXPRESSION:
	    // Do not visit function declarations.
	    return false;
	default:
	    // Topological sort the node's children.
	    return true;
	}
    }

    @Override
    public void postVisit(Node node) {
	if (node.type() == NodeType.CALL_EXPRESSION) {
	    callSites.add((CallExpression) node);
	}
    }

}
