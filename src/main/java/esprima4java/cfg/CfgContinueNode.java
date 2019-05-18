package esprima4java.cfg;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.ast.Node;

/**
 * The entry point to a control flow graph.
 */
public class CfgContinueNode extends CfgNode {

    @Nullable
    private Node label;

    public CfgContinueNode(Node label) {
	super();
	this.label = label;
    }

    @Override
    public void evaluate() {
	// Control flow only.
    }

}
