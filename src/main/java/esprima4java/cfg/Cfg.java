package esprima4java.cfg;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.eclipse.jdt.annotation.Nullable;

import esprima4java.abstractstate.AnalysisState;

/**
 * An intra-procedural control flow graph or subgraph.
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
    private List<CfgBreakNode> breakNodes;
    private List<CfgContinueNode> continueNodes;
    private List<CfgThrowNode> throwNodes;
    private List<CfgReturnNode> returnNodes;

    public Cfg(CfgNode entryNode) {
	this.entryNode = entryNode;
	this.exitNode = null;
	this.breakNodes = new LinkedList<CfgBreakNode>();
	this.continueNodes = new LinkedList<CfgContinueNode>();
	this.throwNodes = new LinkedList<CfgThrowNode>();
	this.returnNodes = new LinkedList<CfgReturnNode>();
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
    public void addBreakNode(CfgBreakNode node) {
	this.breakNodes.add(node);
    }

    /**
     * Adds all the break nodes in the list.
     * 
     * @param nodes
     */
    public void addAllBreakNodes(List<CfgBreakNode> nodes) {
	this.breakNodes.addAll(nodes);
    }

    /**
     * Get the break nodes for this graph.
     * 
     * @return The list of break points.
     */
    public List<CfgBreakNode> getBreakNodes() {
	return this.breakNodes;
    }

    /**
     * Add an continue node to this CFG.
     * 
     * @param node
     *            The last node before continuing an execution branch.
     */
    public void addContinueNode(CfgContinueNode node) {
	this.continueNodes.add(node);
    }

    /**
     * Adds all the continue nodes in the list.
     * 
     * @param nodes
     */
    public void addAllContinueNodes(List<CfgContinueNode> nodes) {
	this.continueNodes.addAll(nodes);
    }

    /**
     * Get the continue nodes for this graph.
     * 
     * @return The list of continue points.
     */
    public List<CfgContinueNode> getContinueNodes() {
	return this.continueNodes;
    }

    /**
     * Add an throw node to this CFG.
     * 
     * @param node
     *            The last node before continuing an execution branch.
     */
    public void addThrowNode(CfgThrowNode node) {
	this.throwNodes.add(node);
    }

    /**
     * Adds all the throw nodes in the list.
     * 
     * @param nodes
     */
    public void addAllThrowNodes(List<CfgThrowNode> nodes) {
	this.throwNodes.addAll(nodes);
    }

    /**
     * Get the throw nodes for this graph.
     * 
     * @return The list of throw points.
     */
    public List<CfgThrowNode> getThrowNodes() {
	return this.throwNodes;
    }

    /**
     * Add an return node to this CFG.
     * 
     * @param node
     *            The last node before returning an execution branch.
     */
    public void addReturnNode(CfgReturnNode node) {
	this.returnNodes.add(node);
    }

    /**
     * Adds all the return nodes in the list.
     * 
     * @param nodes
     */
    public void addAllReturnNodes(List<CfgReturnNode> nodes) {
	this.returnNodes.addAll(nodes);
    }

    /**
     * Get the return nodes for this graph.
     * 
     * @return The list of return points.
     */
    public List<CfgReturnNode> getReturnNodes() {
	return this.returnNodes;
    }

    /**
     * Returns a depth-first trace of the statements/conditions in the CFG.
     */
    public List<String> depthFirstTrace() {
	List<String> trace = new ArrayList<>();
	Set<CfgNode> visited = new HashSet<>();
	Stack<CfgNode> stack = new Stack<>();
	stack.add(entryNode);
	visited.add(entryNode);
	while (!stack.isEmpty()) {
	    CfgNode current = stack.pop();
	    trace.add(current.type());
	    for (CfgNode child : current.outgoing()) {
		if (!visited.contains(child)) {
		    stack.push(child);
		    visited.add(child);
		}
	    }
	}
	return trace;
    }

    /**
     * Initializes a new intra-procedural control flow queue for the CFG. The queue
     * contains CfgNodes ordered such that the execution order is maintained. To run
     * an analysis, transfer the initial state over each node in order.
     */
    public Queue<CfgNode> initializeControlState(AnalysisState initialState) {
	// The ordered instructions.
	Queue<CfgNode> instructions = new ArrayDeque<>();
	this.getEntryNode().updateBeforeState(initialState);

	// The set of nodes that have been visited.
	Set<CfgNode> visited = new HashSet<>();

	// Run a topological sort starting at each exit node to find a sound ordering.
	List<CfgNode> exitNodes = new ArrayList<>();
	exitNodes.add(this.getExitNode());
	exitNodes.addAll(this.getReturnNodes());
	exitNodes.addAll(this.getThrowNodes());
	exitNodes.forEach(exitNode -> topsort(exitNode, visited, instructions));

	return instructions;
    }

    private void topsort(CfgNode node, Set<CfgNode> visited, Queue<CfgNode> instructions) {
	// Visit the children.
	for (CfgNode child : node.incoming()) {
	    if (!visited.contains(child)) {
		topsort(child, visited, instructions);
	    }
	}
	// Visit this node.
	instructions.add(node);
	visited.add(node);
    }

}
