package esprima4java.cfg.builders;

import esprima4java.ast.BlockStatement;
import esprima4java.ast.Node;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBlockScopeEntryNode;
import esprima4java.cfg.CfgBlockScopeExitNode;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from return statements.
 */
public class CfgBuilderForBlockStatements {

    public static Cfg build(BlockStatement statement) {
	CfgBlockScopeEntryNode entryNode = new CfgBlockScopeEntryNode();
	Cfg cfg = new Cfg(entryNode);

	CfgNode currentNode = entryNode;

	for (Node child : statement.body()) {

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
		// The rest of the statements in the body are unreachable.
		return cfg;
	    }

	    currentNode = childCfg.getExitNode();
	}

	CfgBlockScopeExitNode exitNode = new CfgBlockScopeExitNode();
	CfgBuilderUtils.addEdge(currentNode, exitNode);
	cfg.setExitNode(exitNode);

	return cfg;
    }

}
