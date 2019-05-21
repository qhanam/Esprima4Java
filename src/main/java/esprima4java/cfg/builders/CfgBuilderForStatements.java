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
	return CfgBuilderForCallSites.build(statement, new CfgEvaluateNode(statement));
    }

}
