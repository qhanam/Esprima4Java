package esprima4java.cfg;

import esprima4java.ast.CallExpression;

/**
 * The entry point to a control flow graph.
 */
public class CfgCallsiteNode extends CfgNode {

    private CallExpression expression;

    public CfgCallsiteNode(CallExpression expression) {
	super();
	this.expression = expression;
    }

    @Override
    public void evaluate() {
	// TODO Auto-generated method stub
    }

}
