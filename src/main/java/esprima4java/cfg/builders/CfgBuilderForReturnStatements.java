package esprima4java.cfg.builders;

import esprima4java.ast.ReturnStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgReturnNode;

/**
 * A builder for creating control flow graphs from return statements.
 */
public class CfgBuilderForReturnStatements {

    public static Cfg build(ReturnStatement statement) {
	CfgReturnNode node = new CfgReturnNode(statement);
	Cfg cfg = CfgBuilderForCallSites.build(statement);
	if (cfg == null) {
	    // There were no call sites in the return expression.
	    cfg = new Cfg(node);
	} else {
	    // Remove the exit node.
	    CfgBuilderUtils.addEdge(cfg.getExitNode(), node);
	    cfg.setExitNode(null);
	}
	cfg.addReturnNode(node);
	return cfg;
    }

}
