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

    private CfgEdge(Node condition, CfgNode from, CfgNode to) {
	this.id = CfgIdGenerator.getUniqueID();
	this.condition = condition;
	this.from = from;
	this.to = to;
	from.addOutgoing(this);
	to.addIncoming(this);
    }

    public static CfgEdge create(Node condition, CfgNode from, CfgNode to) {
	return new CfgEdge(condition, from, to);
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

    @Override
    public String toString() {
	return from.toString() + "-(" + id() + ":" + condition.toString() + ")->" + to.toString();
    }
}
