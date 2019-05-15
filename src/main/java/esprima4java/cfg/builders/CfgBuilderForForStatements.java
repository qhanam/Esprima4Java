package esprima4java.cfg.builders;

import esprima4java.ast.EmptyStatement;
import esprima4java.ast.ExpressionStatement;
import esprima4java.ast.ForStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEdge;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForForStatements {

    public static Cfg build(ForStatement statement) {
	CfgNode entryNode = CfgNode
		.create(ExpressionStatement.create(statement.init().clone(), null));
	CfgNode loopNode = CfgNode.create(EmptyStatement.create());
	CfgNode exitNode = CfgNode.create(EmptyStatement.create());
	CfgNode updateNode = CfgNode
		.create(ExpressionStatement.create(statement.update().clone(), null));

	// Edges from init to loop and update to loop.
	CfgEdge.create(Literal.createBoolean(true, "true"), entryNode, loopNode);
	CfgEdge.create(Literal.createBoolean(true, "true"), updateNode, loopNode);

	Cfg cfg = new Cfg(entryNode);
	cfg.setExitNode(exitNode);

	// Handle the consequent.
	Cfg bodyCfg = statement.body().buildCfg();

	// Set up the true and false branch edges.
	CfgEdge.create(statement.test(), loopNode, bodyCfg.getEntryNode());
	CfgEdge.create(UnaryExpression.create(UnaryOperator.NOT, true, statement.test().clone()),
		loopNode, exitNode);

	// Add all the exit points
	cfg.addAllReturnNodes(bodyCfg.getReturnNodes());
	cfg.addAllThrowNodes(bodyCfg.getThrowNodes());

	// Set up loops and exits
	for (CfgNode node : bodyCfg.getBreakNodes()) {
	    CfgEdge.create(Literal.createBoolean(true, "true"), node, exitNode);
	}
	for (CfgNode node : bodyCfg.getContinueNodes()) {
	    CfgEdge.create(Literal.createBoolean(true, "true"), node, updateNode);
	}
	if (bodyCfg.getExitNode() != null) {
	    CfgEdge.create(Literal.createBoolean(true, "true"), bodyCfg.getExitNode(), updateNode);
	}

	return cfg;
    }

}
