package esprima4java.diff;

import java.util.HashMap;
import java.util.Map;

import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

import esprima4java.ast.Identifier;
import esprima4java.ast.Literal;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.utilities.NodeVisitor;

public class GumTreeVisitor implements NodeVisitor {

    private Map<ITree, Node> gum2Esprima;
    private Map<Node, ITree> esprima2Gum;
    private TreeContext context;

    public GumTreeVisitor(Node root) {
	gum2Esprima = new HashMap<>();
	esprima2Gum = new HashMap<>();
	context = new TreeContext();
	ITree tree = buildTree(root);
	context.setRoot(tree);
    }

    public TreeContext getTree(Node root) {
	return context;
    }

    public Map<ITree, Node> getTrees() {
	return gum2Esprima;
    }

    @Override
    public boolean visit(Node node) {
	if (node.type() == NodeType.PROGRAM) {
	    return true;
	} else {
	    ITree t = buildTree(node);

	    ITree p = esprima2Gum.get(node.parent());
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
	esprima2Gum.put(node, t);
	gum2Esprima.put(t, node);
	return t;
    }

}
