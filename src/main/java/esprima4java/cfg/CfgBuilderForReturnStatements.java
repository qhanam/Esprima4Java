package esprima4java.cfg;

import esprima4java.ast.Node;

/**
 * A builder for creating control flow graphs from return statements.
 */
public class CfgBuilderForReturnStatements {

    public static Cfg build(Node statement) {
	CfgNode node = CfgNode.create(statement);
	Cfg cfg = new Cfg(node);
	cfg.addExitNode(node);
	cfg.addReturnNode(node);
	return cfg;
    }

}
