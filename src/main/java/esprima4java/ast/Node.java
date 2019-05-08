package esprima4java.ast;

import esprima4java.addons.ChangeInfo;
import esprima4java.addons.Criteria;
import esprima4java.addons.Dependencies;
import esprima4java.addons.SourceLocation;

public abstract class Node {

    /** Populated by the GumTree AST diff utility. */
    private ChangeInfo changeInfo;

    /** Populated by the MultiDiff flow analysis. */
    public Criteria criteria;
    public Dependencies dependencies;

    /** Populated by the deserializer. */
    private SourceLocation location;

    public Node() {
	this.changeInfo = null;
	this.criteria = new Criteria();
	this.dependencies = new Dependencies();
	this.location = null;
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
