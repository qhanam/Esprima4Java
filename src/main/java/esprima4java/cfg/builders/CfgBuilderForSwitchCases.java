package esprima4java.cfg.builders;

import esprima4java.ast.Node;
import esprima4java.ast.SwitchCase;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEmptyNode;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from switch cases.
 */
public class CfgBuilderForSwitchCases {

    public static Cfg build(SwitchCase statement) {
	CfgEmptyNode entryNode = new CfgEmptyNode();
	Cfg cfg = new Cfg(entryNode);

	CfgNode currentNode = entryNode;

	for (Node child : statement.consequent()) {

	    // Build the CFG for the child.
	    Cfg childCfg = child.buildCfg();

	    // Create an edge between the child and the current node.
	    CfgBuilderUtils.addEdge(currentNode, childCfg.getEntryNode());

	    // Propagate control flow nodes.
	    cfg.addAllBreakNodes(childCfg.getBreakNodes());
	    cfg.addAllContinueNodes(childCfg.getContinueNodes());
	    cfg.addAllReturnNodes(childCfg.getReturnNodes());
	    cfg.addAllThrowNodes(childCfg.getThrowNodes());

	    if (childCfg.getExitNode() == null) {
		// The rest of the statements in the case are unreachable.
		return cfg;
	    }

	    currentNode = childCfg.getExitNode();
	}

	cfg.setExitNode(currentNode);

	return cfg;
    }

}
