package esprima4java.cfg;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.ast.Node;

/**
 * The entry point to a control flow graph.
 */
public class CfgReturnNode extends CfgNode {

    @Nullable
    private Node expression;

    public CfgReturnNode(Node expression) {
	super();
	this.expression = expression;
    }

    @Override
    public void evaluate() {
	// TODO Auto-generated method stub
    }

}
