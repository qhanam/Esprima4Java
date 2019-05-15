package esprima4java.cfg.builders;

import esprima4java.ast.EmptyStatement;
import esprima4java.ast.Literal;
import esprima4java.ast.LogicalExpression;
import esprima4java.ast.LogicalExpression.LogicalOperator;
import esprima4java.ast.Node;
import esprima4java.ast.SwitchCase;
import esprima4java.ast.SwitchStatement;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgEdge;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from switch statements.
 */
public class CfgBuilderForSwitchStatements {

    public static Cfg build(SwitchStatement statement) {
	CfgNode entryNode = CfgNode.create(EmptyStatement.create());
	CfgNode exitNode = CfgNode.create(EmptyStatement.create());
	Cfg cfg = new Cfg(entryNode);

	CfgNode currentNode = null;

	// Build the default condition first
	boolean hasDefault = false;
	Node defaultCondition = null;
	for (SwitchCase child : statement.cases()) {
	    if (child.test() != null) {
		defaultCondition = defaultCondition == null ? child.test().clone()
			: LogicalExpression.create(LogicalOperator.AND, defaultCondition,
				UnaryExpression.create(UnaryOperator.NOT, true, child.test()));
	    } else {
		hasDefault = true;
	    }
	}
	defaultCondition = defaultCondition == null ? Literal.createBoolean(true, "true")
		: defaultCondition;

	// If there is no default, then there is an edge from entry to exit.
	if (!hasDefault) {
	    CfgEdge.create(defaultCondition, entryNode, exitNode);
	}

	// Build each case
	for (SwitchCase child : statement.cases()) {
	    Cfg childCfg = child.buildCfg();

	    if (child.test() != null) {
		CfgEdge.create(child.test(), entryNode, childCfg.getEntryNode());
		defaultCondition = defaultCondition == null ? child.test().clone()
			: LogicalExpression.create(LogicalOperator.AND, defaultCondition,
				UnaryExpression.create(UnaryOperator.NOT, true, child.test()));
	    } else {
		CfgEdge.create(defaultCondition, entryNode, childCfg.getEntryNode());
	    }

	    if (currentNode != null) {
		CfgEdge.create(Literal.createBoolean(true, "true"), currentNode,
			childCfg.getEntryNode());
	    }

	    cfg.addAllContinueNodes(childCfg.getContinueNodes());
	    cfg.addAllReturnNodes(childCfg.getReturnNodes());
	    cfg.addAllThrowNodes(childCfg.getThrowNodes());

	    for (CfgNode breakNode : childCfg.getBreakNodes()) {
		CfgEdge.create(Literal.createBoolean(true, "true"), breakNode, exitNode);
	    }

	    currentNode = childCfg.getExitNode();
	}

	if (currentNode != null) {
	    CfgEdge.create(Literal.createBoolean(true, "true"), currentNode, exitNode);
	}

	if (!exitNode.incoming().isEmpty()) {
	    cfg.setExitNode(exitNode);
	}

	return cfg;
    }

}
