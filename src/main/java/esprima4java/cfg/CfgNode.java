package esprima4java.cfg;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.abstractstate.AnalysisState;

public abstract class CfgNode {

    /** The unique id for this node. */
    private int id;

    /** CfgNodes which precede this node. */
    private List<CfgNode> incoming;

    /** CfgNodes which follow this node. */
    private List<CfgNode> outgoing;

    /**
     * The state of the machine before transferring over the node.
     */
    @Nullable
    private AnalysisState beforeState;

    /**
     * The state of the machine after transferring over the node.
     */
    @Nullable
    private AnalysisState afterState;

    protected CfgNode() {
	this.id = CfgIdGenerator.getUniqueID();
	this.incoming = new ArrayList<>();
	this.outgoing = new ArrayList<>();
	this.beforeState = null;
	this.afterState = null;
    }

    public int id() {
	return id;
    }

    public AnalysisState getBeforeState() {
	return beforeState;
    }

    public AnalysisState getAfterState() {
	return afterState;
    }

    public void addOutgoing(CfgNode outgoing) {
	if (!this.outgoing.contains(outgoing)) {
	    this.outgoing.add(outgoing);
	}
    }

    public void addIncoming(CfgNode incoming) {
	if (!this.incoming.contains(incoming)) {
	    this.incoming.add(incoming);
	}
    }

    public List<CfgNode> outgoing() {
	return outgoing;
    }

    public List<CfgNode> incoming() {
	return incoming;
    }

    /**
     * Updates the before AnalysisState with the new state.
     */
    public void updateBeforeState(AnalysisState newState) {
	if (beforeState == null) {
	    beforeState = newState;
	} else {
	    beforeState = beforeState.join(newState);
	}
    }

    /**
     * Updates the after AnalysisState with the new state.
     */
    public void updateAfterState(AnalysisState newState) {
	if (afterState == null) {
	    afterState = newState;
	} else {
	    afterState = afterState.join(newState);
	}
    }

    /**
     * Updates the before AnalysisState by merging the after state of this node's
     * predecessors.
     */
    public void updateFromPredecessors() {
	for (CfgNode predecessor : incoming) {
	    if (beforeState == null) {
		beforeState = predecessor.afterState;
	    } else {
		beforeState = beforeState.join(predecessor.afterState);
	    }
	}
    }

    /**
     * Returns the named type of the CfgNode.
     */
    public String type() {
	return this.getClass().getSimpleName();
    }

    /**
     * Updates the after AnalysisState by evaluating the expression of this node.
     */
    public abstract void evaluate();

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

}
