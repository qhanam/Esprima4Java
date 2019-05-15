package esprima4java.cfg.builders;

import esprima4java.ast.EmptyStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEdge;
import esprima4java.cfg.CfgNode;
import esprima4java.ast.WhileStatement;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForWhileStatements {

    public static Cfg build(WhileStatement statement) {
	CfgNode entryNode = CfgNode.create(EmptyStatement.create());
	CfgNode exitNode = CfgNode.create(EmptyStatement.create());

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Handle the consequent.
	Cfg bodyCfg = statement.body().buildCfg();

	// Set up the true and false branch edges.
	CfgEdge.create(statement.test(), entryNode, bodyCfg.getEntryNode());
	CfgEdge.create(UnaryExpression.create(UnaryOperator.NOT, true, statement.test().clone()),
		entryNode, exitNode);

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
	    CfgEdge.create(Literal.createBoolean(true, "true"), bodyCfg.getExitNode(), entryNode);
	}

	return cfg;
    }

}
