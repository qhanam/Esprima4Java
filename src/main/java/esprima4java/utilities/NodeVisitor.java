package esprima4java.utilities;

import esprima4java.ast.Node;

/**
 * An interface for traversing an AST.
 */
public interface NodeVisitor {

    /**
     * Visits the given node.
     * 
     * @return {@code true} if the node's children should also be visited;
     *         {@code false} otherwise.
     */
    boolean visit(Node node);

}
