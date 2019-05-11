package esprima4java.cfg;

import esprima4java.ast.BlockStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.Node;

/**
 * A builder for creating control flow graphs from return statements.
 */
public class CfgBuilderForBlockStatements {

    public static Cfg build(BlockStatement statement) {
	CfgNode entryNode = CfgNode.create(EmptyStatement.create());
	Cfg cfg = new Cfg(entryNode);

	CfgNode currentNode = entryNode;

	for (Node child : statement.body()) {
	    Cfg childCfg = child.buildCfg();

	    CfgEdge.create(Literal.createBoolean(true, "true"), currentNode,
		    childCfg.getEntryNode());

	    cfg.addAllBreakNodes(childCfg.getBreakNodes());
	    cfg.addAllContinueNodes(childCfg.getContinueNodes());
	    cfg.addAllReturnNodes(childCfg.getReturnNodes());
	    cfg.addAllThrowNodes(childCfg.getThrowNodes());

	    if (childCfg.getExitNode() == null)
		return cfg;

	    currentNode = childCfg.getExitNode();
	}

	cfg.setExitNode(currentNode);

	return cfg;
    }

}
