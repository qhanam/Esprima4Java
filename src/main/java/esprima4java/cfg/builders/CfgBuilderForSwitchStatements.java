package esprima4java.cfg.builders;

import esprima4java.ast.LogicalExpression;
import esprima4java.ast.LogicalExpression.LogicalOperator;
import esprima4java.ast.Node;
import esprima4java.ast.SwitchCase;
import esprima4java.ast.SwitchStatement;
import esprima4java.ast.UnaryExpression;
import esprima4java.ast.UnaryExpression.UnaryOperator;
import esprima4java.cfg.Cfg;
import esprima4java.cfg.CfgBreakNode;
import esprima4java.cfg.CfgEmptyNode;
import esprima4java.cfg.CfgNode;

/**
 * A builder for creating control flow graphs from switch statements.
 */
public class CfgBuilderForSwitchStatements {

    public static Cfg build(SwitchStatement statement) {
	CfgEmptyNode entryNode = new CfgEmptyNode();
	CfgEmptyNode exitNode = new CfgEmptyNode();
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

	// If there is no default, then there is an edge from entry to exit.
	if (!hasDefault) {
	    if (defaultCondition == null) {
		CfgBuilderUtils.addEdge(entryNode, exitNode);
	    } else {
		CfgBuilderUtils.addTrueAssertion(defaultCondition, entryNode, exitNode);
	    }
	}

	// Build each case
	for (SwitchCase child : statement.cases()) {
	    Cfg childCfg = child.buildCfg();

	    if (child.test() != null) {
		// Case condition
		CfgBuilderUtils.addTrueAssertion(child.test(), entryNode, childCfg.getEntryNode());
	    } else {
		// Default condition
		if (defaultCondition == null) {
		    CfgBuilderUtils.addEdge(entryNode, childCfg.getEntryNode());
		} else {
		    CfgBuilderUtils.addTrueAssertion(defaultCondition, entryNode,
			    childCfg.getEntryNode());
		}
	    }

	    if (currentNode != null) {
		// Add an edge from the previous node to the this node (fallthrough).
		CfgBuilderUtils.addEdge(currentNode, childCfg.getEntryNode());
	    }

	    cfg.addAllContinueNodes(childCfg.getContinueNodes());
	    cfg.addAllReturnNodes(childCfg.getReturnNodes());
	    cfg.addAllThrowNodes(childCfg.getThrowNodes());

	    for (CfgBreakNode breakNode : childCfg.getBreakNodes()) {
		// Add an edge from the break node to the exit node.
		CfgBuilderUtils.addEdge(breakNode, exitNode);
	    }

	    currentNode = childCfg.getExitNode();
	}

	if (currentNode != null) {
	    // Add an edge from the last statement to the exit node.
	    CfgBuilderUtils.addEdge(currentNode, exitNode);
	}

	if (!exitNode.incoming().isEmpty()) {
	    cfg.setExitNode(exitNode);
	}

	return cfg;
    }

}
