package esprima4java.cfg.builders;

import esprima4java.ast.ForStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBreakNode;
import esprima4java.cfg.CfgContinueNode;
import esprima4java.cfg.CfgEmptyNode;
import esprima4java.cfg.CfgEvaluateNode;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForForStatements {

    public static Cfg build(ForStatement statement) {
	CfgNode entryNode = new CfgEvaluateNode(statement.init());
	CfgNode loopNode = new CfgEmptyNode();
	CfgNode exitNode = new CfgEmptyNode();
	CfgNode updateNode = new CfgEvaluateNode(statement.update());

	// Edges from init to loop and update to loop.
	CfgBuilderUtils.addEdge(entryNode, loopNode);
	CfgBuilderUtils.addEdge(updateNode, loopNode);

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Handle the consequent.
	Cfg bodyCfg = statement.body().buildCfg();

	// Set up the true and false branch edges.
	CfgBuilderUtils.addTrueAssertion(statement.test(), loopNode, bodyCfg.getEntryNode());
	CfgBuilderUtils.addFalseAssertion(statement.test(), loopNode, bodyCfg.getEntryNode());

	// Add all the exit points
	cfg.addAllReturnNodes(bodyCfg.getReturnNodes());
	cfg.addAllThrowNodes(bodyCfg.getThrowNodes());

	for (CfgBreakNode node : bodyCfg.getBreakNodes()) {
	    // Break nodes have edges to the exit node.
	    CfgBuilderUtils.addEdge(node, exitNode);
	}

	for (CfgContinueNode node : bodyCfg.getContinueNodes()) {
	    // Continue nodes have edges to the update node.
	    CfgBuilderUtils.addEdge(node, updateNode);
	}

	if (bodyCfg.getExitNode() != null) {
	    // Body has an edge to the update node.
	    CfgBuilderUtils.addEdge(bodyCfg.getExitNode(), updateNode);
	}

	return cfg;
    }

}
