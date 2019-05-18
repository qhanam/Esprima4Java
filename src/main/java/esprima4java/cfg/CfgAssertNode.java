package esprima4java.cfg;

import esprima4java.ast.Node;

/**
 * The entry point to a control flow graph.
 */
public class CfgAssertNode extends CfgNode {

    private Node expression;

    /** {@code true} will assert true; {@code false} will assert false. */
    private boolean assertType;

    public CfgAssertNode(Node expression, CfgNode from, CfgNode to, boolean assertType) {
	super();
	this.expression = expression;
	this.addIncoming(from);
	this.addOutgoing(to);
	from.addOutgoing(this);
	to.addIncoming(this);
	this.assertType = assertType;
    }

    @Override
    public void evaluate() {
	// TODO Auto-generated method stub
    }

}
