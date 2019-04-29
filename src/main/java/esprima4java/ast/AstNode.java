package esprima4java.ast;

public abstract class AstNode {

    private NodeType type;

    protected AstNode(NodeType type) {
	this.type = type;
    }

    public NodeType getType() {
	return type;
    }

    // TODO
    // public abstract void accept(NodeVisitor visitor);

}
