package esprima4java.cfg;

import esprima4java.ast.DoWhileStatement;
import esprima4java.ast.EmptyStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForDoWhileStatements {

    public static Cfg build(DoWhileStatement statement) {
	CfgNode entryNode = CfgNode.create(EmptyStatement.create());
	CfgNode exitNode = CfgNode.create(EmptyStatement.create());

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Handle the consequent.
	Cfg bodyCfg = statement.body().buildCfg();

	// The block is always executed at least once.
	CfgEdge.create(Literal.createBoolean(true, "true"), entryNode, bodyCfg.getEntryNode());

	// Add all the exit points
	cfg.addAllReturnNodes(bodyCfg.getReturnNodes());
	cfg.addAllThrowNodes(bodyCfg.getThrowNodes());

	// Set up loops and exits
	for (CfgNode node : bodyCfg.getBreakNodes()) {
	    CfgEdge.create(Literal.createBoolean(true, "true"), node, exitNode);
	}
	for (CfgNode node : bodyCfg.getContinueNodes()) {
	    CfgEdge.create(Literal.createBoolean(true, "true"), node, entryNode);
	}
	if (bodyCfg.getExitNode() != null) {
	    CfgEdge.create(statement.test(), bodyCfg.getExitNode(), entryNode);
	    CfgEdge.create(
		    UnaryExpression.create(UnaryOperator.NOT, true, statement.test().clone()),
		    bodyCfg.getExitNode(), exitNode);
	}

	return cfg;
    }

}
