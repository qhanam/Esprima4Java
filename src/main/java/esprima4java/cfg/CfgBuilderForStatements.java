package esprima4java.cfg;

import esprima4java.ast.Node;

/**
 * A builder for creating control flow graphs from non-control-flow statements.
 * For example: empty statements, expression statements and return statements.
 */
public class CfgBuilderForStatements {

    public static Cfg build(Node statement) {
	CfgNode node = CfgNode.create(statement);
	Cfg cfg = new Cfg(node);
	cfg.setExitNode(node);
	return cfg;
    }

}
