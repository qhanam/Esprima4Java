package esprima4java.cfg.builders;

import esprima4java.ast.EmptyStatement;
import esprima4java.ast.IfStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEdge;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from if statements.
 */
public class CfgBuilderForIfStatements {

    public static Cfg build(IfStatement statement) {
	CfgNode entryNode = CfgNode.create(EmptyStatement.create());
	CfgNode exitNode = CfgNode.create(EmptyStatement.create());

	Cfg cfg = new Cfg(entryNode);

	// Handle the consequent.
	Cfg consequentCfg = statement.consequent().buildCfg();

	// Set up an edge from the entry node to the consequent.
	CfgEdge.create(statement.test(), entryNode, consequentCfg.getEntryNode());

	// Add all the exit points
	cfg.addAllBreakNodes(consequentCfg.getBreakNodes());
	cfg.addAllContinueNodes(consequentCfg.getContinueNodes());
	cfg.addAllReturnNodes(consequentCfg.getReturnNodes());
	cfg.addAllThrowNodes(consequentCfg.getThrowNodes());

	// Set up an edge from the consequent to the exit node.
	if (consequentCfg.getExitNode() != null) {
	    CfgEdge.create(Literal.createBoolean(true, "true"), consequentCfg.getExitNode(),
		    exitNode);
	}

	// Handle the alternate.
	if (statement.alternate() != null) {
	    Cfg alternateCfg = statement.alternate().buildCfg();

	    // Set up an edge from the entry node to the consequent.
	    CfgEdge.create(
		    UnaryExpression.create(UnaryOperator.NOT, true, statement.test().clone()),
		    entryNode, alternateCfg.getEntryNode());

	    // Add all the exit points
	    cfg.addAllBreakNodes(alternateCfg.getBreakNodes());
	    cfg.addAllContinueNodes(alternateCfg.getContinueNodes());
	    cfg.addAllReturnNodes(alternateCfg.getReturnNodes());
	    cfg.addAllThrowNodes(alternateCfg.getThrowNodes());

	    // Set up an edge from the alternate to the exit node.
	    if (alternateCfg.getExitNode() != null) {
		CfgEdge.create(Literal.createBoolean(true, "true"), alternateCfg.getExitNode(),
			exitNode);
	    }

	}

	if (!exitNode.incoming().isEmpty()) {
	    cfg.setExitNode(exitNode);
	}

	return cfg;
    }

}
