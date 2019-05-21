package esprima4java.cfg.builders;

import esprima4java.ast.CallExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgCallsiteNode;
import esprima4java.utilities.NodeVisitor;

/**
 * A builder for creating control flow graphs for call sites contained in
 * expressions.
 */
public class CfgBuilderForCallSites implements NodeVisitor {

    /**
     * Returns an ordered list of function calls within a statement. Function calls
     * are sorted in topological order, so that the call sites that have
     * dependencies occur in the list before their dependencies, and can therefore
     * be evaluated first.
     */
    public static Cfg build(Node expression) {
	CfgBuilderForCallSites visitor = new CfgBuilderForCallSites();
	expression.accept(visitor);
	if (visitor.entry == null) {
	    return null;
	} else {
	    Cfg cfg = new Cfg(visitor.entry);
	    cfg.setExitNode(visitor.exit);
	    return cfg;
	}
    }

    private CfgCallsiteNode entry;
    private CfgCallsiteNode exit;

    private CfgBuilderForCallSites() {
	this.entry = null;
	this.exit = null;
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
	    CfgCallsiteNode tmp = new CfgCallsiteNode((CallExpression) node);
	    if (entry == null) {
		entry = tmp;
		exit = tmp;
	    } else {
		CfgBuilderUtils.addEdge(exit, tmp);
		exit = tmp;
	    }
	}
    }

}
