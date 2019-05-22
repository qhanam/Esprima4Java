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

    /** The number of times to iterate over the loop. */
    private static final int LOOP_UNROLL_ITERATIONS = 1;

    public static Cfg build(ForStatement statement) {
	CfgNode entryNode = new CfgEvaluateNode(statement.init());
	CfgNode updateNode;
	CfgNode loopNode = new CfgEmptyNode();
	CfgNode exitNode = new CfgEmptyNode();

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Edges from init to loop and update to loop.
	CfgBuilderUtils.addEdge(entryNode, loopNode);

	// Unroll the loop the desired number of times.
	for (int i = 0; i < LOOP_UNROLL_ITERATIONS; i++) {
	    // Handle the consequent.
	    Cfg bodyCfg = statement.body().buildCfg();

	    // Set up the true and false branch edges.
	    CfgBuilderUtils.addTrueAssertion(statement.test(), loopNode, bodyCfg.getEntryNode());
	    CfgBuilderUtils.addFalseAssertion(statement.test(), loopNode, exitNode);

	    // Create a new entry point for the next iteration of the loop.
	    updateNode = new CfgEvaluateNode(statement.update());
	    loopNode = new CfgEmptyNode();
	    CfgBuilderUtils.addEdge(updateNode, loopNode);

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

	}

	// The last loop entry point only has a path through the false branch.
	CfgBuilderUtils.addFalseAssertion(statement.test(), loopNode, exitNode);

	return cfg;
    }

}
