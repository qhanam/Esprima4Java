package esprima4java.cfg.builders;

import esprima4java.ast.Node;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEvaluateNode;

/**
 * A builder for creating control flow graphs from non-control-flow statements.
 * For example: empty statements, expression statements and return statements.
 */
public class CfgBuilderForStatements {

    public static Cfg build(Node statement) {
	CfgEvaluateNode node = new CfgEvaluateNode(statement);
	Cfg cfg = CfgBuilderForCallSites.build(statement);
	if (cfg == null) {
	    // There were no call sites in the expression.
	    cfg = new Cfg(node);
	    cfg.setExitNode(node);
	} else {
	    // Change the exit node to this expression.
	    CfgBuilderUtils.addEdge(cfg.getExitNode(), node);
	    cfg.setExitNode(node);
	}
	return cfg;
    }

}
