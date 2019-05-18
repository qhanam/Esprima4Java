package esprima4java.cfg.builders;

import esprima4java.ast.IfStatement;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEmptyNode;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForIfStatements {

    public static Cfg build(IfStatement statement) {
	CfgEmptyNode entryNode = new CfgEmptyNode();
	CfgEmptyNode exitNode = new CfgEmptyNode();

	Cfg cfg = new Cfg(entryNode);

	// Handle the consequent.
	Cfg consequentCfg = statement.consequent().buildCfg();

	// Add an edge from the entry node to the consequent.
	CfgBuilderUtils.addTrueAssertion(statement.test(), entryNode, consequentCfg.getEntryNode());

	// Add all the exit points
	cfg.addAllBreakNodes(consequentCfg.getBreakNodes());
	cfg.addAllContinueNodes(consequentCfg.getContinueNodes());
	cfg.addAllReturnNodes(consequentCfg.getReturnNodes());
	cfg.addAllThrowNodes(consequentCfg.getThrowNodes());

	if (consequentCfg.getExitNode() != null) {
	    // Add an edge from the consequent to the exit node.
	    CfgBuilderUtils.addEdge(consequentCfg.getExitNode(), exitNode);
	}

	// Handle the alternate.
	if (statement.alternate() != null) {
	    Cfg alternateCfg = statement.alternate().buildCfg();

	    // Add an edge from the entry node to the consequent.
	    CfgBuilderUtils.addFalseAssertion(statement.test(), entryNode,
		    alternateCfg.getEntryNode());

	    // Add all the exit points
	    cfg.addAllBreakNodes(alternateCfg.getBreakNodes());
	    cfg.addAllContinueNodes(alternateCfg.getContinueNodes());
	    cfg.addAllReturnNodes(alternateCfg.getReturnNodes());
	    cfg.addAllThrowNodes(alternateCfg.getThrowNodes());

	    if (alternateCfg.getExitNode() != null) {
		// Add an edge from the alternate to the exit node.
		CfgBuilderUtils.addEdge(alternateCfg.getExitNode(), exitNode);
	    }

	}

	if (!exitNode.incoming().isEmpty()) {
	    cfg.setExitNode(exitNode);
	}

	return cfg;
    }

}
