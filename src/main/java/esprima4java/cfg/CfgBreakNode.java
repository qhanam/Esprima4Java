package esprima4java.cfg;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.ast.Node;

/**
 * The entry point to a control flow graph.
 */
public class CfgBreakNode extends CfgNode {

    @Nullable
    private Node label;

    public CfgBreakNode(Node label) {
	super();
	this.label = label;
    }

    @Override
    public void evaluate() {
	// Control flow only.
    }

}
