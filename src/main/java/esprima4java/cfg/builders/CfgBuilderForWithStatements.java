package esprima4java.cfg.builders;

import esprima4java.ast.WithStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEmptyNode;

/**
 * A builder for creating control flow graphs from With statements.
 * 
 * TODO: The behaviour of the With statement is currently ignored.
 */
public class CfgBuilderForWithStatements {

    public static Cfg build(WithStatement statement) {
	CfgEmptyNode entryNode = new CfgEmptyNode();
	Cfg cfg = new Cfg(entryNode);

	Cfg bodyCfg = statement.body().buildCfg();

	CfgBuilderUtils.addEdge(entryNode, bodyCfg.getEntryNode());

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
