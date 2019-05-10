package esprima4java.cfg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.ast.NodeType;

/**
 * A low(er) level control flow graph or subgraph.
 *
 * The control flow graph contains an entry node where the graph begins. It also
 * keeps track of statements that exit the CFG. These include the last
 * statements in a block that exit the graph and jump statements including
 * break, continue, throw and return.
 */
public class Cfg {

    private CfgNode entryNode;
    @Nullable
    private CfgNode exitNode;
    private List<CfgNode> breakNodes;
    private List<CfgNode> continueNodes;
    private List<CfgNode> throwNodes;
    private List<CfgNode> returnNodes;

    public Cfg(CfgNode entryNode) {
	this.entryNode = entryNode;
	this.exitNode = null;
	this.breakNodes = new LinkedList<CfgNode>();
	this.continueNodes = new LinkedList<CfgNode>();
	this.throwNodes = new LinkedList<CfgNode>();
	this.returnNodes = new LinkedList<CfgNode>();
    }

    /**
     * Returns the entry node for this CFG.
     * 
     * @return The entry Node.
     */
    public CfgNode getEntryNode() {
	return entryNode;
    }

    /**
     * Add an exit node to this CFG.
     * 
     * @param node
     *            The last node before exiting an execution branch.
     */
    public void setExitNode(CfgNode node) {
	this.exitNode = node;
    }

    /**
     * Remove all exit nodes from the CFG.
     */
    public void clearExitNodes() {
	this.exitNode = null;
    }

    /**
     * Get the exit node for this graph.
     */
    public CfgNode getExitNode() {
	return this.exitNode;
    }

    /**
     * Add a break node to this CFG.
     * 
     * @param node
     *            The last node before breaking an execution branch.
     */
    public void addBreakNode(CfgNode node) {
	this.breakNodes.add(node);
    }

    /**
     * Adds all the break nodes in the list.
     * 
     * @param nodes
     */
    public void addAllBreakNodes(List<CfgNode> nodes) {
	this.breakNodes.addAll(nodes);
    }

    /**
     * Get the break nodes for this graph.
     * 
     * @return The list of break points.
     */
    public List<CfgNode> getBreakNodes() {
	return this.breakNodes;
    }

    /**
     * Add an continue node to this CFG.
     * 
     * @param node
     *            The last node before continuing an execution branch.
     */
    public void addContinueNode(CfgNode node) {
	this.continueNodes.add(node);
    }

    /**
     * Adds all the continue nodes in the list.
     * 
     * @param nodes
     */
    public void addAllContinueNodes(List<CfgNode> nodes) {
	this.continueNodes.addAll(nodes);
    }

    /**
     * Get the continue nodes for this graph.
     * 
     * @return The list of continue points.
     */
    public List<CfgNode> getContinueNodes() {
	return this.continueNodes;
    }

    /**
     * Add an throw node to this CFG.
     * 
     * @param node
     *            The last node before continuing an execution branch.
     */
    public void addThrowNode(CfgNode node) {
	this.throwNodes.add(node);
    }

    /**
     * Adds all the throw nodes in the list.
     * 
     * @param nodes
     */
    public void addAllThrowNodes(List<CfgNode> nodes) {
	this.throwNodes.addAll(nodes);
    }

    /**
     * Get the throw nodes for this graph.
     * 
     * @return The list of throw points.
     */
    public List<CfgNode> getThrowNodes() {
	return this.throwNodes;
    }

    /**
     * Removes all return nodes from the CFG.
     */
    public void clearnReturnNodes() {
	this.returnNodes.clear();
    }

    /**
     * Add an return node to this CFG.
     * 
     * @param node
     *            The last node before returning an execution branch.
     */
    public void addReturnNode(CfgNode node) {
	this.returnNodes.add(node);
    }

    /**
     * Adds all the return nodes in the list.
     * 
     * @param nodes
     */
    public void addAllReturnNodes(List<CfgNode> nodes) {
	this.returnNodes.addAll(nodes);
    }

    /**
     * Get the return nodes for this graph.
     * 
     * @return The list of return points.
     */
    public List<CfgNode> getReturnNodes() {
	return this.returnNodes;
    }

    /**
     * Return {@code true} if the CFG is a script (ie. it is not a function).
     */
    public boolean isScript() {
	return entryNode.statement().type() == NodeType.PROGRAM;
    }

    /**
     * Returns a depth-first trace of the statements/conditions in the CFG.
     */
    public List<NodeType> depthFirstTrace() {
	List<NodeType> trace = new ArrayList<>();
	Set<CfgNode> visited = new HashSet<>();
	Stack<CfgNode> stack = new Stack<>();
	stack.add(entryNode);
	while (!stack.isEmpty()) {
	    CfgNode current = stack.pop();
	    trace.add(current.statement().type());
	    for (CfgEdge edge : current.outgoing()) {
		trace.add(edge.condition().type());
		if (!visited.contains(edge.to())) {
		    stack.push(edge.to());
		}
	    }
	}
	return trace;
    }

}
