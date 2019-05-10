package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.addons.ChangeInfo;
import esprima4java.addons.Criteria;
import esprima4java.addons.Dependencies;
import esprima4java.addons.SourceLocation;
import esprima4java.utilities.NodeVisitor;

public abstract class Node {

    /** The node's parent. */
    @Nullable
    private Node parent;

    /** Populated by the GumTree AST diff utility. */
    private ChangeInfo changeInfo;

    /** Populated by the MultiDiff flow analysis. */
    public Criteria criteria;
    public Dependencies dependencies;

    /** Populated by the deserializer. */
    private SourceLocation location;

    public Node() {
	this.parent = null;
	this.changeInfo = null;
	this.criteria = new Criteria();
	this.dependencies = new Dependencies();
	this.location = SourceLocation.create();
    }

    public void accept(NodeVisitor visitor) {
	boolean visitChildren = visitor.visit(this);
	if (visitChildren) {
	    getChildren().forEach(child -> child.accept(visitor));
	}
    }

    public List<Node> getChildren() {
	return Collections.emptyList();
    }

    public void setParent(Node parent) {
	this.parent = parent;
    }

    public Node parent() {
	return parent;
    }

    public void setChangeInfo(ChangeInfo changeInfo) {
	this.changeInfo = changeInfo;
    }

    public ChangeInfo changeInfo() {
	return changeInfo;
    }

    public void setSourceLocation(SourceLocation location) {
	this.location = location;
    }

    public SourceLocation sourceLocation() {
	return location;
    }

    public abstract NodeType type();
}
