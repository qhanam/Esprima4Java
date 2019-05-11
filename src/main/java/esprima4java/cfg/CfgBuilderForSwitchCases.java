package esprima4java.cfg;

import esprima4java.ast.EmptyStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.Node;
import esprima4java.ast.SwitchCase;

/**
 * A builder for creating control flow graphs from switch cases.
 */
public class CfgBuilderForSwitchCases {

    public static Cfg build(SwitchCase statement) {
	CfgNode entryNode = CfgNode.create(EmptyStatement.create());
	Cfg cfg = new Cfg(entryNode);

	CfgNode currentNode = entryNode;

	for (Node child : statement.consequent()) {
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
