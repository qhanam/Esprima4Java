package esprima4java.cfg;

import esprima4java.ast.Node;

/**
 * The entry point to a control flow graph.
 */
public class CfgEvaluateNode extends CfgNode {

    private Node expression;

    public CfgEvaluateNode(Node expression) {
	super();
	this.expression = expression;
    }

    @Override
    public void evaluate() {
	// TODO Auto-generated method stub
    }

}
