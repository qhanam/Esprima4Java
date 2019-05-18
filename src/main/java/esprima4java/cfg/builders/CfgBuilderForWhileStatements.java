package esprima4java.cfg.builders;

import esprima4java.ast.WhileStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBreakNode;
import esprima4java.cfg.CfgContinueNode;
import esprima4java.cfg.CfgEmptyNode;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForWhileStatements {

    public static Cfg build(WhileStatement statement) {
	CfgEmptyNode entryNode = new CfgEmptyNode();
	CfgEmptyNode exitNode = new CfgEmptyNode();

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Handle the consequent.
	Cfg bodyCfg = statement.body().buildCfg();

	// Set up the true and false branch edges.
	CfgBuilderUtils.addTrueAssertion(statement.test(), entryNode, bodyCfg.getEntryNode());
	CfgBuilderUtils.addFalseAssertion(statement.test(), entryNode, exitNode);

	// Add all the exit points
	cfg.addAllReturnNodes(bodyCfg.getReturnNodes());
	cfg.addAllThrowNodes(bodyCfg.getThrowNodes());

	for (CfgBreakNode node : bodyCfg.getBreakNodes()) {
	    // Break nodes have edges to the exit node.
	    CfgBuilderUtils.addEdge(node, exitNode);
	}

	for (CfgContinueNode node : bodyCfg.getContinueNodes()) {
	    // Continue nodes have edges to the loop entry.
	    CfgBuilderUtils.addEdge(node, entryNode);
	}

	if (bodyCfg.getExitNode() != null) {
	    // Body has an edge back to the start of the loop.
	    CfgBuilderUtils.addEdge(bodyCfg.getExitNode(), entryNode);
	}

	return cfg;
    }

}
