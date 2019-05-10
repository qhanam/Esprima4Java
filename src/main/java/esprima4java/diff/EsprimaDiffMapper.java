package esprima4java.diff;

import java.io.InvalidClassException;

import com.github.gumtreediff.actions.TreeClassifier;
import com.github.gumtreediff.matchers.Mapping;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.ITree;

import esprima4java.addons.ChangeInfo;
import esprima4java.addons.ChangeInfo.EditOp;
import esprima4java.addons.ChangeInfo.Version;
import esprima4java.ast.Node;
import esprima4java.diff.GumTree.NodeMapContext;

public class EsprimaDiffMapper {

    private NodeMapContext src;
    private NodeMapContext dst;
    private TreeClassifier treeClassifier;
    private MappingStore mappings;

    public EsprimaDiffMapper(NodeMapContext src, NodeMapContext dst, TreeClassifier treeClassifier,
	    MappingStore mappings) {
	this.src = src;
	this.dst = dst;
	this.treeClassifier = treeClassifier;
	this.mappings = mappings;
    }

    /**
     * Maps Tree node classifications to AST node classifications.
     * 
     * @throws InvalidClassException
     */
    public void classifyASTNodes() {
	// Handle UNCHANGED, UPDATED and MOVED nodes.
	for (Mapping mapping : mappings) {
	    Node srcNode = src.map.get(mapping.getFirst());
	    Node dstNode = dst.map.get(mapping.getSecond());

	    // Check src for update/move.
	    if (treeClassifier.getSrcMvTrees().contains(mapping.getFirst())) {
		srcNode.setChangeInfo(ChangeInfo.create(true, EditOp.UNCHANGED, EditOp.UNCHANGED,
			dstNode, Version.SOURCE));
	    } else if (treeClassifier.getSrcUpdTrees().contains(mapping.getFirst())) {
		srcNode.setChangeInfo(ChangeInfo.create(false, EditOp.UPDATED, EditOp.UPDATED,
			dstNode, Version.SOURCE));
	    } else {
		srcNode.setChangeInfo(ChangeInfo.create(false, EditOp.UNCHANGED, EditOp.UNCHANGED,
			dstNode, Version.SOURCE));
	    }

	    // Check dst for update/move.
	    if (treeClassifier.getDstMvTrees().contains(mapping.getFirst())) {
		dstNode.setChangeInfo(ChangeInfo.create(true, EditOp.UNCHANGED, EditOp.UNCHANGED,
			srcNode, Version.DESTINATION));
	    } else if (treeClassifier.getSrcUpdTrees().contains(mapping.getFirst())) {
		dstNode.setChangeInfo(ChangeInfo.create(false, EditOp.UPDATED, EditOp.UPDATED,
			srcNode, Version.DESTINATION));
	    } else {
		dstNode.setChangeInfo(ChangeInfo.create(false, EditOp.UNCHANGED, EditOp.UNCHANGED,
			srcNode, Version.DESTINATION));
	    }
	}

	// Handle INSERTED and DELETED nodes.
	for (ITree tree : treeClassifier.getSrcDelTrees()) {
	    Node srcNode = src.map.get(tree);
	    srcNode.setChangeInfo(
		    ChangeInfo.create(false, EditOp.DELETED, EditOp.DELETED, null, Version.SOURCE));
	}

	for (ITree tree : treeClassifier.getDstAddTrees()) {
	    Node dstNode = dst.map.get(tree);
	    dstNode.setChangeInfo(ChangeInfo.create(false, EditOp.INSERTED, EditOp.INSERTED, null,
		    Version.DESTINATION));
	}
    }

}