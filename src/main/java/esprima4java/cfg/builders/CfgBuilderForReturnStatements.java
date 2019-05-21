package esprima4java.cfg.builders;

import esprima4java.ast.ReturnStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgReturnNode;

/**
 * A builder for creating control flow graphs from return statements.
 */
public class CfgBuilderForReturnStatements {

    public static Cfg build(ReturnStatement statement) {
	CfgReturnNode returnNode = new CfgReturnNode(statement);
	Cfg cfg = CfgBuilderForCallSites.build(statement, returnNode);
	cfg.addReturnNode(returnNode);
	cfg.clearExitNodes();
	return cfg;
    }

}
