package esprima4java.diff;

import java.io.InvalidClassException;
import java.util.Map;

import com.github.gumtreediff.actions.RootAndLeavesClassifier;
import com.github.gumtreediff.actions.TreeClassifier;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.TreeContext;

import esprima4java.ast.Node;

public class GumTree {

    /**
     * Annotates the AST nodes with edit operations computed by GumTree diff.
     * 
     * @param src
     *            The root of the old AST subtree.
     * @param dst
     *            The root of the new AST subtree.
     */
    public static void diff(Node src, Node dst) {
	/* Create the abstract GumTree representation of the ASTs. */
	NodeMapContext srcContext = generate(src);
	NodeMapContext dstContext = generate(dst);

	/*
	 * Initialize the tree metrics needed by GumTree (i.e. depth, height, size,
	 * hash, post-order numbering)
	 */
	srcContext.context.validate();
	dstContext.context.validate();

	/* Match the source tree nodes to the destination tree nodes. */
	Matcher matcher = matchTreeNodes(srcContext.context.getRoot(),
		dstContext.context.getRoot());

	/* Map the results of the diff from the GumTree AST to the Esprima AST. */
	classifyTreeNodes(srcContext, dstContext, matcher);
    }

    /**
     * Returns the GumTree TreeContext for the AST subtree.
     * 
     * This utility should not be confused with GumTree's TreeGenerator, although it
     * serves a similar purpose. While TreeGenerator both parses an AST from a text
     * stream AND creates an ITree, this function only create an ITree given a
     * parsed AST.
     */
    private static NodeMapContext generate(Node node) {
	GumTreeVisitor visitor = new GumTreeVisitor(node);
	node.accept(visitor);
	return new NodeMapContext(visitor.getTrees(), visitor.getTree(node));
    }

    /**
     * Match the source Tree (AST) nodes to the destination nodes.
     *
     * The default algorithm for doing this is the GumTree algorithm (used here),
     * but other methods (like ChangeDistiller) could also be used with a bit more
     * instrumentation.
     * 
     * @param src
     *            The source GumTree (AST).
     * @param dst
     *            The destination GumTree (AST).
     * @return The data structure containing GumTree node mappings.
     */
    private static Matcher matchTreeNodes(ITree src, ITree dst) {
	Matcher matcher = Matchers.getInstance().getMatcher(src, dst);
	matcher.match();
	return matcher;
    }

    /**
     * Classify nodes in the source and destination trees as deleted, added, moved
     * or updated. The source tree nodes can be deleted, moved or updated, while the
     * destination tree nodes can be inserted, moved or updated. Moved, deleted and
     * unchanged nodes have mappings from the source tree to the destination tree.
     * 
     * @param src
     *            The source GumTree (AST).
     * @param dst
     *            The destination GumTree (AST).
     * @param matcher
     *            The data structure containing GumTree node mappings.
     * @return The ASTClassifier so the CFGFactory can assign IDs to any new nodes
     *         it creates.
     * @throws InvalidClassException
     *             If GumTree Tree nodes are generated from a parser other than
     *             Mozilla Rhino.
     */
    private static void classifyTreeNodes(NodeMapContext src, NodeMapContext dst, Matcher matcher) {

	/* Classify the GumTree (Tree) nodes. */
	TreeClassifier classifier = new RootAndLeavesClassifier(src.context, dst.context, matcher);
	classifier.classify();

	/*
	 * We use mapping ids to keep track of mapping changes from the source to the
	 * destination.
	 */
	MappingStore mappings = matcher.getMappings();

	/* Assign the classifications directly to the AstNodes. */
	EsprimaDiffMapper diffMapper = new EsprimaDiffMapper(src, dst, classifier, mappings);
	diffMapper.classifyASTNodes();

    }

    static class NodeMapContext {
	Map<ITree, Node> map;
	TreeContext context;

	private NodeMapContext(Map<ITree, Node> map, TreeContext context) {
	    this.map = map;
	    this.context = context;
	}
    }
}
