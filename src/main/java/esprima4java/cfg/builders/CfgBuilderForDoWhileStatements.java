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

    /** The number of times to iterate over the loop. */
    private static final int LOOP_UNROLL_ITERATIONS = 1;

    public static Cfg build(DoWhileStatement statement) {
	CfgEmptyNode entryNode = new CfgEmptyNode();
	CfgEmptyNode loopNode = new CfgEmptyNode();
	CfgEmptyNode exitNode = new CfgEmptyNode();

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Unroll the loop the desired number of times.
	Cfg bodyCfg;
	int i = 0;
	do {
	    // Handle the consequent.
	    bodyCfg = statement.body().buildCfg();

	    // The block is always executed at least once.
	    CfgBuilderUtils.addEdge(entryNode, bodyCfg.getEntryNode());

	    // Create a new entry point for the next iteration of the loop.
	    entryNode = new CfgEmptyNode();
	    loopNode = new CfgEmptyNode();

	    // Add all the exit points
	    cfg.addAllReturnNodes(bodyCfg.getReturnNodes());
	    cfg.addAllThrowNodes(bodyCfg.getThrowNodes());

	    for (CfgBreakNode node : bodyCfg.getBreakNodes()) {
		// Break nodes have edges to the exit node.
		CfgBuilderUtils.addEdge(node, exitNode);
	    }

	    for (CfgContinueNode node : bodyCfg.getContinueNodes()) {
		// Continue nodes have edges to the loop entry.
		CfgBuilderUtils.addEdge(node, loopNode);
	    }

	    if (bodyCfg.getExitNode() != null) {
		// Body exits to the loop node.
		CfgBuilderUtils.addEdge(bodyCfg.getExitNode(), loopNode);
	    }

	    // False branch has an edge to the exit node.
	    CfgBuilderUtils.addFalseAssertion(statement.test(), loopNode, exitNode);

	    i++;
	    if (i < LOOP_UNROLL_ITERATIONS) {
		// True branch has an edge to the entry node.
		CfgBuilderUtils.addTrueAssertion(statement.test(), loopNode, entryNode);
		// We haven't reached the desired number of iterations, so start another loop.
		continue;
	    } else {
		// We have reached the desired number of iterations, so break.
		break;
	    }

	} while (true);

	return cfg;
    }

}
