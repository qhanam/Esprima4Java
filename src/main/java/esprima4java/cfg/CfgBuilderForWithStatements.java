package esprima4java.cfg;

import esprima4java.ast.Literal;
import esprima4java.ast.WithStatement;

/**
 * A builder for creating control flow graphs from return statements.
 */
public class CfgBuilderForWithStatements {

    public static Cfg build(WithStatement statement) {
	CfgNode entryNode = CfgNode.create(statement);
	Cfg cfg = new Cfg(entryNode);

	Cfg bodyCfg = statement.body().buildCfg();

	CfgEdge.create(Literal.createBoolean(true, "true"), entryNode, bodyCfg.getEntryNode());

	cfg.addAllBreakNodes(bodyCfg.getBreakNodes());
	cfg.addAllContinueNodes(bodyCfg.getContinueNodes());
	cfg.addAllReturnNodes(bodyCfg.getReturnNodes());
	cfg.addAllThrowNodes(bodyCfg.getThrowNodes());

	if (bodyCfg.getExitNode() == null)
	    return cfg;

	cfg.setExitNode(bodyCfg.getExitNode());

	return cfg;
    }

}
