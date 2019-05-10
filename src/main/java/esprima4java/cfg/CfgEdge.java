package esprima4java.cfg;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.ast.Node;

public class CfgEdge {

    private int id;

    private Node condition;

    @Nullable
    private CfgNode from;

    @Nullable
    private CfgNode to;

    private CfgEdge(Node condition) {
	this.id = CfgIdGenerator.getUniqueID();
	this.condition = condition;
	this.from = null;
	this.to = null;
    }

    public static CfgEdge create(Node condition) {
	return new CfgEdge(condition);
    }

    public int id() {
	return id;
    }

    public Node condition() {
	return condition;
    }

    public void setFrom(CfgNode from) {
	this.from = from;
    }

    public void setTo(CfgNode to) {
	this.to = to;
    }

    public CfgNode from() {
	return from;
    }

    public CfgNode to() {
	return to;
    }

    @Override
    public int hashCode() {
	return id;
    }

    @Override
    public boolean equals(Object o) {
	if (o == this) {
	    return true;
	}
	if (o instanceof CfgEdge) {
	    CfgEdge that = (CfgEdge) o;
	    return this.id == that.id;
	}
	return false;
    }

}
