package esprima4java.ast;

import esprima4java.addons.ChangeInfo;
import esprima4java.addons.Criteria;
import esprima4java.addons.Dependencies;

public abstract class Node {

    /** Populated by the GumTree AST diff utility. */
    public ChangeInfo changeInfo;

    /** Populated by the MultiDiff flow analysis. */
    public Criteria criteria;
    public Dependencies dependencies;

    public Node() {
	this.changeInfo = null;
	this.criteria = null;
	this.dependencies = null;
    }

    public abstract NodeType type();
}
