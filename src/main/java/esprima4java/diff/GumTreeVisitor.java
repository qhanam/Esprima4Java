package esprima4java.diff;

import java.util.Map;

import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

import esprima4java.ast.Identifier;
import esprima4java.ast.Literal;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.utilities.NodeVisitor;

public class GumTreeVisitor implements NodeVisitor {

    private Map<Node, ITree> trees;
    private TreeContext context;

    public TreeContext getTree(Node root) {
	return context;
    }

    @Override
    public boolean visit(Node node) {
	if (node.type() == NodeType.PROGRAM)
	    return true;
	else {
	    ITree t = buildTree(node);
	    ITree p = trees.get(node.parent());
	    p.addChild(t);

	    if (node.type() == NodeType.IDENTIFIER) {
		Identifier identifier = (Identifier) node;
		t.setLabel(identifier.name());
	    } else if (node.type() == NodeType.LITERAL) {
		Literal literal = (Literal) node;
		t.setLabel(literal.raw());
	    }
	    return true;
	}
    }

    private ITree buildTree(Node node) {
	ITree t = context.createTree(node.type().ordinal(), ITree.NO_LABEL, node.type().toString());
	t.setPos(node.sourceLocation().startChar());
	t.setLength(node.sourceLocation().endChar() - node.sourceLocation().startChar());
	trees.put(node, t);
	return t;
    }

}
