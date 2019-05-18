package esprima4java.cfg.builders;

import esprima4java.ast.Node;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBreakNode;

/**
 * A builder for creating control flow graphs from break statements.
 */
public class CfgBuilderForBreakStatements {

    public static Cfg build(Node statement) {
	CfgBreakNode node = new CfgBreakNode(statement);
	Cfg cfg = new Cfg(node);
	cfg.addBreakNode(node);
	return cfg;
    }

}
