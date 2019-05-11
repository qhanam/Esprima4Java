package esprima4java.cfg;

import java.util.ArrayList;
import java.util.List;

import esprima4java.ast.Node;

public class CfgNode {

    private int id;

    private Node statement;

    private List<CfgEdge> incoming;

    private List<CfgEdge> outgoing;

    private CfgNode(Node statement) {
	this.id = CfgIdGenerator.getUniqueID();
	this.statement = statement;
	this.incoming = new ArrayList<CfgEdge>();
	this.outgoing = new ArrayList<CfgEdge>();
    }

    public static CfgNode create(Node statement) {
	return new CfgNode(statement);
    }

    public int id() {
	return id;
    }

    public Node statement() {
	return statement;
    }

    public void addIncoming(CfgEdge edge) {
	incoming.add(edge);
    }

    public void addOutgoing(CfgEdge edge) {
	outgoing.add(edge);
    }

    public List<CfgEdge> incoming() {
	return incoming;
    }

    public List<CfgEdge> outgoing() {
	return outgoing;
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
	if (o instanceof CfgNode) {
	    CfgNode that = (CfgNode) o;
	    return this.id == that.id;
	}
	return false;
    }

    @Override
    public String toString() {
	return id() + ":" + statement.toString();
    }

}
