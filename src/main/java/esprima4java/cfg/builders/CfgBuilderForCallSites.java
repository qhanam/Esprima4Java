package esprima4java.cfg.builders;

import esprima4java.ast.CallExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgCallsiteNode;
import esprima4java.cfg.CfgNode;
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
     * be evaluated first. The expression itself is always the exit node.
     * 
     * @param expression
     *            The expression which may contain call sites.
     * @param evaluator
     *            The expression wrapped as a CfgNode.
     */
    public static Cfg build(Node expression, CfgNode evaluator) {
	Cfg cfg;
	CfgBuilderForCallSites visitor = new CfgBuilderForCallSites();
	expression.accept(visitor);
	if (visitor.entry == null) {
	    // There are no call sites in the expression.
	    cfg = new Cfg(evaluator);
	} else {
	    // Add the expression to the end of the call site graph.
	    cfg = new Cfg(visitor.entry);
	    CfgBuilderUtils.addEdge(visitor.exit, evaluator);
	}
	cfg.setExitNode(evaluator);
	return cfg;
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
