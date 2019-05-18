package esprima4java.cfg.builders;

import esprima4java.ast.DoWhileStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBreakNode;
import esprima4java.cfg.CfgContinueNode;
import esprima4java.cfg.CfgEmptyNode;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForDoWhileStatements {

    public static Cfg build(DoWhileStatement statement) {
	CfgEmptyNode entryNode = new CfgEmptyNode();
	CfgEmptyNode exitNode = new CfgEmptyNode();

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Handle the consequent.
	Cfg bodyCfg = statement.body().buildCfg();

	// The block is always executed at least once.
	CfgBuilderUtils.addEdge(entryNode, bodyCfg.getEntryNode());

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
	    // True branch has an edge to the entry node.
	    CfgBuilderUtils.addTrueAssertion(statement.test(), bodyCfg.getExitNode(), entryNode);
	    // False branch has an edge to the exit node.
	    CfgBuilderUtils.addFalseAssertion(statement.test(), bodyCfg.getExitNode(), entryNode);
	}

	return cfg;
    }

}
