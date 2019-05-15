package esprima4java.cfg.builders;

import esprima4java.ast.Node;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from continue statements.
 */
public class CfgBuilderForContinueStatements {

    public static Cfg build(Node statement) {
	CfgNode node = CfgNode.create(statement);
	Cfg cfg = new Cfg(node);
	cfg.addContinueNode(node);
	return cfg;
    }

}
